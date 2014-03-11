package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * set self as owner of field.
 * 
 * procedure (obj,fieldName)
 * 
 * @author Administrator
 * 
 */
public class OwnChild extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		_Object obj = ctx.get("obj");
		StringValue fieldName = (StringValue) ctx.get("fieldName");

		if (obj != null) {
			_Object child = obj.getChild(fieldName.getValue());
			if (child != null) {
				child.attachToOwner(obj, fieldName.getValue());
			}
		}

		return null;
	}

}
