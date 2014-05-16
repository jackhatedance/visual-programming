package com.bluesky.visualprogramming.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.serialization.ConfigurableObjectSerializer;
import com.bluesky.visualprogramming.core.serialization.MessageFormat;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;
import com.bluesky.visualprogramming.utils.Config;

public class ObjectRepository {

	static Logger logger = Logger.getLogger(ObjectRepository.class);

	static String DEFAULT_VIEW_POSITION = "DEFAULT_VIEW_POSITION";

	public static String ROOT_OBJECT = "_world";
	public static String USERS = "users";
	public static String PROTOTYPE_PATH = ROOT_OBJECT + ".core.prototype";

	volatile long objectId;

	_Object rootObject;

	private List<ObjectRepositoryListener> listeners = new ArrayList<ObjectRepositoryListener>();

	/**
	 * a convenient way to create runtime object
	 */
	private BasicObjectFactory factory;

	private ObjectTreeModel objectTreeModel = new ObjectTreeModelImpl();

	public ObjectRepository() {
		// start from 0
		objectId = 0;

		rootObject = createObject(ObjectType.NORMAL, ObjectScope.Persistent);
		rootObject.setName(ObjectRepository.ROOT_OBJECT);

		// my listeners
		ObjectRepositoryListener migrationListener = new AbstractObjectRepositoryListener() {
			@Override
			public void beforeSave(_Object obj) {

			}

		};

		// this listener is used to modify object data in batch for migration
		// purpose. enable it only when necessary.
		listeners.add(migrationListener);

		factory = new BasicObjectFactory() {
			@Override
			public _Object create(ObjectType type) {
				return ObjectRepository.this.createObject(type,
						ObjectScope.ExecutionContext);

			}

			@Override
			public StringValue createString(String value) {
				StringValue obj = (StringValue) ObjectRepository.this
						.createObject(ObjectType.STRING,
								ObjectScope.ExecutionContext);
				obj.setValue(value);
				return obj;
			}

			@Override
			public _Object createObject() {
				return ObjectRepository.this.createObject(ObjectType.NORMAL,
						ObjectScope.ExecutionContext);
			}

			@Override
			public IntegerValue createInteger(long value) {
				IntegerValue obj = (IntegerValue) ObjectRepository.this
						.createObject(ObjectType.INTEGER,
								ObjectScope.ExecutionContext);
				obj.setIntValue(value);
				return obj;
			}

			@Override
			public BooleanValue createBoolean(boolean value) {
				BooleanValue obj = (BooleanValue) ObjectRepository.this
						.createObject(ObjectType.BOOLEAN,
								ObjectScope.ExecutionContext);
				obj.setBooleanValue(value);
				return obj;
			}

			@Override
			public Link createLink(String address) {
				Link link = (Link) ObjectRepository.this.createObject(
						ObjectType.LINK, ObjectScope.ExecutionContext);
				link.setValue(address);

				return link;
			}

			@Override
			public TimeValue createTime() {
				TimeValue time = (TimeValue) ObjectRepository.this
						.createObject(ObjectType.TIME,
								ObjectScope.ExecutionContext);
				return time;
			}

			@Override
			public VException createException(String message) {
				VException obj = (VException) ObjectRepository.this
						.createObject(ObjectType.EXCEPTION,
								ObjectScope.ExecutionContext);
				obj.setMessage(message);
				return obj;
			}
		};
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

		owner.setField(name, newObject, true);

		for (ObjectRepositoryListener l : listeners) {
			l.afterCreate(newObject);
		}

		return newObject;
	}

	/**
	 * e.g. root.abc.xyz
	 * 
	 * @param path
	 * @return
	 */
	public _Object getObjectByPath(String path) {
		String[] ss = path.split("\\.");

		if (!ss[0].equals(ObjectRepository.ROOT_OBJECT))
			throw new RuntimeException("the first object must be '"
					+ ObjectRepository.ROOT_OBJECT + "':" + path);

		int index = path.indexOf('.');
		if (index < 0)
			return getRootObject();
		else {
			String subPath = path.substring(index + 1);

			return getRootObject().getObjectByPath(subPath);
		}
	}

	public void destroyObject(_Object obj) {
		if (obj.getOwner() != null) {
			obj.getOwner().removeChild(obj);
			// obj.setOwner(null);
		}

	}

	public _Object getRootObject() {
		return rootObject;
	}

	public void load(String runtimeFileName, String userFileName) {
		// runtime
		loadAndAttach("", runtimeFileName);

		loadAndAttach(ROOT_OBJECT, userFileName);
	}

	public void save(String runtimeFileName, String userFileName) {
		if (logger.isDebugEnabled())
			logger.debug("enter save()");

		// to update fields , such as pointer path

		if (logger.isDebugEnabled())
			logger.debug("before beforeSaveXml()");

		beforeSaveXml(rootObject);

		if (logger.isDebugEnabled())
			logger.debug("after beforeSaveXml()");

		_Object users = detach(ROOT_OBJECT + "." + USERS);
		if (userFileName != null) {
			if (logger.isDebugEnabled())
				logger.debug("before saveXml(): users.xml");

			saveXml(users, userFileName);

			if (logger.isDebugEnabled())
				logger.debug("after saveXml(): users.xml");
		}

		_Object root = getObjectByPath(ROOT_OBJECT);
		if (runtimeFileName != null) {
			if (logger.isDebugEnabled())
				logger.debug("before saveXml() : runtime.xml");

			saveXml(root, runtimeFileName);

			if (logger.isDebugEnabled())
				logger.debug("after saveXml(): runtime.xml");
		}

		// attach users
		root.setField(USERS, users, true);
	}

	private void loadAndAttach(String mountOwnerPath, String fileName) {
		_Object mountPoint = loadXml(fileName);
		if (mountOwnerPath != null && !mountOwnerPath.isEmpty()) {
			_Object mountOwner = getObjectByPath(mountOwnerPath);

			mountOwner.setField(mountPoint.getName(), mountPoint, true);
		} else {
			rootObject = mountPoint;
		}

		afterLoadXml(mountPoint);
	}

	/**
	 * pre-process user objects, detach and save.
	 * 
	 * @param fileName
	 */
	private _Object detach(String mountPointPath) {
		_Object mountPoint = getObjectByPath(mountPointPath);

		if (mountPoint.hasOwner()) {
			_Object owner = mountPoint.getOwner();
			Field ownerField = mountPoint.getOwnerField();

			mountPoint.detachFromOwner();

			String fieldName = ownerField.name;
			owner.removeField(fieldName);
		}

		// saveXml(mountPoint, fileName);
		return mountPoint;
	}

	private void saveXml(_Object object, String fileName) {

		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(fileName),
					"utf-8");
			saveXml(object, w);

		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	/**
	 * it just save whole object tree, assuming user objects has been detached.
	 * 
	 * @param writer
	 */
	private void saveXml(_Object obj, Writer writer) {

		ConfigurableObjectSerializer serializer = MessageFormat.Xstream
				.getSerializer();
		try {

			serializer.serialize(obj, writer, new Config());
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private _Object loadXml(String fileName) {
		ConfigurableObjectSerializer serializer = MessageFormat.Xstream
				.getSerializer();
		try {
			Reader r = new InputStreamReader(new FileInputStream(fileName),
					"utf-8");

			_Object root = serializer.deserialize(r, new Config());
			return root;
			// registerObject(rootObject);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	interface TreeWalker {
		void walk(_Object obj);
	}

	private void treeWalk(_Object obj, TreeWalker walker) {
		walker.walk(obj);
		if (obj.hasChildren()) {
			for (Field f : obj.getFields()) {

				if (f.getType() == FieldType.STRONG && f.getTarget() != null)
					treeWalk(f.getTarget(), walker);
			}
		}
	}

	/**
	 * pre-process objects.
	 * 
	 * remove backward references
	 * 
	 * convert pointer to softlink
	 * 
	 * @param mountPoint
	 */
	private void beforeSaveXml(_Object mountPoint) {
		logger.debug("before save image.");

		logger.debug("check consistency");
		// check consistency
		treeWalk(mountPoint, new TreeWalker() {
			@Override
			public void walk(_Object obj) {
				// System.out.println("beforeSave" + obj.getPath());
				for (int i = 0; i < obj.getFields().size(); i++) {
					Field f = obj.getField(i);

					if (f.getType() == FieldType.WEAK
							&& f.getStrongTarget() != null)
						throw new RuntimeException(
								"field inconsistency: weak but has strong reference."
										+ f.owner.getPath() + "." + f.getName());

					if (f.getType() == FieldType.STRONG
							&& f.getTarget() != null) {
						if (f.getTarget().getOwnerField() == null)
							logger.warn("target.ownerField is null."
									+ obj.getPath() + "." + f.getName());
					}
				}

			}
		});

		logger.debug("calculate field target path.");
		treeWalk(mountPoint, new TreeWalker() {
			@Override
			public void walk(_Object obj) {

				for (int i = 0; i < obj.getFields().size(); i++) {
					Field f = obj.getField(i);

					// calculate field target path so that we can serialize it
					if (f.getType() == FieldType.WEAK) {
						if (f.getTarget() != null) {
							f.pointerPath = f.getTarget().getPath();
							if (f.pointerPath == null) {

								logger.warn(String
										.format("weak reference but path is null, %s.%s(#%d)",
												obj.getPath(), f.getName(), obj.getId()));
							}

							if (!f.pointerPath.startsWith(ROOT_OBJECT))
								System.out.println(f.pointerPath);

							// f.setWeakTarget(null);
						} else {
							// maybe the target has been destroyed.
							logger.warn("empty weak reference found:"
									+ obj.getPath() + "." + f.getName());
						}
					}

				}

			}
		});

		// notify
		logger.debug("notify registered listener.");
		treeWalk(mountPoint, new TreeWalker() {
			@Override
			public void walk(_Object obj) {
				for (ObjectRepositoryListener l : listeners)
					l.beforeSave(obj);
			}
		});

	}

	/**
	 * 
	 * @param obj
	 *            the root object of the mounting tree
	 */
	private void afterLoadXml(_Object obj) {
		logger.info("objects loaded, start linking");

		treeWalk(obj, new TreeWalker() {
			@Override
			public void walk(_Object obj) {
				// System.out.println("beforeSave" + obj.getPath());
				for (int i = 0; i < obj.getFields().size(); i++) {
					Field f = obj.getField(i);

					// check
					if (f.getType() == FieldType.WEAK
							&& f.getStrongTarget() != null) {
						// f.setStrongTarget(null);

						throw new RuntimeException(
								"field inconsistency: weak but has strong reference. owner ID:"
										+ f.owner.getId());

					}
				}

			}
		});

		// restore backward pointers, reference field
		treeWalk(obj, new TreeWalker() {
			@Override
			public void walk(_Object obj) {

				// get the max object id
				if (obj.getId() > objectId)
					objectId = obj.getId() + 1;

				for (int i = 0; i < obj.getFields().size(); i++) {
					Field f = obj.getField(i);

					// restore owner of field
					f.owner = obj;

					if (f.getType() == FieldType.WEAK) {

						// restore pointer field
						if (f.pointerPath != null
								&& f.pointerPath.startsWith(ROOT_OBJECT)) {

							f.setWeakTarget(ObjectRepository.this
									.getObjectByPath(f.pointerPath));
						} else {
							System.out.println("invalid target path:"
									+ f.pointerPath + ",owner object ID:"
									+ obj.getId() + ",field name:" + f.name);
						}

					} else if (f.getType() == FieldType.STRONG) {
						// restore field of child object.
						if (f.getTarget() != null) {

							f.getTarget().setOwnerField(f);

						} else
							System.out.println("null field:" + f.getName());

					} else {
						f.setType(FieldType.WEAK);
						System.out.println("null field type:"
								+ f.owner.getPath() + "." + f.getName());
						// throw new RuntimeException("field type is null");
					}
				}

			}
		});

		// re-sort fields
		treeWalk(obj, new TreeWalker() {
			@Override
			public void walk(_Object obj) {
				obj.sortFields();
			}
		});

		// notify
		treeWalk(obj, new TreeWalker() {
			@Override
			public void walk(_Object obj) {
				for (ObjectRepositoryListener l : listeners)
					l.afterLoadFromFile(obj);
			}
		});

		treeWalk(obj, new TreeWalker() {
			@Override
			public void walk(_Object obj) {
				for (ObjectRepositoryListener l : listeners)
					l.onStartService(obj);
			}
		});

	}

	public void addListener(ObjectRepositoryListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ObjectRepositoryListener listener) {
		listeners.remove(listener);
	}

	public BasicObjectFactory getFactory() {
		return factory;
	}
}
