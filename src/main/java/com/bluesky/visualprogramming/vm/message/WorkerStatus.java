package com.bluesky.visualprogramming.vm.message;

public enum WorkerStatus {
	/**
	 * started or starting
	 */
	Running,
	/**
	 * means the executor paused
	 */
	Paused,
	/**
	 * exiting or exited
	 */
	Finished;
}
