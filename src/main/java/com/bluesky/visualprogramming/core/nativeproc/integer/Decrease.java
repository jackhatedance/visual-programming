package com.bluesky.visualprogramming.core.nativeproc.integer;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Decrease extends BaseNativeProcedure implements NativeProcedure {

	

	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		
		IntegerValue selfInt = (IntegerValue) self;
		IntegerValue num = (IntegerValue) ctx.get("num");
		
		selfInt.setIntValue( selfInt.getIntValue()-num.getIntValue());
		
		IntegerValue result = selfInt;

		return result;
	}
}
