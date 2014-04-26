package com.bluesky.visualprogramming.core.nativeproc.integer;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Divide extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		IntegerValue num = (IntegerValue) ctx.get("num");

		IntegerValue selfInt = (IntegerValue) self;

		IntegerValue result = getObjectFactory().createInteger(
				selfInt.getIntValue() / num.getIntValue());



		return result;
	}
}
