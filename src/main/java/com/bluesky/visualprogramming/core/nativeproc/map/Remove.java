package com.bluesky.visualprogramming.core.nativeproc.map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Remove extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		StringValue keySV = (StringValue) ctx.get("key");

		MapObject mo = new MapObject(self);
		mo.remove(keySV.getValue());
		
		return null;

	}
}
