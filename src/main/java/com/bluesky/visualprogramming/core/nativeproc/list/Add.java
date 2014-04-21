package com.bluesky.visualprogramming.core.nativeproc.list;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Add extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		_Object item = ctx.get("element");

		ListObject lo = new ListObject(self);
		lo.add(item);
		
		return null;
	}
}
