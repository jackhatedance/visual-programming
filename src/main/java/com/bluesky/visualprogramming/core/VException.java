package com.bluesky.visualprogramming.core;

import java.util.ArrayList;
import java.util.List;

/**
 * exception object, as return value if error happened
 * 
 * @author jack
 * 
 */
public class VException extends _Object {
	private String message;
	private List<CodePosition> traces = new ArrayList<CodePosition>();

	public VException(long id) {
		super(id);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void addTrace(Exception e) {
		for (StackTraceElement ele : e.getStackTrace()) {
			addTrace(new CodePosition(ele.getClassName(), ele.getMethodName(),
					ele.getFileName(), ele.getLineNumber()));
		}
	}

	public void addTrace(CodePosition position) {
		traces.add(position);
	}

	public String getTrace() {
		StringBuilder sb = new StringBuilder();
		sb.append("Exception:" + message + "\r\n");

		for (CodePosition pos : traces) {
			sb.append("\tat ");
			sb.append(pos.toString());
			sb.append("\r\n");
		}
		return sb.toString();

	}

}
