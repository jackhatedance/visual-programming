package com.bluesky.my4gl.core.instruction.parameter;

import com.bluesky.my4gl.core.interpreter.ExceutionContext;

/**
 * parameters can be variable like a,b,c or be primitive constant like
 * 123,1.05f,"abc",'a'
 * 
 * @author jack
 * 
 */
public abstract class Parameter {

	private String expression;

	public Parameter(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * get the target object, the my4gl.Object object.
	 * 
	 * @param ctx
	 * @return
	 */
	public abstract com.bluesky.my4gl.core.Object getTargetObject(
			ExceutionContext ctx);

}
