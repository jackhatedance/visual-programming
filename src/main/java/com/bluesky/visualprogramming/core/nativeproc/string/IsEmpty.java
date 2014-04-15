package com.bluesky.visualprogramming.core.nativeproc.string;

import java.util.Map;

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

public class IsEmpty extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		

		StringValue selfStr = (StringValue) self;

		BooleanValue bv = (BooleanValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.BOOLEAN,ObjectScope.ExecutionContext);

		bv.setBooleanValue(selfStr.getValue().isEmpty());

		return bv;
	}
}
