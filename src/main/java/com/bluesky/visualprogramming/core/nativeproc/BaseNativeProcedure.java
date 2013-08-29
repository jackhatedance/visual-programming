package com.bluesky.visualprogramming.core.nativeproc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.ExecutionStatus;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public abstract class BaseNativeProcedure implements NativeProcedure {

	public void execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx, Message msg) {

		_Object reply = execute(virtualMachine, self, ctx);

		msg.executionContext.setResult(reply);
		msg.executionContext.setExecutionStatus(ExecutionStatus.COMPLETE);
	};

	protected abstract _Object execute(VirtualMachine virtualMachine,
			_Object self, ProcedureExecutionContext ctx);

}
