package com.bluesky.visualprogramming.core.nativeproc.time;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.TimeValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Ticks extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		TimeValue selfTime = (TimeValue) self;

		
		IntegerValue result = getObjectFactory().createInteger(
				selfTime.getLongValue());



		return result;
	}
}
