package com.bluesky.visualprogramming.core;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.messageEngine.Worker;
import com.bluesky.visualprogramming.vm.CompiledProcedure;

public class _Object implements Serializable {
	static Logger logger = Logger.getLogger(_Object.class);

	static public final String PROTOTYPE = "_prototype";
	static public final String ENABLE_SUBJECT_MATCH = "_enableSubjectMatch";
	static public final String SUBJECT_MATCH_RULE = "_subjectMatchRule";

	// it is defined by the object, and is for its procedures.
	static public final String DEFAULT_SUBJECT_MATCH_TYPE = "_defaultSubjectMatchType";
	// it is defined by the procedure itself, to override the default value of
	// the onwer object.
	static public final String SUBJECT_MATCH_TYPE = "_subjectMatchType";
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

	// this is a pointer, set after linking.
	private _Object owner;

	/**
	 * only those has root in persistent repository are persistent. messages are
	 * not persistent.
	 */
	public ObjectScope scope = null;

	// in Z order
	private List<Field> fieldList = new ArrayList<Field>();
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

	
	public double scaleRate = 1d;
	public Color borderColor;
	static int borderWidth = 5;

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
			boolean owns = p.target.owner == this;

			addChild(p.target, p.name, owns);
		}

		// messageQueue is skipped

		// always idle
		this.worker = null;

		//this.area = new Rectangle(src.area);

		this.scaleRate = src.scaleRate;
		this.borderColor = src.borderColor;
		this.borderWidth = src.borderWidth;
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

	public ObjectType getType() {
		return type;
	}

	public _Object getOwner() {
		return owner;
	}

	public void setOwner(_Object owner) {
		this.owner = owner;
	}

	public void updateFieldIndexes() {
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

	public void addPointer(_Object child, String name) {
		addChild(child, name, false);
	}

	public int getChildIndex(_Object child) {
		Integer indexObject = childrenObjectMap.get(child);
		return indexObject.intValue();
	}

	public void addChild(_Object child, String name, boolean owner) {

		Field p = new Field(child, name);

		fieldList.add(p);
		fieldNameMap.put(name, fieldList.size() - 1);
		childrenObjectMap.put(child, fieldList.size() - 1);

		if (owner) {
			if (child.getScope() != ObjectScope.ExecutionContext)
				throw new RuntimeException(
						String.format(
								"cannot own object(#%d) because it's scope is not ExecutionContext",
								child.id));

			child.setOwner(this);
		}
	}

	public ObjectScope getScope() {
		if (scope != null)
			return scope;
		else if (hasOwner())
			return owner.getScope();
		else
			return null;
	}

	public void setScope(ObjectScope scope) {
		this.scope = scope;
	}

	/**
	 * take care of the old field if exists.
	 * 
	 * @param name
	 * @param child
	 * @param owner
	 */
	public void setChild(String name, _Object child, boolean owner) {
		if (name == null) {
			throw new RuntimeException("field name cannot be null.");
		}

		_Object oldChild = getChild(name);
		if (oldChild != null)
			removeChild(name);

		addChild(child, name, owner);
	}

	public boolean hasOwner() {

		return owner != null;
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

	public void removeChild(String name) {
		Integer childIndex = fieldNameMap.get(name);
		if (childIndex == null)
			throw new RuntimeException("child not found:" + name);

		removeChild(childIndex);
	}

	public void removeChild(Integer indexObject) {

		if (indexObject == null)
			return;

		int index = indexObject.intValue();

		Field field = fieldList.get(index);
		fieldList.remove(index);

		fieldNameMap.remove(field.name);
		childrenObjectMap.remove(field.target);

		field.target.setOwner(null);
	}

	public void removeChild(_Object object) {
		Integer idx = childrenObjectMap.get(object);
		if (idx == null)
			throw new RuntimeException("field not exist");

		removeChild(idx);
	}

	public void clearChildren() {
		fieldList.clear();
		fieldNameMap.clear();
		childrenObjectMap.clear();
	}

	public boolean hasChildren() {
		return !fieldList.isEmpty();
	}

	public List<Field> getFields() {
		return this.fieldList;
	}

	public String getValue() {
		return "";
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

	public void draw(Graphics g,  Point canvasOffset,Rectangle area, double zoom, boolean own,
			String name, SelectedStatus selectedStatus) {
		// System.out.println("draw:"+getName());

		// draw border
		g.setColor(borderColor);

		int x = (int) (area.x * zoom) + canvasOffset.x;
		int y = (int) (area.y * zoom) + canvasOffset.y;
		int width = (int) (area.width * zoom);
		int height = (int) (area.height * zoom);

		Graphics2D g2 = (Graphics2D) g;

		int finalBorderWidth = -1;
		if (own)
			finalBorderWidth = this.borderWidth;
		else
			finalBorderWidth = this.borderWidth / 2;

		Stroke borderStroke = null;
		if (selectedStatus == SelectedStatus.Preselected
				|| selectedStatus == SelectedStatus.Selected)
			borderStroke = new BasicStroke(finalBorderWidth,
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
					new float[] { 9 }, 0);
		else
			borderStroke = new BasicStroke(finalBorderWidth);

		g2.setStroke(borderStroke);

		g.drawRect(x, y, width, height);

		g.setColor(Color.BLACK);

		if (name != null)
			g.drawString(name, x + 5, (int) (y + 15));

		// draw selector box
		Color selectorColor = null;

		// if (selectedStatus == SelectedStatus.Preselected
		// || selectedStatus == SelectedStatus.Selected) {
		// if (selectedStatus == SelectedStatus.Preselected)
		// selectorColor = Color.RED;
		// else if (selectedStatus == SelectedStatus.Selected)
		// selectorColor = Color.BLUE;
		//
		// g2.setColor(selectorColor);
		// Stroke drawingStroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
		// BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		// g2.setStroke(drawingStroke);
		// // g2.drawre(line);
		// g2.drawRect(x - 1, y - 1, width + 1, height + 1);
		// }
		// draw internal
		int shortSide = width < height ? width : height;
		double internalScale = (double) shortSide / (double) 1000;

		if (internalScale > 0.1f) {
			Point internalOffset = new Point(x, y);
			drawInternal(g, internalOffset, internalScale, name, selectedStatus);
		}
	}

	protected void drawInternal(Graphics g, Point canvasOffset, double zoom,
			String name, SelectedStatus selectedStatus) {

		for (Field f : fieldList) {
			boolean owns = f.target.owner == this;
			f.target.draw(g, canvasOffset,f.getArea(), zoom, owns, name, selectedStatus);
		}
	}

	public void drawInternal(Graphics g, Point canvasOffset) {
		for (Field field : fieldList) {
			boolean owns = field.target.owner == this;

			_Object proto = field.target.getPrototypeObject();
			String objName = null;
			if (proto != null) {
				objName = String.format("%s<%s>", field.name, proto.name);
			} else
				objName = field.name;

			field.target.draw(g, canvasOffset, field.getArea(), scaleRate, owns, objName,
					field.getSelectedStatus());
		}
	}

	public _Object getChild(String name) {
		Integer index = fieldNameMap.get(name);
		if (index == null)
			return null;
		else
			return fieldList.get(index).target;
	}

	public String[] getChildrenNames() {
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

			BooleanValue enableMatch = (BooleanValue) getChild(ENABLE_SUBJECT_MATCH);

			if (enableMatch != null && enableMatch.getBooleanValue() == true) {
				if (logger.isDebugEnabled())
					logger.debug("extended subject match is enabled...");

				// default setting of the object
				SubjectMatchType defaultSubjectMatchType = SubjectMatchType.RegularExpression;
				StringValue defaultSubjectMatchTypeSV = (StringValue) getChild(DEFAULT_SUBJECT_MATCH_TYPE);
				if (defaultSubjectMatchTypeSV != null)
					defaultSubjectMatchType = SubjectMatchType
							.valueOf(defaultSubjectMatchTypeSV.getValue());

				if (logger.isDebugEnabled())
					logger.debug("object match type is"
							+ defaultSubjectMatchType);

				for (Field field : fieldList) {
					_Object child = field.target;

					StringValue messageSubjectMatchRule = (StringValue) child
							.getChild(SUBJECT_MATCH_RULE);

					if (messageSubjectMatchRule != null) {
						// procedure specific setting
						SubjectMatchType subjectMatchType = defaultSubjectMatchType;
						StringValue subjectMatchTypeSV = (StringValue) child
								.getChild(SUBJECT_MATCH_TYPE);

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
				_Object prototype = getPrototypeObject();
				p = prototype.lookupProcedure(message);
			}
		}

		return p;
	}

	private _Object getPrototypeObject() {

		return getChild(PROTOTYPE);
	}

	private boolean hasPrototype() {
		return getChild(PROTOTYPE) != null;
	}

	public CompiledProcedure getCompiledProcedure(Procedure procedure) {

		if (procedure.compiled == null) {
			LanguageType type = LanguageType.valueOf(procedure.language
					.toUpperCase());
			if (type == null)
				throw new RuntimeException("unsupported language:"
						+ procedure.language);

			try {
				CompiledProcedure cp = type.getCompiler().compile(
						procedure.code);

				if (logger.isDebugEnabled())
					logger.debug(cp.getInstructionText());

				procedure.compiled = cp;

			} catch (Exception e) {
				throw new RuntimeException("compile failed", e);

			}
		}

		return procedure.compiled;
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

		if (logger.isDebugEnabled())
			logger.debug(String.format(
					"a message added to queue %s, subject: %s, need-worker:%s",
					pos, msg.getSubject(), applyWorkerForMe));

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

	private MessageType expectMessageType;

	public void setExpectMessageType(MessageType type) {
		this.expectMessageType = type;
	}

	public void checkMessageType(MessageType type) {
		if (expectMessageType == null)
			return;

		if (expectMessageType != type)
			throw new RuntimeException(String.format(
					"expected %s but comes %s", expectMessageType, type));
		else
			expectMessageType = null;

	}

	public synchronized void printMessageQueue() {
		Iterator<Message> it = messageQueue.iterator();

		while (it.hasNext()) {
			Message m = it.next();
			System.out.println(m.toString());
		}
	}

	public boolean owns(_Object child) {
		return child.getOwner() == this;
	}
}
