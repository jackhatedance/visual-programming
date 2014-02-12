package com.bluesky.visualprogramming.core;

import java.util.Stack;

/**
 * exception object, as return value if error happened
 * 
 * @author jack
 * 
 */
public class VException extends _Object {
	private String message;
	private Stack<CodePosition> traces = new Stack<CodePosition>();

	public VException(long id) {
		super(id);

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Stack<CodePosition> getTraces() {
		return traces;
	}

	public void addTrace(CodePosition position) {
		traces.add(position);
	}
	
	public String getTrace(){
		StringBuilder sb = new StringBuilder();
		sb.append("Exception:" + message + "\\r\\n");
		
		while (!traces.isEmpty()) {
			CodePosition pos = traces.pop();
			sb.append(pos.toString());
			sb.append("\\r\\n");
		}
		return sb.toString();

	}

}
