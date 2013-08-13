package com.bluesky.my4gl.core.instruction.parameter;

import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

public class ClassParameter extends Parameter {
	private String className;

	public ClassParameter(String expression) {
		super(expression);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public com.bluesky.my4gl.core.Object getTargetObject(ExceutionContext ctx) {
		return ClassLibrary.getClass(className);

	}

}
