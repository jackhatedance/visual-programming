package com.bluesky.visualprogramming.core;

public class CodePosition {
	private String object;
	private String procedure;
	private String file;
	private int line;

	public CodePosition(String object, String procedure, String file, int line) {
		this.object = object;
		this.procedure = procedure;
		this.file = file;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public String toString() {
		String file2 = file;
		if (file == null)
			file2 = "";
		return String.format("%s.%s(%s:%d)", object, procedure, file2,line);
	}
}
