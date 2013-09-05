package com.bluesky.visualprogramming.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.serialization.SerializationService;
import com.bluesky.visualprogramming.core.serialization.SerializerType;

public class ObjectRepository {

	static Logger logger = Logger.getLogger(ObjectRepository.class);

	static String DEFAULT_VIEW_POSITION = "DEFAULT_VIEW_POSITION";

	static String DUMP_FILE = "objects";

	long objectId;
	Map<Long, _Object> objects = new HashMap<Long, _Object>();
	_Object rootObject;

	private List<ObjectRepositoryListener> listeners = new ArrayList<ObjectRepositoryListener>();

	public ObjectRepository() {
		// start from 0
		objectId = 0;

		rootObject = createObject(ObjectType.NORMAL, ObjectScope.Persistent);
		rootObject.setName("root");
	}

	private String generateNewChildName(_Object owner) {
		String prefix = "unnamed";
		String name = prefix;

		int i = 0;
		while (true) {
			_Object child = owner.getChild(name);
			if (child == null)
				break;

			if (i > 10000)
				throw new RuntimeException("too many 'unnamed' child.");

			i++;
			name = prefix + i;
		}

		return name;
	}

	public _Object createObject(ObjectType type, ObjectScope scope) {
		if (scope == null)
			throw new RuntimeException("scope cannot be null.");

		return _createObject(type, scope);
	}

	/**
	 * create object without owner. mostly it belongs to runtime execution
	 * context.
	 * 
	 * @param type
	 * @param scope
	 * @return
	 */
	private _Object _createObject(ObjectType type, ObjectScope scope) {

		_Object newObject = type.create(objectId++);
		newObject.setScope(scope);

		// set prototype for value objects
		String prototypeEl = type.getPrototypeEL();
		if (prototypeEl != null) {

			try {
				_Object prototype = getObjectByEl(prototypeEl);
				if (prototype != null)
					newObject.addChild(prototype, _Object.PROTOTYPE, false);
			} catch (InvalidELException e) {

				logger.warn(
						"the prototype object is not loaded. if it is in loading process, then it is ok."
								+ prototypeEl, e);
			}
		}

		objects.put(newObject.getId(), newObject);

		for (ObjectRepositoryListener l : listeners) {
			l.afterCreate(newObject);
		}

		return newObject;
	}

	public _Object createObject(_Object owner, ObjectType type) {
		return createObject(owner, generateNewChildName(owner), type, "");
	}

	public _Object createObject(_Object owner, String name, ObjectType type) {
		return createObject(owner, name, type, "");
	}

	/**
	 * create field for owner object
	 * 
	 * @param owner
	 * @param name
	 * @param type
	 * @param value
	 * @return
	 */
	private _Object createObject(_Object owner, String name, ObjectType type,
			String value) {
		// scope is null, means it obliges owner's value
		_Object newObject = _createObject(type, ObjectScope.ExecutionContext);

		newObject.setName(name);

		// String value = type.extractValue(literal);
		newObject.setValue(value);

		if (owner == null)
			throw new RuntimeException("owner must not be null");

		owner.addChild(newObject, name, true);

		objects.put(newObject.getId(), newObject);

		for (ObjectRepositoryListener l : listeners) {
			l.afterCreate(newObject);
		}

		return newObject;
	}

	/**
	 * e.g. root.abc.xyz
	 * 
	 * @param el
	 * @return
	 */
	private _Object getObjectByEl(String el) {
		String[] ss = el.split("\\.");

		if (!ss[0].equals("root"))
			throw new RuntimeException("the first object must be 'root':" + el);

		_Object obj = getRootObject();

		for (int i = 1; i < ss.length; i++) {
			obj = obj.getChild(ss[i]);

			if (obj == null)
				throw new InvalidELException(ss[i], el);
		}

		return obj;
	}

	/**
	 * copy fields only. procedures are not copied. 'prototype' field point to
	 * the prototype
	 * 
	 * @param src
	 * @return
	 */
	public _Object clone(_Object src) {
		return new _Object(objectId++, src);
	}

	public void destroyObject(long id) {
		_Object obj = objects.get(id);
		if (obj == null)
			throw new RuntimeException("delete object, object id not found");

		destroyObject(obj);
	}

	public void destroyObject(_Object obj) {
		if (obj.getOwner() != null) {
			obj.getOwner().removeChild(obj);
			obj.setOwner(null);
		}

		objects.remove(obj);
	}

	public _Object getRootObject() {
		return rootObject;
	}

	public _Object getObjectById(long id) {
		return objects.get(id);
	}

	public void save(String fileName) {

		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(fileName));
			save(w);

		} catch (FileNotFoundException e) {

			throw new RuntimeException(e);
		}
	}

	public void save2(Writer writer) {

		try {
			for (_Object obj : objects.values()) {
				if (getRootObject() == obj || obj.hasOwner()) {
					writer.write(obj.toText());
					writer.write("\n");
				}
			}
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void save(Writer writer) {
		SerializationService svc = new SerializationService();
		try {
			svc.serialize(getRootObject(), SerializerType.Xml, true);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void load(String fileName) {

		try {
			Reader r = new InputStreamReader(new FileInputStream(fileName));
			load(r);
		} catch (FileNotFoundException e) {

			throw new RuntimeException(e);
		}

	}

	public void load(Reader reader) {
		BufferedReader br = new BufferedReader(reader);
		List<String> lines = new ArrayList<String>();
		while (true) {

			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {

				throw new RuntimeException(e);
			}
			if (line == null)
				break;
			lines.add(line);
		}

		load(lines);

		// set object id

	}

	public void load(List<String> lines) {
		objects.clear();

		long maxObjectId = 0;

		for (String line : lines) {
			if (line.trim().isEmpty())
				continue;

			if (logger.isDebugEnabled())
				logger.debug("parse line:" + line);

			Map<String, String> map = KeyValueStringUtils.parse(line);
			String typeLiteral = map.get("type");

			ObjectType type = ObjectType.valueOf(typeLiteral);
			_Object newObject = type.create(-1);

			try {
				newObject.fromText(line);
			} catch (Exception e) {
				throw new RuntimeException("error loading " + line, e);
			}

			objects.put(newObject.getId(), newObject);

			if (newObject.getId() == 0) {
				rootObject = newObject;
				rootObject.scope = ObjectScope.Persistent;
			}

			if (newObject.getId() > maxObjectId)
				maxObjectId = newObject.getId();

		}

		// reset object id
		objectId = maxObjectId + 1;

		// linking
		for (_Object o : objects.values()) {

			_Object owner = objects.get(o.getOwner().getId());

			// remove the owner holder
			o.setOwner(owner);

			for (Field f : o.getFields()) {
				_Object target = objects.get(f.target.getId());
				f.target = target;
			}

			o.updateFieldIndexes();
		}

		logger.info("objects loaded");

		// notify
		for (_Object o : objects.values()) {
			for (ObjectRepositoryListener l : listeners)
				l.afterLoad(o);
		}

	}

	public Collection<_Object> getAllObjects() {
		return objects.values();
	}

	public void addListener(ObjectRepositoryListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ObjectRepositoryListener listener) {
		listeners.remove(listener);
	}
}
