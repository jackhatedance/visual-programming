package com.bluesky.visualprogramming.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.bluesky.visualprogramming.core._Object;

public class ProcedureExecutionContext {

	/**
	 * implicit variables:
	 */
	public final static String VAR_RESULT = "_result";	
	public final static String VAR_SELF = "_self";
	public final static String VAR_SUBJECT = "_subject";
	public final static String VAR_SESSION_USER = "_sessionUser";

	// the message body object.
	public final static String VAR_PARAMETERS = "_parameters";
	public final static String VAR_PARAMETER_STYLE = "_parameterStyle";

	Map<String, _Object> localVariables;

	// Instruction currentInstruction;
	int currentInstructionIndex = 0;
	/**
	 * the code reaches the end of procedure.
	 */
	boolean procedureEnd = false;

	public Stack<BlockStackItem> blockStacks;

	private Set<_Object> tempObjects;

	public ExecutionStatus executionStatus;	
	
	//detailed error info
	public String executionErrorMessage;
	public int executionErrorLine;
	
	// used for multi-step instructions(such as SendMessage)
	public int step = 0;
	/**
	 * the reply stores here after the sync invoke returns.
	 */
	public _Object reply;

	public ProcedureExecutionContext() {
		localVariables = new HashMap<String, _Object>();
		blockStacks = new Stack<BlockStackItem>();
		tempObjects = new HashSet<_Object>();
	}

	public _Object get(String name) {
		return this.getObject(name);
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

	
	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	public void setExecutionStatus(ExecutionStatus executionStatus) {
		this.executionStatus = executionStatus;
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
