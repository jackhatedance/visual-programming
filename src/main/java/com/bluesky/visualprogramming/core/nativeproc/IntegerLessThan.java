package com.bluesky.visualprogramming.core.nativeproc;

import java.util.Map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class IntegerLessThan extends BaseNativeProcedure implements
		NativeProcedure {

	public IntegerLessThan() {
		this.parameterNams = new String[] { "num" };
	}

	@Override
	protected _Object execute(_Object self, Map<String, _Object> params) {
		IntegerValue num = (IntegerValue) params.get("num");

		IntegerValue selfInt = (IntegerValue) self;

		BooleanValue bv = (BooleanValue) VirtualMachine.getInstance()
				.getObjectRepository().createObject(ObjectType.BOOLEAN);

		return bv;
	}
}
