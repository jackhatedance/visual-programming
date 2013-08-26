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

public class LessThanOrEqualTo extends BaseNativeProcedure implements
		NativeProcedure {

	 
	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		IntegerValue num = (IntegerValue) ctx.get("num");

		IntegerValue selfInt = (IntegerValue) self;

		BooleanValue bv = (BooleanValue) VirtualMachine.getInstance()
				.getObjectRepository().createObject(ObjectType.BOOLEAN);

		bv.setBooleanValue(selfInt.getIntValue() <= num.getIntValue());

		return bv;
	}
}
