package com.bluesky.visualprogramming.core.nativeproc.object;

import java.util.Map;
import java.util.UUID;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * every object is a map.
 * 
 * @author Administrator
 * 
 */
public class Set extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		StringValue nameSV = (StringValue) ctx.get("name");
		_Object value = ctx.get("value");

		boolean canIOwn = value.hasOwner() ? false : true;
		self.setChild(nameSV.getValue(), value, canIOwn);

		return null;
	}

}
