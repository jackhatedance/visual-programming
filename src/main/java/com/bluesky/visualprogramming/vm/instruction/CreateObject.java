package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.vm.InstructionType;

/**
 * create value object
 * 
 * @author jack
 * 
 */
public class CreateObject extends Instruction {

	public String varName;
	public ObjectType objType;
	/**
	 * it is the textual form of Integer,Float,String,Boolean, etc. note that ,
	 * the value of string "a\\c" is 'a\c' without quotation marks and escaped
	 * characters. Anyway, the value can be used by valueOf(string) and
	 * toString(). *
	 */
	public String value;

	public CreateObject(int line) {
		super(line);
		
		this.type = InstructionType.CREATE_OBJECT;
	}

	// self.create('a',bar) hard link (by pointer/id)
	// self.link('a','bar') : self.a => bar;
	// self.a=>bar; or self.a.link(bar);

	// self.a=1 integer 1 first create object, belong to local variable. then
	// attached to self.a;
	// self.a.b access field, create a, then create b if field not exists.

	@Override
	public String toString() {
		return String.format("[create_object] %s -> [%s] [%s]", varName, objType,
				value);
	}

}
