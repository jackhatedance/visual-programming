package com.bluesky.visualprogramming.core;

import java.awt.Point;
import java.awt.Rectangle;

import org.apache.batik.dom.svg.SVGOMGElement;

import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.ui.SVGDiagramPanel;
import com.bluesky.visualprogramming.ui.svg.SvgScene;

public class Field {

	public String name;
	public _Object target;

	// scale of a svg shape.
	public float svgScale = 0.2f;

	// public boolean owner;

	private SelectedStatus selectedStatus = SelectedStatus.NotSelected;

	/**
	 * the max value of height and width is 1000;
	 */
	private Rectangle area = new Rectangle();

	public Field(String name) {
		this.name = name;
		area = new Rectangle(0, 0, 100, 100);
	}

	public Field(_Object targetObject, String name) {
		this.target = targetObject;
		this.name = name;
		// this.owner = owner;

		area = new Rectangle(0, 0, 100, 100);
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

	public _Object getTarget() {
		return target;
	}

	public void setTarget(_Object target) {
		this.target = target;
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
			Point canvasOffset, double zoom, boolean own) {

		// System.out.println("draw:"+getName());

		// draw border
		// g.setColor(borderColor);

		int x = (int) (area.x * zoom) + canvasOffset.x;
		int y = (int) (area.y * zoom) + canvasOffset.y;

		long id = target.getId();
		String value = target.getHumanReadableText();

		SVGOMGElement ele = null;
		if (target != null) {
			_Object graphic = target.getSystemChild(_Object.GRAPHIC);
			if (graphic != null && graphic instanceof StringValue) {
				StringValue sv = (StringValue) graphic;
				ele = scene.addObject(sv.getValue(), id, x, y, svgScale);

			} else
				ele = scene.addObject(target.type, id, x, y, svgScale);
		}

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

		ele.setUserData("field", this, null);
		diagramPanel.addMouseListener(ele);

	}

	public void accept(ObjectVisitor visitor) {
		Object status = visitor.enter(this);
		if (status instanceof Boolean && (Boolean) status == true) {
			if (target != null)
				target.accept(visitor);
		}
		visitor.leave(this);
	}
}
