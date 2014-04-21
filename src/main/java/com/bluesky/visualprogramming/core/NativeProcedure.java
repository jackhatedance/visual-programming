package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public interface NativeProcedure {

	public void execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx);
}
