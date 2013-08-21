package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class PushBlock extends Instruction {
	public BlockType blockType;

	public PushBlock() {
		this.type = InstructionType.PUSH_BLOCK;

	}

	@Override
	public String toString() {

		return "[push_block]";
	}
}
