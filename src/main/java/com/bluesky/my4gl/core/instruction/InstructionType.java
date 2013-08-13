package com.bluesky.my4gl.core.instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.parser.java.ClassTokenType;

public enum InstructionType {
	/**
	 * a=b
	 */
	Assignment {
		@Override
		public String getPattern() {

			return "([a-z_]{1}[\\w\\d_]*)[\\t\\s]*=[\\t\\s]*([a-z_]{1}[\\w\\d_]*)";
		}

		@Override
		public Instruction createInstruction(String s) {
			AssignmentInstruction instruction = new AssignmentInstruction();
			instruction.parseExpression(s);
			return instruction;
		}
	},
	/**
	 * caution: this type is not a primitive instruction.<br>
	 * foo = new Foo(a,b,c);
	 */
	CreateObject {
		@Override
		public String getPattern() {

			return "([a-z_]{1}[\\w\\d_]*)[\\t\\s]*=[\\t\\s]*new[\\t\\s]+([A-Z_]{1}[\\w\\d_]*)\\((.*)\\)";
		}

		@Override
		public Instruction createInstruction(String s) {
			CreateObjectInstruction instruction = new CreateObjectInstruction();

			instruction.parseExpression(s);
			return instruction;

		}
	},
	/**
	 * Integer i;
	 */
	VariableDeclaration {
		@Override
		public String getPattern() {
			return "([A-Z_]{1}[\\w\\d_]*)[\\t\\s]+([a-z_]{1}[\\w\\d_]*)";
		}

		@Override
		public Instruction createInstruction(String s) {
			DeclareLocalVariableInstruction instruction = new DeclareLocalVariableInstruction();
			instruction.parseExpression(s);

			return instruction;
		}
	},
	/*
	 * a=obj.field1
	 */
	FieldAccess {
		@Override
		public String getPattern() {

			return "([a-z_]{1}[\\w\\d_]*)[\\t\\s]*=[\\t\\s]*([a-z_][\\w\\d_]*)\\.([a-z_]{1}[\\w\\d_]*)";
		}

		@Override
		public Instruction createInstruction(String expression) {
			FieldAccessInstruction instruction = new FieldAccessInstruction();
			instruction.parseExpression(expression);
			return instruction;
		}
	},
	/**
	 * foo = bar.x(a,b,c);
	 */
	Invocation {
		@Override
		public String getPattern() {
			// [type] [result]=object.field.method(...);
			return "(([\\w\\d_]+)[\\t\\s]*=)?[\\t\\s]*(([\\w\\d_\\$]+\\.)*)([\\w\\d_]+)\\((.*)\\)";
		}

		@Override
		public Instruction createInstruction(String s) {
			MethodInvocationInstruction instruction = new MethodInvocationInstruction();
			instruction.parseExpression(s);

			return instruction;
		}
	};

	abstract public String getPattern();

	/**
	 * wrap core pattern with ^ and $ and spaces.
	 * 
	 * @return
	 */
	public String getLinePattern() {
		String linePattern = "^[\\t\\s]*" + getPattern() + ";?[\\t\\s]*$";
		return linePattern;
	}

	public Instruction createInstruction(String s) {
		throw new RuntimeException("unable to parse:" + s);
	}

	public static InstructionType getType(String s) {

		for (InstructionType it : InstructionType.values()) {

			if (s.matches(it.getLinePattern()))
				return it;

		}
		throw new RuntimeException("unknown instruction type:" + s);
	}

	public static void main(String[] args) {

		String[] ss = new String[] { "Integer i;", "p=b.self();", "a=b;",
				"a=b.c", "foo = Foo$.new(a,b,c);", "i.init();" };
		for (String s : ss) {
			InstructionType tt = InstructionType.getType(s);
			System.out.println(s + "  --  " + tt);

			// Instruction ins = tt.parse(s);
			// System.out.println(s + "  --  " + tt + " -- " + ins);
		}

	}
}
