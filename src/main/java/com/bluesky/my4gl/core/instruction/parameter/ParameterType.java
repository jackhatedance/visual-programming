package com.bluesky.my4gl.core.instruction.parameter;

import com.bluesky.my4gl.core.instruction.MethodInvocationInstruction;
import com.bluesky.my4gl.core.parser.java.expression.ExpressionType;
import com.bluesky.my4gl.global.ClassLibrary;
import com.bluesky.my4gl.internalClass.lang.BooleanClass;
import com.bluesky.my4gl.internalClass.lang.CharacterClass;
import com.bluesky.my4gl.internalClass.lang.FloatClass;
import com.bluesky.my4gl.internalClass.lang.IntegerClass;
import com.bluesky.my4gl.internalClass.lang.PrimitiveType;
import com.bluesky.my4gl.internalClass.lang.StringClass;

public enum ParameterType {
	Integer {
		@Override
		public java.lang.String getPattern() {

			return "(\\d+)";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {
			IntegerClass intCls = (IntegerClass) ClassLibrary
					.getClass(PrimitiveType.Integer);
			com.bluesky.my4gl.core.Object result = intCls
					.createObject(new Integer(expression));

			return result;
		}

	},
	Float {
		@Override
		public java.lang.String getPattern() {

			return "(\\d+\\.\\d+)";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {
			FloatClass cls = (FloatClass) ClassLibrary
					.getClass(PrimitiveType.Float);
			com.bluesky.my4gl.core.Object result = cls.createObject(new Float(
					expression));

			return result;
		}

	},
	Character {
		@Override
		public java.lang.String getPattern() {

			return "(\'\\w\')";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {
			CharacterClass cls = (CharacterClass) ClassLibrary
					.getClass(PrimitiveType.Character);
			com.bluesky.my4gl.core.Object result = cls
					.createObject(new Character(expression.charAt(0)));

			return result;
		}
	},
	String {
		@Override
		public java.lang.String getPattern() {

			//2 types: includes '\"' and not includes '"'
			return "[^\\\\]?([\"](([^\"]*)|(([^\"]*\\\\\")*[^\"]*))[^\\\\][\"])";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {
			StringClass cls = (StringClass) ClassLibrary
					.getClass(PrimitiveType.String);
			
			//remove wrapped '"..."'
			expression=expression.substring(1, expression.length()-1);
			
			com.bluesky.my4gl.core.Object result = cls.createObject(expression);

			return result;
		}
	},
	Boolean {
		@Override
		public java.lang.String getPattern() {

			return "(true|false)";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {
			BooleanClass cls = (BooleanClass) ClassLibrary
					.getClass(PrimitiveType.Boolean);

			com.bluesky.my4gl.core.Object result = cls
					.createObject(java.lang.Boolean.getBoolean(expression));

			return result;
		}
	},
	Class {
		@Override
		public java.lang.String getPattern() {

			return "(([_a-zA-Z]+[_a-zA-Z\\d]*)\\.)*(([_A-Z]+[_\\w\\d]*))\\$";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {
			Object result = expression.substring(0, expression.length()
					- "$".length());
			return result;
		}

		@Override
		public Parameter parse(java.lang.String expression) {
			String className = (String) createValueObject(expression);
			ClassParameter p = new ClassParameter(expression);
			p.setClassName(className);
			return p;
		}
	},
	Variable {
		@Override
		public java.lang.String getPattern() {

			return "([_a-zA-Z_][\\w\\d]*)";
		}

		@Override
		public Object createValueObject(java.lang.String expression) {

			Object result = expression;
			return result;
		}

		@Override
		public Parameter parse(java.lang.String expression) {
			String variableName = (String) createValueObject(expression);
			VariableParameter p = new VariableParameter(expression);
			p.setName(variableName);

			return p;
		}
	};

	public abstract String getPattern();

	/**
	 * create a correct object for primitive expression
	 * 
	 * @param expression
	 * @return
	 */
	public abstract Object createValueObject(String expression);

	/**
	 * default implementation for constant parameters
	 * 
	 * @param expression
	 * @return
	 */
	public Parameter parse(String expression) {

		com.bluesky.my4gl.core.Object obj = (com.bluesky.my4gl.core.Object) createValueObject(expression);
		ConstantParameter p = new ConstantParameter(expression);
		p.setValue(obj);

		return p;
	}

	public static ParameterType getType(String expression) {
		for (ParameterType it : ParameterType.values()) {
			String pattern = "^[\\t\\s]*" + it.getPattern() + "[\\t\\s]*$";
			if (expression.matches(pattern))
				return it;

		}

		throw new RuntimeException("unknow expression:" + expression);
	}

	/**
	 * create a Parameter object for this expression
	 * 
	 * @param expression
	 * @return
	 */
	public static Parameter createPatameter(String expression) {
		Parameter p = getType(expression).parse(expression);

		return p;
	}

	public static void main(String[] args) {
		// MethodInvocationInstruction mii = new MethodInvocationInstruction();
		// mii.parseExpression("a=b.init(c)");
		// System.out.println(mii);

		String s = "com.bluesky.my4gl.lang.String$";
		System.out.println(ParameterType.getType(s));

	}
}
