package com.bluesky.my4gl.core.instruction.parameter;

import com.bluesky.my4gl.core.interpreter.ExceutionContext;

public class ConstantParameter extends Parameter {
	private com.bluesky.my4gl.core.Object value;

	public ConstantParameter(String expression) {
		super(expression);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(com.bluesky.my4gl.core.Object value) {
		this.value = value;
	}

	@Override
	public com.bluesky.my4gl.core.Object getTargetObject(ExceutionContext ctx) {

		return value;
	}

}
