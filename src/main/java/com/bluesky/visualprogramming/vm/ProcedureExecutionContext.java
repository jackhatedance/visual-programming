package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.instruction.Instruction;

public class ProcedureExecutionContext {

	final static String VAR_RESULT = "result";

	Map<String, _Object> localVariables;

	//Instruction currentInstruction;
	int currentInstructionIndex=0;
	/**
	 * the code reaches the end of procedure.
	 */
	boolean procedureEnd=false;

	public Stack<BlockStackItem> blockStacks;

	private Set<_Object> tempObjects;
	
	ExecutionStatus executionStatus;
	
	//used for multi-step instructions(such as SendMessage)
	public int step=0;
	/**
	 * the reply stores here after the sync invoke returns.
	 */
	public _Object reply;

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

	public _Object getResult() {
		return localVariables.get(VAR_RESULT);
	}

	public void setResult(_Object value) {
		localVariables.put(VAR_RESULT, value);
	}

	public void putTempObject(_Object obj) {
		tempObjects.add(obj);
	}

	public boolean isProcedureEnd() {
		return procedureEnd;
	}

	public void setProcedureEnd(boolean procedureEnd) {
		this.procedureEnd = procedureEnd;
	}
	
	
}
