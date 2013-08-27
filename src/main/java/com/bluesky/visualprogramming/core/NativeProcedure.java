package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public interface NativeProcedure {

	public void execute(_Object self,  ProcedureExecutionContext ctx, Message msg);
}
