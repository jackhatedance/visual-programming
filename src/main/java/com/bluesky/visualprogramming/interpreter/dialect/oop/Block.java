package com.bluesky.visualprogramming.interpreter.dialect.oop;

import java.util.List;

public class Block implements Operation {

	List<Operation> operations;

	Operation currentOperation;

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

}
