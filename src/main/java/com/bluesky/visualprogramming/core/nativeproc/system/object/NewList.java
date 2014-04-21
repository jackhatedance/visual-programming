package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.Prototypes;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * create a new list object.
 * 
 * @author Administrator
 * 
 */
public class NewList extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		
		_Object list = Prototypes.List.createInstance();
		

		return list;
	}

}
