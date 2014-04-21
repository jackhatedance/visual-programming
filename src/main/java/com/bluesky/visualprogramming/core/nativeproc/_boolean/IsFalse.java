package com.bluesky.visualprogramming.core.nativeproc._boolean;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class IsFalse extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		BooleanValue selfValue = (BooleanValue) self;

		BooleanValue bv = getObjectFactory().createBoolean();

		bv.setBooleanValue(!selfValue.getBooleanValue());

		return bv;
	}
}
