package com.bluesky.visualprogramming.core;

import java.io.StringWriter;
import java.io.Writer;
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
		StringWriter w = new StringWriter();
		printTrace(w);
		return w.toString();
	}

	public void printTrace(Writer writer) {
		try {
			writer.write("Exception:" + message + "\r\n");

			for (CodePosition pos : traces) {
				writer.write("\tat ");
				writer.write(pos.toString());
				writer.write("\r\n");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
