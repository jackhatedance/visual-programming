package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class FieldAssignment extends Instruction {

	// left
	public String ownerVar;
	// public _Object owner;

	public String fieldNameVar;
	public String fieldNameConst;

	public AssignmentType assignmenType;

	// right
	public String rightVar;

	// public _Object rightObject;

	public FieldAssignment() {
		this.type = InstructionType.FIELD_ASSIGNMENT;
	}

	@Override
	public String toString() {
		return String.format("[field_assignment] %s.$%s %s %s", ownerVar,
				fieldNameVar, assignmenType.getOperator(), rightVar);

	}

	public boolean isConstant() {
		return fieldNameConst != null;
	}
}
