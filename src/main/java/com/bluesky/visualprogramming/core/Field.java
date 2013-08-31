package com.bluesky.visualprogramming.core;

public class Field {

	public boolean owner;
	public String name;
	public _Object target;

	public Field(_Object targetObject, String name, boolean owner) {
		this.target = targetObject;
		this.name = name;
		this.owner = owner;
	}

	public boolean hasName() {

		return name != null && !name.isEmpty();
	}

}
