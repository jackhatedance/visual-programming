package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.core.ObjectType;

/**
 * create value object
 * 
 * @author jack
 * 
 */
public class CreateValueObject extends Instruction {

	public String varName;
	public ObjectType type;
	public String literalValue;

	// self.create('a',bar) hard link (by pointer/id)
	// self.link('a','bar') : self.a => bar;
	// self.a=>bar; or self.a.link(bar);

	// self.a=1 integer 1 first create object, belong to local variable. then
	// attached to self.a;
	// self.a.b access field, create a, then create b if field not exists.

	@Override
	public String toString() {
		return String.format("[create_value_object] %s -> [%s] %s", varName,
				type, literalValue);
	}

}
