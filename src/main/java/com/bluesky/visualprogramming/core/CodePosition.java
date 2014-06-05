package com.bluesky.visualprogramming.core;

public class CodePosition {
	private String object;
	private String procedure;
	/**
	 * for native java code, it is java file name;
	 * 
	 * for Cooby code, it is path of prototype object(if it is from prototype)
	 */
	private String physicalPosition;
	private int line;

	public CodePosition(String object, String procedure, String file, int line) {
		this.object = object;
		this.procedure = procedure;
		this.physicalPosition = file;
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
		return physicalPosition;
	}

	public void setFile(String file) {
		this.physicalPosition = file;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public String toString() {
		String file2 = physicalPosition;
		if (physicalPosition == null)
			file2 = "";
		return String.format("%s.%s(%s:%d)", object, procedure, file2,line);
	}
}
