package com.bluesky.visualprogramming.core;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.bluesky.visualprogramming.vm.VirtualMachine;
import com.bluesky.visualprogramming.vm.message.Worker;

public class _Object implements Serializable {
	static Logger logger = Logger.getLogger(_Object.class);

	// keep system in one folder.
	static public final String SYSTEM = "_system";

	static private final String PROTOTYPE = "prototype";
	static private final String ENABLE_SUBJECT_MATCH = "enableSubjectMatch";
	static private final String SUBJECT_MATCH_RULE = "subjectMatchRule";

	// it is defined by the object, and is for its procedures.
	static public final String DEFAULT_SUBJECT_MATCH_TYPE = "defaultSubjectMatchType";
	// it is defined by the procedure itself, to override the default value of
	// the owner object.
	static public final String SUBJECT_MATCH_TYPE = "subjectMatchType";

	static public final String GRAPHIC = "graphic";
	static public final String OBJECT_LAYOUT = "layout";
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
	public Field field;

	/**
	 * only those has root in persistent repository are persistent. messages are
	 * not persistent.
	 */
	public ObjectScope scope = null;

	// in Z order
	private List<Field> fieldList = new ArrayList<Field>();
	private int systemFieldsCount = 0;

	// index names to accelerate access speed
	private Map<String, Integer> fieldNameMap = new HashMap<String, Integer>();
	private Map<_Object, Integer> childrenObjectMap = new HashMap<_Object, Integer>();

	private Deque<Message> messageQueue;
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

	// this scale is about the interal diagram of the object we are watching,
	// not the svg shape from the orginal size.
	public float scaleRate = 1f;
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
	public _Object(long id, _Object src) {
		this.id = id;

		this.type = src.type;
		// this.id
		this.name = src.name;
		// this.owner

		for (Field p : src.fieldList) {

			boolean owns = p.type == FieldType.Branch;

			setField(p.name, p.target, owns);
		}

		// messageQueue is skipped

		// always idle
		this.worker = null;

		// this.area = new Rectangle(src.area);

		this.scaleRate = src.scaleRate;
		this.borderColor = src.borderColor;

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
		if (field != null)
			return field.owner;
		else
			return null;
	}

	public String getPath() {
		if (hasOwner()) {
			int index = getOwner().getChildIndex(this);
			Field field = getOwner().getField(index);
			return getOwner().getPath() + "." + field.name;
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
	protected void recreateFieldIndexes() {
		fieldNameMap = new HashMap<String, Integer>();
		childrenObjectMap = new HashMap<_Object, Integer>();
		for (int i = 0; i < fieldList.size(); i++) {
			Field f = fieldList.get(i);
			fieldNameMap.put(f.name, i);
			childrenObjectMap.put(f.target, i);
		}
	}

	/**
	 * if there is no named field. then it become an array.
	 * 
	 * @return
	 */
	public boolean hasNamedField() {
		return !fieldNameMap.isEmpty();
	}

	public _Object getChild(int index) {
		return fieldList.get(index).target;
	}

	public Field getField(int index) {
		return fieldList.get(index);
	}

	public int getChildCount() {
		return fieldList.size();
	}

	public int getChildIndex(_Object child) {
		Integer indexObject = childrenObjectMap.get(child);
		if (indexObject != null)
			return indexObject.intValue();
		else
			return -1;
	}

	/**
	 * save or update a field. also take care of old object when updating.
	 * 
	 * @param child
	 * @param name
	 * @param own
	 */
	public void setField(String name, _Object child, boolean own) {

		// name must be unique if is not null
		if (name != null) {
			Field f = getField(name);

			if (f != null) {
				changeChild(f, child, own);

			} else {
				Field newField = new Field(name, own);
				fieldList.add(newField);

				addChild(newField, child, name, own);
			}

		} else
			throw new RuntimeException("field name cannot be null");

	}

	/**
	 * change target object of a field.
	 * 
	 * @param field
	 * @param child
	 * @param owner
	 */
	protected void changeChild(Field field, _Object child, boolean owner) {

		_Object oldChild = field.target;
		if (oldChild != null)
			detachOwnedChild(oldChild);

		field.target = child;

		if (owner) {
			if (child.getScope() != ObjectScope.ExecutionContext)
				throw new RuntimeException(
						String.format(
								"cannot own object(#%d) because it's scope is not ExecutionContext",
								child.id));

			field.type = FieldType.Branch;
			child.field = field;
		}

		recreateFieldIndexes();

	}

	protected void addChild(Field field, _Object child, String name,
			boolean owner) {

		field.setTarget(child);
		field.owner = this;

		if (owner) {
			if (child.getScope() != ObjectScope.ExecutionContext)
				throw new RuntimeException(
						String.format(
								"cannot own object(#%d) because it's scope is not ExecutionContext",
								child.id));

			field.type = FieldType.Branch;
			child.field = field;
		} else {
			field.type = FieldType.Pointer;
		}

		sortFields();
	}

	/**
	 * only for list(no name element)
	 * 
	 * @param index
	 * @param child
	 * @param own
	 */
	public void insertChild(int index, _Object child, boolean own) {

		Field p = new Field(child, name, own);

		fieldList.add(index, p);
		recreateFieldIndexes();

		if (own) {
			if (child.getScope() != ObjectScope.ExecutionContext)
				throw new RuntimeException(
						String.format(
								"cannot own object(#%d) because it's scope is not ExecutionContext",
								child.id));

			child.attachTo(p);
		}
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

		return field != null && field.owner != null;
	}

	public void renameField(String old, String _new) {

		if (fieldNameMap == null) {
			if (logger.isDebugEnabled())
				logger.debug("warnning: field index not created.");

			recreateFieldIndexes();
		}

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

	public void removeChild(_Object object) {
		Integer idx = childrenObjectMap.get(object);
		if (idx == null)
			throw new RuntimeException("field not exist");

		removeField(idx);
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

		recreateFieldIndexes();

		if (field.target != null && owns(field.target))
			detachOwnedChild(field.target);
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
			child.setScope(ObjectScope.ExecutionContext);
		}
	}

	public void detachFromOwner() {
		// owner.detachOwnedChild(this);

		field.target = null;
		field = null;
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
		this.field = field;
		this.scope = null;
	}

	public void clearChildren() {
		while (!fieldList.isEmpty())
			removeField(0);
	}

	public boolean hasChildren() {
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
			Point canvasOffset) {

		ObjectLayout layout = ObjectLayout.XY;
		StringValue layoutSV = (StringValue) getSystemChild(OBJECT_LAYOUT);
		if (layoutSV != null) {
			try {
				layout = ObjectLayout.valueOf(layoutSV.getValue());
			} catch (Exception e) {
				layout = ObjectLayout.XY;// default layout
			}
		}

		layout.preprocess(this);

		for (Field field : fieldList) {

			boolean owns = field.target != null
					&& field.type == FieldType.Branch;

			_Object proto = field.target.getPrototype();
			String objName = null;
			if (proto != null) {
				objName = String.format("%s<%s>", field.name, proto.name);
			} else
				objName = field.name;

			field.draw(diagramPanel, scene, canvasOffset, scaleRate, owns);

		}
	}

	protected Field getField(String name) {
		Integer index = fieldNameMap.get(name);
		if (index != null)
			return fieldList.get(index);
		else
			return null;
	}

	public _Object getChild(String name) {

		if (fieldNameMap == null) {
			recreateFieldIndexes();
			if (logger.isDebugEnabled())
				logger.debug("field index not created when lookup field:"
						+ name);
		}

		Integer index = fieldNameMap.get(name);
		if (index == null)
			return null;
		else
			return fieldList.get(index).target;
	}

	public _Object getSystemChild(String name) {
		_Object systemObject = getSystem();
		if (systemObject != null)
			return systemObject.getChild(name);
		else
			return null;
	}

	public void setSystemField(String name, _Object obj, boolean owner) {
		_Object systemObject = getSystem();
		if (systemObject == null)
			systemObject = createSystemField();

		systemObject.setField(name, obj, owner);

	}

	public String[] getFieldNames() {
		List<String> names = new ArrayList<String>();
		for (String fieldName : fieldNameMap.keySet()) {
			if (!Field.isSystemField(fieldName))
				names.add(fieldName);
		}

		return names.toArray(new String[0]);
	}

	/**
	 * include system field
	 * 
	 * @return
	 */
	public String[] getAllFieldNames() {
		return fieldNameMap.keySet().toArray(new String[0]);
	}

	/**
	 * support prototype
	 * 
	 * @param name
	 * @return
	 */
	public Procedure lookupProcedure(Message message) {
		String subject = message.getSubject();
		// first try match the procedure name
		Procedure p = (Procedure) getChild(subject);

		// not found, try match rule
		if (p == null) {
			if (logger.isDebugEnabled())
				logger.debug("no procedure found by message subject " + subject);

			BooleanValue enableMatch = (BooleanValue) getSystemChild(ENABLE_SUBJECT_MATCH);

			if (enableMatch != null && enableMatch.getBooleanValue() == true) {
				if (logger.isDebugEnabled())
					logger.debug("extended subject match is enabled...");

				// default setting of the object
				SubjectMatchType defaultSubjectMatchType = SubjectMatchType.RegularExpression;
				StringValue defaultSubjectMatchTypeSV = (StringValue) getSystemChild(DEFAULT_SUBJECT_MATCH_TYPE);
				if (defaultSubjectMatchTypeSV != null)
					defaultSubjectMatchType = SubjectMatchType
							.valueOf(defaultSubjectMatchTypeSV.getValue());

				if (logger.isDebugEnabled())
					logger.debug("object match type is"
							+ defaultSubjectMatchType);

				for (Field field : fieldList) {
					_Object child = field.target;

					StringValue messageSubjectMatchRule = (StringValue) child
							.getSystemChild(SUBJECT_MATCH_RULE);

					if (messageSubjectMatchRule != null) {
						// procedure specific setting
						SubjectMatchType subjectMatchType = defaultSubjectMatchType;
						StringValue subjectMatchTypeSV = (StringValue) child
								.getSystemChild(SUBJECT_MATCH_TYPE);

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
			if (hasPrototype()) {
				_Object prototype = getPrototype();

				// prototype field only support local object.
				// finding procedure on remote machine is too slow.
				if (prototype instanceof Link) {

					Link link = (Link) prototype;

					if (link.getRemoteAddress() != null
							&& ProtocolType.PATH.toString().equalsIgnoreCase(
									link.getRemoteAddress().protocol)) {
						ObjectRepository repo = VirtualMachine.getInstance()
								.getObjectRepository();
						_Object target = repo.getObjectByPath(link
								.getRemoteAddress().userId);

						prototype = target;
					}

				}
				// else find procedure on link object, obviously get nothing.

				p = prototype.lookupProcedure(message);
			}
		}

		return p;
	}

	private _Object getSystem() {
		return getChild(SYSTEM);
	}

	public _Object getPrototype() {

		_Object system = getSystem();
		if (system != null) {
			return system.getChild(PROTOTYPE);

		}

		return null;
	}

	private _Object createSystemField() {
		VirtualMachine vm = VirtualMachine.getInstance();
		return vm.getObjectRepository().createObject(this, SYSTEM,
				ObjectType.NORMAL);
	}

	public void setPrototype(_Object obj) {
		_Object system = getSystem();
		if (system == null)
			system = createSystemField();

		system.setField(PROTOTYPE, obj, false);

	}

	private boolean hasPrototype() {
		return getPrototype() != null;
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
		if (msg.urgent || msg.isReply() || msg.sender == this) {
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

	public synchronized boolean hasNewerMessageArrived(
			Message currentWorkingMessage) {

		return messageQueue.peekFirst() != currentWorkingMessage;
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

	public boolean owns(_Object child) {
		if (child != null)
			return child.getOwner() == this;
		else
			return false;
	}

	/**
	 * rearrange the field list, system field are on top.
	 * 
	 * and update the system field count.
	 */
	public void sortFields() {
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

		recreateFieldIndexes();
	}

	public int getSystemFieldsCount() {
		return this.systemFieldsCount;
	}

	public int getUserFieldsCount() {
		return fieldList.size() - systemFieldsCount;
	}

	/**
	 * re-arrange the field
	 */
	public void arrangeField() {

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
}
