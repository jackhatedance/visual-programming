package com.bluesky.visualprogramming.vm;

/**
 * suitable for instruction and procedure.
 * 
 * @author jackding
 * 
 */
public enum ExecutionStatus {
	/**
	 * means OK to be executed
	 */
	RUNNING(false),
	/**
	 * blocking, wait for sync reply
	 */
	WAITING(false),
	/**
	 * execution completed, no error.
	 */
	COMPLETE(true)
	,
	/**
	 * execution occurs error
	 */
	ERROR(true);

	private boolean isFinished;

	private ExecutionStatus(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean isTerminated() {
		return isFinished;
	}
}
