package com.bluesky.visualprogramming.core;

import java.awt.Point;
import java.awt.Rectangle;
import java.lang.ref.WeakReference;

import org.apache.batik.dom.svg.SVGOMGElement;

import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.ui.SVGDiagramPanel;
import com.bluesky.visualprogramming.ui.svg.SvgScene;

public class Field {

	static int BORDER_WIDTH_THIN = 2;

	public String name;

	/**
	 * it is strong reference
	 */
	private _Object strongTarget;
	/**
	 * the weak reference, aka. pointer.
	 */
	private WeakReference<_Object> weakTarget;

	private FieldType type;

	/**
	 * the owner of this field
	 */
	public _Object owner;

	/**
	 * only used for pointer serialization.
	 */
	public String pointerPath;

	/**
	 * if the original size of a SVG is 100*100. then this scale is to resize it.
	 * the transform box is used to change this value.
	 */
	public float svgScale = 0.2f;

	/**
	 * should be move to other place. it is status of GUI component.
	 */
	private SelectedStatus selectedStatus = SelectedStatus.NotSelected;

	/**
	 * the max value of height and width is 1000;
	 */
	private Rectangle area = new Rectangle();

	public Field(String name, boolean own) {
		this.name = name;

		this.type = FieldType.getType(own);

		area = new Rectangle(0, 0, 100, 100);
	}

	public Field(_Object target, String name, boolean own) {

		this.name = name;
		this.type = FieldType.getType(own);

		attachTarget(target);

		area = new Rectangle(0, 0, 100, 100);
	}

	public Field(Field src, _Object target) {
		this.name = src.name;
		this.type = src.type;
		this.svgScale = src.svgScale;
		this.selectedStatus = SelectedStatus.NotSelected;
		this.area = new Rectangle(src.area);

		attachTarget(target);
	}

	@Override
	public String toString() {

		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public _Object getStrongTarget() {
		return strongTarget;
	}

	public void setStrongTarget(_Object target) {
		this.strongTarget = target;
	}

	public SelectedStatus getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(SelectedStatus selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public Rectangle getArea(double zoomRate) {
		Rectangle r = new Rectangle((int) (area.x * zoomRate),
				(int) (area.y * zoomRate), (int) (area.width * zoomRate),
				(int) (area.height * zoomRate));
		return r;
	}

	public Rectangle getArea() {
		// if (area == null)
		// this.area = (Rectangle)target.area.clone();

		// if (area == null)
		// this.area = new Rectangle(0,0,100,100);

		return area;
	}

	public void setStartPosition(float x, float y) {
		area.x = (int) x;
		area.y = (int) y;

	}

	public boolean isSystemField() {
		return name != null && name.startsWith("_");
	}

	static public boolean isSystemField(String name) {
		return name != null && name.startsWith("_");
	}

	/**
	 * draw itself as an icon. (if it is big enough, then draw internal.)
	 * 
	 * @param diagramPanel
	 * @param scene
	 * @param canvasOffset
	 * @param zoom
	 * @param own
	 */
	public void draw(SVGDiagramPanel diagramPanel, SvgScene scene,
			Point canvasOffset, boolean own) {

		// System.out.println("draw:"+getName());

		// draw border
		// g.setColor(borderColor);

		int x = (int) ((area.x + canvasOffset.x) );
		int y = (int) ((area.y + canvasOffset.y) );

		_Object target = getTarget();

		long id = target.getId();
		String value = target.getHumanReadableText();

		SVGOMGElement ele = null;
		if (target != null) {
			_Object graphic = target.getSystemTopChild(SystemField.Graphic);
			if (graphic != null && graphic instanceof StringValue) {
				StringValue sv = (StringValue) graphic;
				ele = scene.addObject(sv.getValue(), id, x, y,  svgScale);

			} else
				ele = scene.addObject(target.type, id, x, y,  svgScale);
		}

		// for debug
		// name += ",name=" + Long.toString(id);

		scene.setName(id, name);

		int maxLength = 50;
		int length = value.length();
		String ellipsis = "...";
		if (value.length() > maxLength) {
			length = maxLength - ellipsis.length();
			value = value.substring(0, length) + ellipsis;
		}

		scene.setDescription(id, value);

		scene.setBorderColor(id, target.borderColor);

		if (!own)
			scene.setBorderWidth(id, BORDER_WIDTH_THIN);

		ele.setUserData("field", this, null);
		diagramPanel.addMouseListener(ele);

	}

	public void accept(ObjectVisitor visitor) {
		Object status = visitor.enter(this);
		if (status instanceof Boolean && (Boolean) status == true) {

			// notice: only visit strong field?
			_Object target = getTarget();
			if (target != null)
				target.accept(visitor);
		}
		visitor.leave(this);
	}

	public WeakReference<_Object> getWeakTarget() {
		return weakTarget;
	}

	public void setWeakTarget(_Object target) {
		this.weakTarget = new WeakReference<_Object>(target);
	}

	public _Object getTarget() {
		return type.getTarget(this);
	}

	/**
	 * force update
	 * 
	 * @param target
	 */
	public void setTarget(_Object target) {
		if (getTarget() != null)
			detachTarget();

		attachTarget(target);
	}

	/**
	 * cut the link between a field and the target object.
	 * 
	 */
	public void detachTarget() {
		type.detachTarget(this);
	}

	public void attachTarget(_Object child) {
		type.attachTarget(this, child);
	}

	public void setType(FieldType type) {
		if (this.type != type) {
			// check if old target cleared
			if (getTarget() != null)
				throw new RuntimeException(
						"cannot change field type when orginal target is not removed");

			this.type = type;
		}

	}

	public FieldType getType() {
		return type;
	}

}
