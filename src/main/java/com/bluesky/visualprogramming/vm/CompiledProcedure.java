package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluesky.visualprogramming.vm.instruction.Instruction;

/**
 * a procedure compiled to instructions
 * 
 * @author jack
 * 
 */
public class CompiledProcedure {

	List<Instruction> instructions;

	Map<String, Integer> labels = new HashMap<String, Integer>();

	public List<Instruction> getInstructions() {
		return this.instructions;
	}

	public Integer getLabelIndex(String label) {
		Integer index = labels.get(label);
		return index;
	}
}
