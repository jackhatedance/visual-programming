package com.bluesky.visualprogramming.core.nativeproc.lib.system;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Sleep extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		IntegerValue time = (IntegerValue) ctx.get("time");

		if(time==null)
			throw new RuntimeException("time is null");
		
		try {
			Thread.sleep(time.getIntValue());
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return null;
	}
}
