package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.ExecutionStatus;

public interface NativeProcedure {

	public ExecutionStatus execute(_Object self, Message msg);
}
