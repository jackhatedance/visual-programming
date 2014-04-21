package com.bluesky.visualprogramming.core.nativeproc.lib.system;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.TimeValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Now extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		TimeValue result = getObjectFactory().createTime();

		return result;
	}
}
