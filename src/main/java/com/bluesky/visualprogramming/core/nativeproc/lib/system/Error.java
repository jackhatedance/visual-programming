package com.bluesky.visualprogramming.core.nativeproc.lib.system;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.VException;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * every object is a map.
 * 
 * @author Administrator
 * 
 */
public class Error extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		StringValue messageSV = (StringValue) ctx.get("message");

		VException ex = getObjectFactory().createException();
		ex.setMessage(messageSV.getValue());

		return ex;
	}

}
