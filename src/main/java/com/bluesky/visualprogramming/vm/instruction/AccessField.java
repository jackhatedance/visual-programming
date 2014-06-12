package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.dialect.goo.Name;
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

	public Name fieldName;

	public AccessField(int line) {
		super(line);

		this.type = InstructionType.ACCESS_FIELD;
	}

	@Override
	public String toString() {		
		String str = String.format("[access_field] %s ~>%s.%s", varName, objName,
					fieldName.getLiteral());		

		return str;
	}


	
}
