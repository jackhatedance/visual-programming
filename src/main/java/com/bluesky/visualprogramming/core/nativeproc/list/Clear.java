package com.bluesky.visualprogramming.core.nativeproc.list;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Clear extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		ListObject lo = new ListObject(self);

		lo.clear();
		
		return null;
	}
}
