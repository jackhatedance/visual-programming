package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class PopBlock extends Instruction{

	public PopBlock(int line) {
		super(line);
		
		this.type=InstructionType.POP_BLOCK;
	}
	@Override
	public String toString() {
	
		return "[pop_block]";
	}
}
