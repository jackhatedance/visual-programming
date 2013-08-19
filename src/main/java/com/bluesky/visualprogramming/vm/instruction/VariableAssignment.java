package com.bluesky.visualprogramming.vm.instruction;

public class VariableAssignment extends Instruction {

	public String left;
	public String right;

	@Override
	public String toString() {

		return String.format("[variableAssignment] %s->%s", left, right);
	}

}
