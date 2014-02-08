package com.bluesky.visualprogramming.core.nativeproc.lib.system;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * test if a var is pointing to a null object
 * 
 * @author Administrator
 * 
 */
public class IsNull extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		_Object obj = ctx.get("var");

		BooleanValue result = (BooleanValue) virtualMachine
				.getObjectRepository().createObject(ObjectType.BOOLEAN,
						ObjectScope.ExecutionContext);

		result.setBooleanValue(obj == null);

		return result;
	}

}
