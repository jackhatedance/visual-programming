package com.bluesky.visualprogramming.core;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.procedure.SubjectMatchType;
import com.bluesky.visualprogramming.core.procedure.SubjectMatcher;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.ui.SVGDiagramPanel;
import com.bluesky.visualprogramming.ui.svg.SvgScene;
import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.VirtualMachine;
import com.bluesky.visualprogramming.vm.message.Worker;

public class _Object implements Serializable {
	static Logger logger = Logger.getLogger(_Object.class);

	// keep system in one folder.
	static public final String SYSTEM = "_system";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1809740541459285761L;

	protected ObjectType type = ObjectType.NORMAL;
	private long id;

	/*
	 * the called by itself. maybe it is the type name.
	 */
	private String name;

	private String description;

	/**
	 * only not null if it has owner.
	 */
	private Field ownerField;

	/**
	 * just for editing image XML.
	 */
	public String pathForDebug;

	/**
	 * only those has root in persistent repository are persistent. messages are
	 * not persistent.
	 * 
	 * always set to executionContext, except the root object is set to
	 * persistent.
	 */
	public ObjectScope scope = ObjectScope.ExecutionContext;

	// in Z order
	private List<Field> fieldList = new ArrayList<Field>();
	private int systemFieldsCount = 0;
	// index names to accelerate access speed
	private Map<String, Integer> fieldNameMap;


	/**
	 * the normal message queue, which are OK to run
	 * 
	 * the worker only work on this queue.
	 */
	private Deque<Message> messageQueue;
	/**
	 * blocking procedures,
	 */
	private Deque<Message> messageQueueBlocking;

	private Worker worker = null;

	/**
	 * key is message Id of async request, value is message of incoming request.
	 * if it received a request message, in order to process it, it raise
	 * another async request to other object. later the async reply comes, we
	 * use this map to get the incoming request, so that we can prepare the
	 * reply message (if it need reply). after the async reply is processed, it
	 * will be removed.
	 */
	private Map<String, Message> asynMessageMap = new HashMap<String, Message>();

	/**
	 * registered itself to the worker manager, set to true if worker assigned.
	 */
	private boolean applyingWorker = false;

	public Color borderColor;

	public _Object(long id) {
		this.id = id;

		borderColor = Color.BLACK;

	}

	/**
	 * copy constructor
	 * 
	 * @param newObj
	 */
	public void copy(_Object src, boolean deep, BasicObjectFactory factory) {

		this.type = src.type;
		// this.id = id;
		this.name = src.name;
		this.description = src.description;
		this.scope = src.scope;

		for (Field oldField : src.getFields()) {

			// boolean owns = oldField.getType() == FieldType.STRONG;

			_Object target = null;

			if (deep) {
				if (oldField.getTarget() != null)
					target = oldField.getTarget().clone(deep, factory);
			} else
				target = oldField.getTarget();

			Field newField = new Field(oldField, target);
			addField(newField, target);
		}

		// messageQueue is skipped

		// always idle
		this.worker = null;

		// this.area = new Rectangle(src.area);

		// this.scaleRate = src.scaleRate;
		this.borderColor = src.borderColor;

	}

	public _Object clone(boolean deep, BasicObjectFactory factory) {
		_Object obj = factory.create(type);
		obj.copy(this, deep, factory);
		return obj;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasRealName() {
		return !(name == null || name.equals("unnamed") || name
				.startsWith("t_"));
	}

	public ObjectType getType() {
		return type;
	}

	public _Object getOwner() {
		if (ownerField != null)
			return ownerField.owner;
		else
			return null;
	}

	public String getPath() {
		if (hasOwner()) {
			return getOwner().getPath() + "." + ownerField.name;
		} else
			return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * re-create indexes.
	 */
	protected void rebuildFieldIndexes() {
		fieldNameMap = new HashMap<String, Integer>();


		for (int i = 0; i < fieldList.size(); i++) {
			Field f = fieldList.get(i);
			fieldNameMap.put(f.name, i);

		}

	}

	public Field getField(int index) {
		return fieldList.get(index);
	}

	public int getFieldCount() {
		return fieldList.size();
	}

	/**
	 * save or update a field. also take care of old object when updating.
	 * 
	 * @param child
	 * @param name
	 * @param own
	 */
	public void setField(String name, _Object child, boolean owns) {

		if (child != null && child.hasOwner() && owns) {

			if (child.getScope() == ObjectScope.ExecutionContext)
				child.downgradeLinkFromOwner();
			else
				throw new RuntimeException(
						"can not own an object which has persistent owner.");
		}

		// name must be unique if is not null
		if (name != null) {
			Field f = getField(name);

			if (f == null) {
				f = new Field(name, owns);
				fieldList.add(f);

				f.owner = this;

				f.setTarget(child);

				sortFields();

			} else {
				if (f.getTarget() != null)
					f.detachTarget();

				// re-assure
				f.owner = this;
				// set type
				f.setType(FieldType.getType(owns));
				f.attachTarget(child);
			}

		} else
			throw new RuntimeException("field name cannot be null");

	}

	private void addField(Field field, _Object target) {

		fieldList.add(field);
		field.owner = this;
		field.setTarget(target);
		sortFields();

	}

	public ObjectScope getScope() {

		if (hasOwner())
			return getOwner().getScope();
		else
			return scope;

	}

	public void setScope(ObjectScope scope) {
		this.scope = scope;
	}

	public boolean hasOwner() {

		return ownerField != null && ownerField.owner != null;
	}

	public void renameField(String old, String _new) {

		Integer index = fieldNameMap.get(old);
		if (index == null)
			throw new RuntimeException("source field not exist:" + old);

		if (fieldNameMap.containsKey(_new)) {
			throw new RuntimeException("destination field already exist:"
					+ _new);
		}

		fieldList.get(index).setName(_new);

		fieldNameMap.remove(old);
		fieldNameMap.put(_new, index);

	}


	public void removeField(String name) {
		Integer childIndex = fieldNameMap.get(name);
		if (childIndex == null)
			throw new RuntimeException("child not found:" + name);

		removeField(childIndex);
	}

	public void removeField(Integer index) {

		if (index == null)
			return;

		int idx = index.intValue();

		Field field = fieldList.get(idx);
		fieldList.remove(idx);

		rebuildFieldIndexes();

		if (field.getTarget() != null && owns(field.getTarget()))
			detachOwnedChild(field.getTarget());

	}

	/**
	 * it won't remove the original field. just remove the owner.
	 * 
	 * TODO but how can a field point to a non-persistent object?
	 * 
	 * @param child
	 */
	public void detachOwnedChild(_Object child) {
		if (owns(child)) {
			// child.setOwner(null);
			child.detachFromOwner();
			// child.setScope(ObjectScope.ExecutionContext);
		}
	}

	public void detachFromOwner() {
		ownerField.detachTarget();
	}

	/**
	 * a owning relationship become pointer relationship.
	 */
	public void downgradeLinkFromOwner() {
		Field theOwnerField = ownerField;

		detachFromOwner();

		theOwnerField.setType(FieldType.WEAK);
		theOwnerField.attachTarget(this);

	}

	/**
	 * attach to new owner.
	 * 
	 * @param owner
	 */
	public void attachToOwner(_Object owner, String name) {
		Field f = owner.getField(name);

		attachTo(f);
	}

	public void attachTo(Field field) {
		this.ownerField = field;
		// this.scope = null;
	}

	public boolean hasFields() {

		return !fieldList.isEmpty();

	}

	public List<Field> getFields() {

		return this.fieldList;

	}

	/**
	 * textual value for persist
	 * 
	 * @return
	 */
	public String getValue() {
		return "";
	}

	/**
	 * human readable text
	 * 
	 * @return
	 */
	public String getHumanReadableText() {
		return getValue();
	}

	public void setValue(String value) {
		// it is not a value object, the value must be null;
		if (value != null && !value.isEmpty())
			throw new RuntimeException("param 'value' is not empty.");
	}

	@Override
	public String toString() {

		return name;
	}

	/**
	 * draw on SVG DOM
	 * 
	 * @param doc
	 * @param canvasOffset
	 */
	public void drawInternal(SVGDiagramPanel diagramPanel, SvgScene scene,
			Point canvasOffset, ObjectRepository repo) {

		ObjectLayout layout = ObjectLayout.XY;
		StringValue layoutSV = (StringValue) getSystemTopChild(SystemField.Layout);
		if (layoutSV != null) {
			try {
				layout = ObjectLayout.valueOf(layoutSV.getValue());
			} catch (Exception e) {
				layout = ObjectLayout.XY;// default layout
			}
		}

		layout.preprocess(this);

		// System.out.println("drawInternal:" + name + ",id=" + id);

		for (Field field : fieldList) {

			// System.out.println("drawInternal, fieldName=" + field.name);

			boolean owns = field.getTarget() != null
					&& field.getType() == FieldType.STRONG;

			_Object target = field.getTarget();
			if (target != null) {
				_Object proto = target.getPrototype(repo);
				String objName = null;
				if (proto != null) {
					objName = String.format("%s<%s>", field.name, proto.name);
				} else
					objName = field.name;

				field.draw(diagramPanel, scene, canvasOffset, owns);
			}
		}

	}

	protected Field getField(String name) {

		Integer index = getFieldNameMap().get(name);
		if (index != null)
			return fieldList.get(index);
		else
			return null;

	}

	public _Object getChild(String name) {

		Field field = getField(name);
		if (field != null)
			return field.getTarget();
		else
			return null;
	}

	public _Object getSystemConfig(SystemField systemField, String name) {
		_Object field = getSystemTopChild(systemField);
		if (field != null)
			return field.getChild(name);
		else
			return null;
	}

	public _Object getSystemTopChild(SystemField systemField) {
		return getSystemChild(systemField.getFieldName());
	}

	protected _Object getSystemChild(String name) {
		_Object systemObject = getSystem();
		if (systemObject != null)
			return systemObject.getChild(name);
		else
			return null;
	}

	public void setSystemTopField(SystemField systemField, _Object obj,
			boolean owner) {
		setSystemField(systemField.getFieldName(), obj, owner);
	}

	protected void setSystemField(String name, _Object obj, boolean owner) {
		_Object systemObject = getSystem();
		if (systemObject == null)
			systemObject = createSystemField();

		systemObject.setField(name, obj, owner);

	}

	public String[] getFieldNames() {

		List<String> names = new ArrayList<String>();
		for (Field field : fieldList) {
			if (!field.isSystemField())
				names.add(field.name);
		}

		return names.toArray(new String[0]);

	}

	/**
	 * support prototype
	 * 
	 * @param name
	 * @return
	 */
	public Procedure lookupProcedure(Message message, ObjectRepository repo) {

		String subject = message.getSubject();
		// first try match the procedure name
		Procedure p = (Procedure) getChild(subject);

		// not found, try match rule
		if (p == null) {
			if (logger.isDebugEnabled())
				logger.debug("no procedure found by message subject " + subject);

			BooleanValue enableMatch = (BooleanValue) getSystemConfig(
					SystemField.SubjectMatch, "enable");

			if (enableMatch != null && enableMatch.getBooleanValue() == true) {
				if (logger.isDebugEnabled())
					logger.debug("extended subject match is enabled...");

				// default setting of the object
				SubjectMatchType defaultSubjectMatchType = SubjectMatchType.RegularExpression;
				StringValue defaultSubjectMatchTypeSV = (StringValue) getSystemConfig(
						SystemField.SubjectMatch, "type");
				if (defaultSubjectMatchTypeSV != null)
					defaultSubjectMatchType = SubjectMatchType
							.valueOf(defaultSubjectMatchTypeSV.getValue());

				if (logger.isDebugEnabled())
					logger.debug("object match type is"
							+ defaultSubjectMatchType);

				for (Field field : fieldList) {
					_Object child = field.getTarget();

					// _Object childSubjectMatch =
					// child.getSystemChild(SystemField.SubjectMatch);

					StringValue messageSubjectMatchRule = (StringValue) child
							.getSystemConfig(SystemField.SubjectMatch, "rule");

					if (messageSubjectMatchRule != null) {
						// procedure specific setting
						SubjectMatchType subjectMatchType = defaultSubjectMatchType;
						StringValue subjectMatchTypeSV = (StringValue) child
								.getSystemConfig(SystemField.SubjectMatch,
										"type");

						if (subjectMatchTypeSV != null)
							subjectMatchType = SubjectMatchType
									.valueOf(subjectMatchTypeSV.getValue());

						if (logger.isDebugEnabled())
							logger.debug("procedure match type is"
									+ subjectMatchType);

						if (logger.isDebugEnabled())
							logger.debug("try matching "
									+ messageSubjectMatchRule.getValue());

						try {

							SubjectMatcher subjectMatcher = subjectMatchType
									.getMatcher(messageSubjectMatchRule
											.getValue());

							boolean result = subjectMatcher.matches(subject);

							if (result == true) {

								subjectMatcher.postProcess(message);

								p = (Procedure) child;

								if (logger.isDebugEnabled())
									logger.debug("procedure matched:"
											+ field.name);

								break;
							}
						} catch (Exception e) {
							// error when evaluate:
							logger.warn(
									"failed to evaluate message subject match rule.",
									e);
							continue;
						}
					}
				}
			}
		}

		// still not found, try prototype
		if (p == null) {
			_Object prototype = getPrototype(repo);
			// prototype cannot be itself.
			if (prototype != null && prototype != this) {
				// prototype field only support local object.
				// finding procedure on remote machine is too slow.
				if (prototype instanceof Link) {

					Link link = (Link) prototype;

					if (link.getRemoteAddress() != null
							&& ProtocolType.PATH.toString().equalsIgnoreCase(
									link.getRemoteAddress().protocol)) {
						_Object target = repo.getObjectByPath(link
								.getRemoteAddress().userId);

						prototype = target;
					}

				}
				// else find procedure on link object, obviously get
				// nothing.

				p = prototype.lookupProcedure(message, repo);
			}
		}

		return p;

	}

	private _Object getSystem() {
		return getChild(SYSTEM);
	}

	public _Object getPrototype(ObjectRepository repo) {

		_Object proto = getSystemTopChild(SystemField.Prototype);

		if (proto == null) {
			// optimization for predefined objects
			String path = type.getPrototypeEL();
			if (path != null) {
				proto = repo.getObjectByPath(path);
			}
		}
		return proto;
	}

	private _Object createSystemField() {
		VirtualMachine vm = VirtualMachine.getInstance();
		return vm.getObjectRepository().createObject(this, SYSTEM,
				ObjectType.NORMAL);
	}

	public void setPrototype(_Object obj) {
		setSystemTopField(SystemField.Prototype, obj, false);
	}

	public CompiledProcedure getCompiledProcedure(Procedure procedure) {
		if (procedure.compiled == null) {
			compiledProcedure(procedure);
		}
		return procedure.compiled;
	}

	public void compiledProcedure(Procedure procedure) {

		LanguageType type = LanguageType.valueOf(procedure.language
				.toUpperCase());
		if (type == null)
			throw new RuntimeException("unsupported language:"
					+ procedure.language);

		try {
			CompiledProcedure cp = type.getCompiler().compile(procedure.code);

			if (logger.isDebugEnabled())
				logger.debug(cp.getInstructionText());

			procedure.compiled = cp;

		} catch (Exception e) {
			String msg2 = String.format("compile failed for %s.%s(): %s",
					getPath(), procedure.getName(), e.getMessage());
			throw new RuntimeException(msg2, e);

		}

	}

	/**
	 * 
	 * @param msg
	 * @return true if need a worker
	 */
	public synchronized boolean addToMessageQueue(Message msg) {
		if (messageQueue == null)
			messageQueue = new ArrayDeque<Message>();

		String pos = "";
		if (msg.urgent || msg.isSyncReply() || msg.sender == this) {
			pos = "head";
			messageQueue.addFirst(msg);
		} else {
			pos = "tail";
			messageQueue.addLast(msg);
		}

		boolean applyWorkerForMe = false;

		if (worker == null && !applyingWorker) {
			applyingWorker = true;
			applyWorkerForMe = true;
		}

		if (logger.isDebugEnabled()) {

			logger.debug(String
					.format("a message added to %s's queue %s, subject: %s, need-worker:%s",
							name, pos, msg.getSubject(), applyWorkerForMe));
		}

		return applyWorkerForMe;
	}

	public synchronized void assignWorkerIfNot(Worker worker) {
		if (this.worker != null)
			throw new RuntimeException("worker already assigned");

		this.worker = worker;

		this.applyingWorker = false;
	}

	public synchronized void removeWorker() {
		this.worker = null;
	}

	public synchronized boolean hasWorker() {
		return this.worker != null;
	}

	public synchronized boolean hasWorkableMessage() {
		return !messageQueue.isEmpty();
	}

	public synchronized Message peekFirstMessage() {
		return messageQueue.peekFirst();
	}

	public synchronized boolean removeMessage(Message msg) {
		return messageQueue.remove(msg);
	}

	public synchronized void addFirstMessage(Message msg) {
		messageQueue.addFirst(msg);
	}

	public synchronized void addLastMessage(Message msg) {
		messageQueue.addLast(msg);
	}

	public synchronized int getMessageQueueSize() {
		return messageQueue.size();
	}

	public synchronized void printMessageQueue() {
		Iterator<Message> it = messageQueue.iterator();

		while (it.hasNext()) {
			Message m = it.next();
			System.out.println(m.toString());
		}
	}

	public synchronized void moveMessageToBlockingQueue(Message msg) {
		// lazy init
		if (messageQueueBlocking == null)
			messageQueueBlocking = new ArrayDeque<Message>();

		messageQueue.remove(msg);
		msg.executionContext.setExecutionStatus(ExecutionStatus.WAITING);
		messageQueueBlocking.add(msg);
	}

	public synchronized void moveMessageToRunnableQueue(Message msg) {
		messageQueueBlocking.remove(msg);
		msg.executionContext.setExecutionStatus(ExecutionStatus.RUNNING);
		messageQueue.add(msg);
	}

	public boolean owns(_Object child) {
		if (child != null)
			return child.getOwner() == this;
		else
			return false;
	}

	/**
	 * rearrange the field list, system field are on top. user fields are sorted
	 * with comparator. and update the system field count.
	 * 
	 * @param comparator
	 */

	protected void sortFields() {
		List<Field> systemFields = new ArrayList<Field>();

		List<Field> userFields = new ArrayList<Field>();

		for (Field f : fieldList) {
			if (f.isSystemField())
				systemFields.add(f);
			else
				userFields.add(f);
		}

		fieldList.clear();

		fieldList.addAll(systemFields);
		fieldList.addAll(userFields);

		systemFieldsCount = systemFields.size();

		rebuildFieldIndexes();
	}

	public void sortFields(Comparator<Field> comparator) {

		List<Field> systemFields = new ArrayList<Field>();

		TreeSet<Field> userFields = new TreeSet<Field>(comparator);

		for (Field f : fieldList) {
			if (f.isSystemField())
				systemFields.add(f);
			else
				userFields.add(f);
		}

		fieldList.clear();

		fieldList.addAll(systemFields);
		fieldList.addAll(userFields);

		systemFieldsCount = systemFields.size();

		rebuildFieldIndexes();

	}

	public int getUserFieldsCount() {

		return fieldList.size() - systemFieldsCount;

	}

	/**
	 * 
	 * @param path
	 *            separated by dot
	 * @return
	 */
	public _Object getObjectByPath(String path) {
		String[] ss = path.split("\\.");

		_Object obj = this;

		for (int i = 0; i < ss.length; i++) {
			obj = obj.getChild(ss[i]);

			if (obj == null)
				return null;
		}

		return obj;
	}

	/**
	 * support visitor pattern
	 * 
	 * @param visitor
	 */
	public void accept(ObjectVisitor visitor) {

		visitor.enter(this);

		// children
		for (Field f : fieldList) {
			f.accept(visitor);

		}

		visitor.leave(this);

	}

	public Field getOwnerField() {

		return ownerField;
	}

	/**
	 * throw exception when already linked to a owner field.
	 * 
	 * @param ownerField
	 */
	public void setOwnerField(Field ownerField) {
		if (this.ownerField != null)
			throw new RuntimeException(
					"object already linked to an owner field.");

		this.ownerField = ownerField;
	}

	public void removeOwnerField() {

		if (this.ownerField == null)
			throw new RuntimeException(
					"no owner field linked to this object, ID:" + getId());

		this.ownerField = null;
	}

	private Map<String, Integer> getFieldNameMap() {
		if (fieldNameMap == null)
			rebuildFieldIndexes();

		return fieldNameMap;
	}



}
