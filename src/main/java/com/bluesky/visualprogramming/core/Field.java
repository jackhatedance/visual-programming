package com.bluesky.visualprogramming.core;

public class Field {

	public String name;
	public _Object target;

	public boolean owner;

	private SelectedStatus selectedStatus = SelectedStatus.NotSelected;

	public Field(_Object targetObject, String name, boolean owner) {
		this.target = targetObject;
		this.name = name;
		this.owner = owner;
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

}
