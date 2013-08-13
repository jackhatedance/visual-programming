package com.bluesky.visualprogramming.vm;

import java.util.Map;
import java.util.Stack;

import com.bluesky.my4gl.core.flow.block.Block;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.instruction.Instruction;

public class ProcedureExecutionContext {

	Map<String, _Object> localVariables;

	Instruction currentInstruction;
	int currentInstructionIndex;

	public Stack<BlockStackItem> blockStacks;

	public _Object getObject(String name) {

		if (localVariables.containsKey(name))
			return localVariables.get(name);
		else
			return null;
	}

	public void setObject(String name, _Object value) {
		localVariables.put(name, value);

	}
}
