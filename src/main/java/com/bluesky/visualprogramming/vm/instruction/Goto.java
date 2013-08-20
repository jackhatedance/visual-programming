package com.bluesky.visualprogramming.vm.instruction;

public class Goto extends Instruction {
	public String destinationLabel;
	
	@Override
	public String toString() {
	
		return String.format("[goto] %s",destinationLabel);
	}
}
