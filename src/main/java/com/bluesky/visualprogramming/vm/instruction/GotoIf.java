package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

public class GotoIf extends Instruction {
	public String destinationLabel;
	public boolean expected;
	public String actualVarName;
	
	public GotoIf() {
		this.type = InstructionType.GOTO_IF;
	}
	@Override
	public String toString() {

		return String.format("[goto_if] goto %s if %s is %s", destinationLabel, actualVarName,
				expected);
	}
}
