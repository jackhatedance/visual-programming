package com.bluesky.visualprogramming.vm.instruction;

public class PushBlock extends Instruction{
	public BlockType type;
	@Override
	public String toString() {
	
		return "[push_block]";
	}
}
