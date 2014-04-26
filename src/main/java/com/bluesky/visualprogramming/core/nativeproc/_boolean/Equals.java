package com.bluesky.visualprogramming.core.nativeproc._boolean;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Equals extends BaseNativeProcedure implements
		NativeProcedure {

	 
	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		BooleanValue bValue = (BooleanValue) ctx.get("b");

		BooleanValue selfValue = (BooleanValue) self;

		BooleanValue bv = getObjectFactory().createBoolean(
				selfValue.getBooleanValue() == bValue.getBooleanValue());
		


		return bv;
	}
}
