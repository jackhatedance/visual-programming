package com.bluesky.my4gl.core.instruction.parameter;

import com.bluesky.my4gl.core.interpreter.ExceutionContext;

public class VariableParameter extends Parameter {
	private String name;

	public VariableParameter(String expression) {
		super(expression);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public com.bluesky.my4gl.core.Object getTargetObject(ExceutionContext ctx) {

		return ctx.getVariable(name);
	}

}
