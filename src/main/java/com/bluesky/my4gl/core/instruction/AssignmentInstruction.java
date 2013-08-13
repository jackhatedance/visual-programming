package com.bluesky.my4gl.core.instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class AssignmentInstruction extends Instruction {

	private static Logger logger = Logger
			.getLogger(AssignmentInstruction.class);

	private String leftVar;
	private String rightVar;

	/**
	 * left=right
	 */
	@Override
	public void parseExpression(String exp) {

		Pattern p = Pattern.compile(InstructionType.Assignment
				.getLinePattern());
		Matcher matcher = p.matcher(exp);

		if (matcher.matches()) {
			// for (int i = 1; i < matcher.groupCount() + 1; i++)
			// logger.info(i + ":[" + matcher.group(i) + "]");

			leftVar = matcher.group(1);

			rightVar = matcher.group(2);

		}
	}

	public String getLeftVar() {
		return leftVar;
	}

	public void setLeftVar(String leftVar) {
		this.leftVar = leftVar;
	}

	public String getRightVar() {
		return rightVar;
	}

	public void setRightVar(String rightVar) {
		this.rightVar = rightVar;
	}

	@Override
	public String toString() {

		return String.format("%s = %s", leftVar, rightVar);
	}

	public static void main(String[] args) {
		String s = "Integer i;";
		AssignmentInstruction ins = new AssignmentInstruction();
		ins.parseExpression(s);

		logger.info(ins);
	}
}
