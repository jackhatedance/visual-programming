package com.bluesky.visualprogramming.vm.instruction;

import com.bluesky.visualprogramming.vm.InstructionType;

/**
 * for label purpose
 * 
 * @author jackding
 * 
 */
public class ProcedureEnd extends Instruction {

	public ProcedureEnd(int line) {
		super(line);
		
		this.type = InstructionType.PROCEDURE_END;
	}
	@Override
	public String toString() {
		return "[procedure_end]";

	}
}
