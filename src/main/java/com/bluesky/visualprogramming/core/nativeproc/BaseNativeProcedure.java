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

public abstract class BaseNativeProcedure implements NativeProcedure {


	public BaseNativeProcedure() {
		// this.parameterNams = new String[] { "content" };
	}

	public ExecutionStatus execute(_Object self, ProcedureExecutionContext ctx,
			Message msg) {

		_Object reply = execute(self, ctx);

		msg.executionContext.setResult(reply);

		return ExecutionStatus.COMPLETE;
	};

	protected abstract _Object execute(_Object self,
			ProcedureExecutionContext ctx);

	
}
