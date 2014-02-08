package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

/**
 * e.g. a = foo.bar;
 * 
 * @author jackding
 * 
 */
public class AccessField extends Instruction {

	public String varName;
	public String objName;
	public String fieldName;

	public AccessField(int line) {
		super(line);
		
		this.type = InstructionType.ACCESS_FIELD;
	}
	
	@Override
	public String toString() {
		return String.format("[access_field] %s ~>%s.%s", varName,objName, fieldName
				);

	}
}
