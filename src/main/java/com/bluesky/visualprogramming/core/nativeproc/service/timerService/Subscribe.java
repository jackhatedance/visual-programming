package com.bluesky.visualprogramming.core.nativeproc.service.timerService;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Subscribe extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		_Object timer = (_Object) ctx.get("timer");

		
		getVM().getTimerService().subscribe(timer);
		
		return null;
	}
}
