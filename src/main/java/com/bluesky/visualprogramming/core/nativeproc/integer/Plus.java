package com.bluesky.visualprogramming.core.nativeproc.integer;

import java.util.Map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Plus extends BaseNativeProcedure implements NativeProcedure {

	public Plus() {
		this.parameterNams = new String[] { "num" };
	}

	@Override
	protected _Object execute(_Object self, Map<String, _Object> params) {
		IntegerValue num = (IntegerValue) params.get("num");

		IntegerValue selfInt = (IntegerValue) self;

		IntegerValue result = (IntegerValue) VirtualMachine.getInstance()
				.getObjectRepository().createObject(ObjectType.INTEGER);

		result.setIntValue(selfInt.getIntValue() + num.getIntValue());

		return result;
	}
}
