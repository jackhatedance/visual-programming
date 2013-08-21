package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class VariableAssignment extends Instruction {

	public String left;
	public String right;

	public VariableAssignment() {
		this.type = InstructionType.VARIABLE_ASSIGNMENT;
	}

	@Override
	public String toString() {

		return String.format("[variableAssignment] %s->%s", left, right);
	}

}
