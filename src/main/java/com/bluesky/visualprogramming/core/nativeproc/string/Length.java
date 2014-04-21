package com.bluesky.visualprogramming.core.nativeproc.string;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Length extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		StringValue selfValue = (StringValue) self;

		IntegerValue result = getObjectFactory().createInteger();

		result.setIntValue(Integer.valueOf(selfValue.getValue().length()));

		return result;
	}
}
