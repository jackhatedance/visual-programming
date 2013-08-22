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

public class Increase extends BaseNativeProcedure implements NativeProcedure {

	public Increase() {
		this.parameterNams = new String[] {"num"};
	}

	@Override
	protected _Object execute(_Object self, Map<String, _Object> params) {
		
		IntegerValue selfInt = (IntegerValue) self;
		IntegerValue num = (IntegerValue) getUniqueParameter();
		
		selfInt.setIntValue( selfInt.getIntValue()+num.getIntValue());
		
		IntegerValue result = selfInt;

		return result;
	}
}
