package com.bluesky.visualprogramming.vm.instruction;

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

	@Override
	public String toString() {
		return String.format("[access_field] %s ~>%s.%s", varName,objName, fieldName
				);

	}
}
