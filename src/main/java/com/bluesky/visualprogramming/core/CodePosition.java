package com.bluesky.visualprogramming.core;

public class CodePosition {
	private String object;
	private String procedure;
	private int line;

	public CodePosition(String object, String procedure, int line) {
		this.object = object;
		this.procedure = procedure;
		this.line = line;

	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public String toString() {

		return String.format("%s.%s():%d", object, procedure, line);
	}
}
