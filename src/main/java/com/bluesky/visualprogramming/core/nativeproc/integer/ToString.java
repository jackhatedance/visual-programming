package com.bluesky.visualprogramming.core.nativeproc.integer;

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

public class ToString extends BaseNativeProcedure implements NativeProcedure {

	 
	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		IntegerValue selfInt = (IntegerValue) self;

		StringValue result = (StringValue) VirtualMachine.getInstance()
				.getObjectRepository().createObject(ObjectType.STRING);

		result.setValue(String.valueOf(selfInt.getIntValue()));

		return result;
	}
}
