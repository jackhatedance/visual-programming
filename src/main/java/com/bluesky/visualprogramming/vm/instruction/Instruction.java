package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class Instruction {
	public int line;

	public InstructionType type;

	public String label;

	public String comment;

	public Instruction(int line) {
		this.line = line;
	}
	
	public boolean hasLabel() {

		return label != null && !label.isEmpty();
	}
}
