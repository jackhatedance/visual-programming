package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

/**
 * for label purpose
 * 
 * @author jackding
 * 
 */
public class NoOperation extends Instruction {

	public NoOperation(int line) {
		super(line);
		
		this.type = InstructionType.NO_OPERATION;
	}
	@Override
	public String toString() {
		return "[no_op]";

	}
}
