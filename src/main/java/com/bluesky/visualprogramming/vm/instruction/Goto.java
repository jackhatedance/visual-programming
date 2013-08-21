package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class Goto extends Instruction {
	public String destinationLabel;
	
	public Goto() {
		this.type = InstructionType.GOTO;
	}
	@Override
	public String toString() {
	
		return String.format("[goto] %s",destinationLabel);
	}
}
