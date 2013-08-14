package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.instruction.Instruction;

public class ProcedureExecutionContext {

	Map<String, _Object> localVariables;

	Instruction currentInstruction;
	int currentInstructionIndex;

	public Stack<BlockStackItem> blockStacks;

	private Set<_Object> tempObjects;

	public ProcedureExecutionContext() {
		localVariables = new HashMap<String, _Object>();
		blockStacks = new Stack<BlockStackItem>();
		tempObjects = new HashSet<_Object>();
	}

	public _Object getObject(String name) {

		if (localVariables.containsKey(name))
			return localVariables.get(name);
		else
			return null;
	}

	public void setObject(String name, _Object value) {
		localVariables.put(name, value);

	}

	public void setVariable(String name, _Object value) {
		localVariables.put(name, value);
	}

	public void putTempObject(_Object obj) {
		tempObjects.add(obj);
	}
}
