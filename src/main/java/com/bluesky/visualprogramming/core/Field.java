package com.bluesky.visualprogramming.core;

import java.awt.Rectangle;

public class Field {

	public String name;
	public _Object target;

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

	public void setArea(Rectangle area) {
		this.area = area;
	}

	public Rectangle getArea() {
		// if (area == null)
		// this.area = (Rectangle)target.area.clone();

		// if (area == null)
		// this.area = new Rectangle(0,0,100,100);

		return area;
	}

	public boolean isSystemField() {
		return name != null && name.startsWith("_");
	}
}
