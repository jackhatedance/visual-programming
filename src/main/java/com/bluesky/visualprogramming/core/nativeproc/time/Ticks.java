package com.bluesky.visualprogramming.core.nativeproc.time;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.TimeValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Ticks extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		TimeValue selfTime = (TimeValue) self;

		
		IntegerValue result = (IntegerValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.INTEGER, ObjectScope.ExecutionContext);

		result.setIntValue(selfTime.getLongValue());

		return result;
	}
}
