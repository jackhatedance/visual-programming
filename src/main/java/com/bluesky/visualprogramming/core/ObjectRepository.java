package com.bluesky.visualprogramming.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;

public class ObjectRepository {

	static String DEFAULT_VIEW_POSITION = "DEFAULT_VIEW_POSITION";

	static String DUMP_FILE = "objects";

	long objectId;
	Map<Long, _Object> objects = new HashMap<Long, _Object>();;
	_Object rootObject;

	 
	public ObjectRepository() {
		// start from 0
		objectId = 0;

		rootObject = createObject(null, "root");
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

	public _Object createObject(_Object owner) {

		return createObject(owner, generateNewChildName(owner),
				ObjectType.DEFAULT);
	}

	public _Object createObject(_Object owner, String name) {

		return createObject(owner, name, ObjectType.DEFAULT);
	}

	public _Object createObject(_Object owner, ObjectType type) {
		return createObject(owner, generateNewChildName(owner), type, "");
	}

	public _Object createObject(_Object owner, String name, ObjectType type) {
		return createObject(owner, name, type, "");
	}

	public _Object createObject(_Object owner, String name, ObjectType type,
			String value) {
		_Object newObject = type.create(objectId++);

		newObject.setName(name);
		newObject.setValue(value);

		if (owner != null)
			owner.addChild(newObject, name);

		objects.put(newObject.getId(), newObject);

		return newObject;
	}

	public void destroyObject(long id) {
		_Object obj = objects.get(id);
		if (obj == null)
			throw new RuntimeException("delete object, object id not found");

		destroyObject(obj);
	}

	public void destroyObject(_Object obj) {
		if(obj.getOwner()!=null)
		{
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

	public void save(Writer writer) {

		try {
			for (_Object obj : objects.values()) {
				writer.write(obj.toText());
				writer.write("\n");
			}
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

	}

	public void load(List<String> lines) {
		objects.clear();

		for (String line : lines) {

			Map<String, String> map = KeyValueStringUtils.parse(line);
			String typeLiteral = map.get("type");
			ObjectType type = ObjectType.valueOf(typeLiteral);
			_Object newObject = type.create(objectId++);

			newObject.fromText(line);
			objects.put(newObject.getId(), newObject);

			if (newObject.getId() == 0)
				rootObject = newObject;
		}

		// linking
		for (_Object o : objects.values()) {

			_Object owner = objects.get(o.getOwner().getId());

			o.setOwner(owner);

			if (owner != null)
				owner.addChild(o, o.getName());
		}
	}

	public void loadSampleObjects() {

		_Object user = createObject(rootObject, "user");

		_Object name = createObject(user, "name", ObjectType.STRING, "jack");
		name.area.x += 200;

		_Object age = createObject(user, "age", ObjectType.INTEGER, "10");
		age.area.y += 200;

		_Object desc = createObject(user, "desc", ObjectType.STRING,
				"I'm a programmer that name=,./=-0@#$%^^&*&*((");
		desc.area.y += 300;

	}
}
