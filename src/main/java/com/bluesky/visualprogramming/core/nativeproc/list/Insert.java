package com.bluesky.visualprogramming.core.nativeproc.list;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Insert extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		IntegerValue index = (IntegerValue) ctx.get("index");
		_Object item = ctx.get("element");

		ListObject lo = new ListObject(self);
		lo.insert((int) index.getIntValue(), item);

		return null;
	}
}
