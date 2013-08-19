package com.bluesky.visualprogramming.vm.instruction;

public class Goto extends Instruction {
	public String label;
	
	@Override
	public String toString() {
	
		return String.format("[goto] %s",label);
	}
}
