package com.bluesky.visualprogramming.core.nativeproc.map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Contains extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		StringValue keySV = (StringValue) ctx.get("key");

		MapObject mo = new MapObject(self);

		boolean result = mo.contains(keySV.getValue());

		VirtualMachine vm = VirtualMachine.getInstance();

		BooleanValue resultBV = (BooleanValue) vm.getObjectRepository()
				.createObject(ObjectType.BOOLEAN, ObjectScope.ExecutionContext);

		resultBV.setBooleanValue(result);

		return resultBV;

	}
}
