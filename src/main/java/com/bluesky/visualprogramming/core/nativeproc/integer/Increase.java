package com.bluesky.visualprogramming.core.nativeproc.integer;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Increase extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine,_Object self, ProcedureExecutionContext ctx) {

		IntegerValue selfInt = (IntegerValue) self;
		IntegerValue num = (IntegerValue) ctx.get("num");
		
		if(num==null)
			throw new RuntimeException("error parameter 'num' is null.");

		selfInt.setIntValue(selfInt.getIntValue() + num.getIntValue());

		IntegerValue result = selfInt;

		return result;
	}
}
