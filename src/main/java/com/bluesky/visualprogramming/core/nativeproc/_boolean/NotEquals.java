package com.bluesky.visualprogramming.core.nativeproc._boolean;

import java.util.Map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class NotEquals extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine,_Object self, ProcedureExecutionContext ctx) {
		BooleanValue bValue = (BooleanValue) ctx.get("b");

		BooleanValue selfValue = (BooleanValue) self;

		BooleanValue bv = (BooleanValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.BOOLEAN);

		bv.setBooleanValue(selfValue.getBooleanValue() != bValue
				.getBooleanValue());

		return bv;
	}
}
