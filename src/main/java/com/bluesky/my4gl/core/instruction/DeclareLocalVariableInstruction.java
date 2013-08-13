package com.bluesky.my4gl.core.instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class DeclareLocalVariableInstruction extends Instruction {

	private static Logger logger = Logger
			.getLogger(DeclareLocalVariableInstruction.class);

	private String varClassName;
	private String varName;

	/**
	 * eg. Foo foo;
	 */
	@Override
	public void parseExpression(String exp) {

		Pattern p = Pattern.compile(InstructionType.VariableDeclaration.getLinePattern());
		Matcher matcher = p.matcher(exp);

		if (matcher.matches()) {
			// for (int i = 1; i < matcher.groupCount() + 1; i++)
			// logger.info(i + ":[" + matcher.group(i) + "]");

			varClassName = matcher.group(1);

			varName = matcher.group(2);

		}
	}

	public String getVarClassName() {
		return varClassName;
	}

	public void setVarClassName(String varClassName) {
		this.varClassName = varClassName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	@Override
	public String toString() {

		return String.format("%s %s", varClassName, varName);
	}

	public static void main(String[] args) {
		String s = "Integer i;";
		DeclareLocalVariableInstruction ins = new DeclareLocalVariableInstruction();
		ins.parseExpression(s);

		logger.info(ins);
	}
}
