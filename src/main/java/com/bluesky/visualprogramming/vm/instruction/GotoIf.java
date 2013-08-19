package com.bluesky.visualprogramming.vm.instruction;

public class GotoIf extends Instruction {
	public String destinationLabel;
	public boolean expected;
	public String actualVarName;

	@Override
	public String toString() {

		return String.format("[goto_if] goto %s if %s is %s", destinationLabel, actualVarName,
				expected);
	}
}
