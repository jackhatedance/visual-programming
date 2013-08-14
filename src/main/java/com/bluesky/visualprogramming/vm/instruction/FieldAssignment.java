package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.core._Object;

public class FieldAssignment extends Instruction {

	//left
	public String ownerVar;
	//public _Object owner;
	
	public String fieldName;
	
	public AssignmentType type;
	
	//right
	public String rightVar;
	//public _Object rightObject;

}
