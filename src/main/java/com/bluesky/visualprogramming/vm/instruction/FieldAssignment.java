package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.dialect.goo.Name;
import com.bluesky.visualprogramming.dialect.goo.NameType;
import com.bluesky.visualprogramming.vm.InstructionType;

public class FieldAssignment extends Instruction {

	// left
	public String ownerVar;
	// public _Object owner;
	
	public Name fieldName;

	public AssignmentType assignmentType;
	
	public ValueObjectAssignmentPolicy valueObjectAssignmentPolicy=ValueObjectAssignmentPolicy.Clone;

	// right
	public String rightVar;

	// public _Object rightObject;

	public FieldAssignment(int line) {
		super(line);
		
		this.type = InstructionType.FIELD_ASSIGNMENT;
	}

	@Override
	public String toString() {
		return String.format("[field_assignment] %s.%s %s %s", ownerVar,
				fieldName.getLiteral(), assignmentType.getOperator(), rightVar);

	}

}
