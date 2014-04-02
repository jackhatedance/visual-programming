package com.bluesky.visualprogramming.vm;

/**
 * suitable for instruction and procedure.
 * 
 * @author jackding
 * 
 */
public enum ExecutionStatus {
	ON_GOING(false), WAITING(false), COMPLETE(true)

	, ERROR(true);

	private boolean isFinished;

	private ExecutionStatus(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean isTerminated() {
		return isFinished;
	}
}
