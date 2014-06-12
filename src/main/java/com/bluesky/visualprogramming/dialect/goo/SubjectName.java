package com.bluesky.visualprogramming.dialect.goo;

public class SubjectName {

	public enum SubjectType {
		Constant, Variable;
	}

	String value;
	SubjectType type;

	public SubjectName(String value, SubjectType type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}

	public SubjectType getType() {
		return this.type;
	}
}
