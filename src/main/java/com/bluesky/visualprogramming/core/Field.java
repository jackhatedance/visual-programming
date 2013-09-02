package com.bluesky.visualprogramming.core;

public class Field {

	
	public String name;
	public _Object target;

	//public boolean owner;
	
	
	public Field(_Object targetObject, String name) {
		this.target = targetObject;
		this.name = name;
		//this.owner = owner;
	}

	public boolean hasName() {

		return name != null && !name.isEmpty();
	}

	
}
