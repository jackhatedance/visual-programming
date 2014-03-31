package com.bluesky.visualprogramming.core;

public enum ObjectScope {

	Persistent(1), ExecutionContext(0);

	private int level;

	private ObjectScope(int level) {
		this.level = level;
	}


	public boolean stableThan(ObjectScope scope) {
		if (scope == null)
			throw new RuntimeException("param scope is null");

		return this.level > scope.level;
	}
}
