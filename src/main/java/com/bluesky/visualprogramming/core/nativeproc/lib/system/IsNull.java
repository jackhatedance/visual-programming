package com.bluesky.visualprogramming.core.nativeproc.lib.system;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * test if a var is pointing to a null object
 * 
 * @author Administrator
 * 
 */
public class IsNull extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		_Object obj = ctx.get("var");

		BooleanValue result = getObjectFactory().createBoolean();

		result.setBooleanValue(obj == null);

		if (obj != null) {
			System.out.println("IsNull: obj ID" + obj.getId());
		}

		return result;
	}

}
