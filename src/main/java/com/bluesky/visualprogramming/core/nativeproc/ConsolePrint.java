package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class ConsolePrint extends BaseNativeProcedure implements
		NativeProcedure {
	
	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		StringValue content = (StringValue) ctx.getObject("content");

		System.out.println(content.getValue());

		return null;
	}
}
