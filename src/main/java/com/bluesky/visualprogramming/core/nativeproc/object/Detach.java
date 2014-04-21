package com.bluesky.visualprogramming.core.nativeproc.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * detach from owner
 * 
 * @author Administrator
 * 
 */
public class Detach extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		if (self.hasOwner())
			self.getOwner().detachOwnedChild(self);

		return self;
	}

}
