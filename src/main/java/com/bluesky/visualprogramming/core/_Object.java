package com.bluesky.visualprogramming.core;

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
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.bluesky.visualprogramming.goo.GooCompiler;
import com.bluesky.visualprogramming.vm.CompiledProcedure;

public class _Object implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1809740541459285761L;

	protected ObjectType type = ObjectType.DEFAULT;
	private long id;

	/*
	 * the name called by owner
	 */
	private String name;

	// this is a pointer, set after linking.
	private _Object owner;

	private boolean context = false;

	// in Z order
	private List<Pointer> childrenList = new ArrayList<Pointer>();
	// index names to accelerate access speed
	private Map<String, Integer> childrenMap = new HashMap<String, Integer>();

	private Deque<Message> messageQueue;
	private boolean awake = true;
	private Worker worker = null;

	/**
	 * the max value of height and width is 1000;
	 */
	public Rectangle area = new Rectangle();
	public SelectedStatus selectedStatus = SelectedStatus.NotSelected;
	public double scaleRate = 1d;
	public Color borderColor;
	static int borderWidth = 5;

	public _Object(long id) {
		this.id = id;

		area = new Rectangle(0, 0, 100, 100);
		borderColor = Color.BLACK;

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

	public Map<String, Integer> getChildrenMap() {
		return childrenMap;
	}

	/**
	 * if there is no named field. then it become an array.
	 * 
	 * @return
	 */
	public boolean hasNamedField() {
		return !childrenMap.isEmpty();
	}

	public _Object getChild(int index) {
		return childrenList.get(index).target;
	}

	public int getChildCount() {
		return childrenList.size();
	}

	public void addPointer(_Object child, String name) {
		addChild(child, name, false);
	}

	/**
	 * no name
	 * 
	 * @param child
	 */
	public void addChild(_Object child, boolean owner) {
		addChild(child, null, owner);
	}

	public void addChild(_Object child, String name, boolean owner) {
		Pointer p = new Pointer(child, name, owner);
		childrenList.add(p);
		childrenMap.put(name, childrenList.size()-1);

		if (owner) {
			if (child.hasOwner())
				throw new RuntimeException(
						"the child object already has owner: id="
								+ child.owner.id);

			child.setOwner(this);
		}
	}

	private boolean hasOwner() {

		return owner != null;
	}

	public void renameField(String old, String _new) {

		Integer oldIndex = childrenMap.get(old);
		if (oldIndex == null)
			throw new RuntimeException("source field not exist:" + old);

		if (childrenMap.containsKey(_new)) {
			throw new RuntimeException("destination field already exist:"
					+ _new);
		}

		childrenMap.remove(old);

		childrenMap.put(_new, oldIndex);

	}

	public void removeChild(String name) {
		Integer childIndex = childrenMap.get(name);
		if (childIndex == null)
			throw new RuntimeException("child not found:" + name);

		removeChild(childIndex);
	}

	public void removeChild(Integer index) {

		if (index == null)
			return;

		Pointer p = childrenList.get(index);
		childrenList.remove(index);

		if (p.hasName())
			childrenMap.remove(p.name);

		p.target.setOwner(null);
	}

	public void removeChild(_Object obj) {

	}

	public void clearChildren() {
		childrenList.clear();
		childrenMap.clear();
	}

	public boolean hasChildren() {
		return !childrenList.isEmpty();
	}

	public String getValue() {
		return "";
	}

	public void setValue(String value) {
		// it is not a value object, the value must be null;
		if (value != null && !value.isEmpty())
			throw new RuntimeException("param 'value' is not empty.");
	}

	public Rectangle getArea() {
		return area;
	}

	public Rectangle getArea(double zoomRate) {
		Rectangle r = new Rectangle((int) (area.x * zoomRate),
				(int) (area.y * zoomRate), (int) (area.width * zoomRate),
				(int) (area.height * zoomRate));
		return r;
	}

	public void setArea(Rectangle area) {
		this.area = area;
	}

	@Override
	public String toString() {

		return name;
	}

	@SuppressWarnings("deprecation")
	public String toText() {
		// id=1,name=foo,owner=0,value=

		long ownerId = -1;
		if (owner != null)
			ownerId = owner.getId();

		String encValue = getValue();

		try {
			encValue = URLEncoder.encode(getValue(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return String
				.format("type=%s,id=%d,name=%s,owner=%d,x=%d,y=%d,width=%d,height=%d,scale=%f,color=%d,value=%s",
						type, id, name, ownerId, area.x, area.y, area.width,
						area.height, scaleRate, borderColor.getRGB(), encValue);
	}

	public void fromText(String text) {
		Map<String, String> map = KeyValueStringUtils.parse(text);
		fromMap(map);
	}

	public void fromMap(Map<String, String> map) {

		this.type = ObjectType.valueOf(map.get("type").toUpperCase());
		this.id = Long.parseLong(map.get("id"));
		this.name = map.get("name");
		long ownerId = Long.parseLong(map.get("owner"));

		// ID holder
		this.owner = new _Object(ownerId);

		int x = Integer.valueOf(map.get("x"));
		int y = Integer.valueOf(map.get("y"));
		int width = Integer.valueOf(map.get("width"));
		int height = Integer.valueOf(map.get("height"));
		area.x = x;
		area.y = y;
		area.width = width;
		area.height = height;

		String scaleStr = "1.0";
		if (map.get("scale") != null)
			scaleStr = map.get("scale");

		this.scaleRate = Double.valueOf(scaleStr);

		String colorStr = "0";
		if (map.get("color") != null)
			colorStr = map.get("color");

		this.borderColor = new Color(Integer.valueOf(colorStr));

		String encValue = map.get("value");
		String decValue = null;
		try {
			decValue = URLDecoder.decode(encValue, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		setValue(decValue);

	}

	public void draw(Graphics g, Point canvasOffset, double zoom) {
		// System.out.println("draw:"+getName());

		// draw border
		g.setColor(borderColor);

		int x = (int) (area.x * zoom) + canvasOffset.x;
		int y = (int) (area.y * zoom) + canvasOffset.y;
		int width = (int) (area.width * zoom);
		int height = (int) (area.height * zoom);

		Graphics2D g2 = (Graphics2D) g;

		Stroke borderStroke = null;
		if (selectedStatus == SelectedStatus.Preselected
				|| selectedStatus == SelectedStatus.Selected)
			borderStroke = new BasicStroke(borderWidth, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		else
			borderStroke = new BasicStroke(borderWidth);

		g2.setStroke(borderStroke);

		g.drawRect(x, y, width, height);

		g.setColor(Color.BLACK);
		g.drawString(this.name, x, (int) (y + 10));

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
			drawInternal(g, internalOffset, internalScale);
		}
	}

	protected void drawInternal(Graphics g, Point canvasOffset, double zoom) {

		for (Pointer p : childrenList) {
			if (p.owner)
				p.target.draw(g, canvasOffset, zoom);
		}
	}

	public void drawInternal(Graphics g, Point canvasOffset) {
		for (Pointer p : childrenList) {
			if (p.owner)
				p.target.draw(g, canvasOffset, scaleRate);
		}
	}

	public _Object getChild(String name) {
		Integer index = childrenMap.get(name);

		return childrenList.get(index).target;
	}

	public String[] getChildrenNames() {
		return childrenMap.keySet().toArray(new String[0]);
	}

	public Deque<Message> getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(Deque<Message> messageQueue) {
		this.messageQueue = messageQueue;
	}

	public Procedure getProcedure(String name) {
		Integer index = childrenMap.get(name);
		if (index == null)
			throw new RuntimeException("procedure not found:" + name);

		Procedure p = (Procedure) (childrenList.get(index).target);

		return p;
	}

	public CompiledProcedure getCompiledProcedure(String name) {

		Procedure p = getProcedure(name);

		if (p.compiled == null) {
			LanguageType type = LanguageType.valueOf(p.language.toUpperCase());
			if (type == null)
				throw new RuntimeException("unsupported language:" + p.language);

			try {
				CompiledProcedure cp = type.getCompiler().compile(p.code);

				System.out.println(cp.getInstructionText());

				p.compiled = cp;

			} catch (Exception e) {
				throw new RuntimeException("compile failed", e);

			}
		}

		return p.compiled;
	}

	public boolean isContext() {
		if (owner != null)
			return owner.isContext();
		else
			return this.context;
	}

	public synchronized int addToMessageQueue(Message msg) {
		if (messageQueue == null)
			messageQueue = new ArrayDeque<Message>();

		messageQueue.add(msg);
		return messageQueue.size();
	}

	public synchronized boolean isAwake() {
		return awake;

	}

	public synchronized void setAwake(boolean awake) {
		this.awake = awake;
	}

	public synchronized boolean hasWorker() {
		return this.worker != null;
	}

	public synchronized void sleep() {
		this.awake = false;

	}
}
