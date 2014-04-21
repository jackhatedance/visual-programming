package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * get the internal ID
 * 
 * @author Administrator
 * 
 */
public class GetType extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		_Object obj = ctx.get("obj");

		StringValue sv = getObjectFactory().createString();

		sv.setValue(obj.getType().name());

		return sv;
	}

}
