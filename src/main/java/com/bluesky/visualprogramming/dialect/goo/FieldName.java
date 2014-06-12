package com.bluesky.visualprogramming.dialect.goo;

public class FieldName {

	public enum FieldType {
		Constant, Variable;
	}

	String value;
	FieldType type;

	public FieldName(String value, FieldType type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public FieldType getType() {
		return this.type;
	}
}
