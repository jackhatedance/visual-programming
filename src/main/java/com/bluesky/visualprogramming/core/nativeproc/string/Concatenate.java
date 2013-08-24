package com.bluesky.visualprogramming.core.nativeproc.string;

import java.util.Map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Concatenate extends BaseNativeProcedure implements NativeProcedure {

	public Concatenate() {
		this.parameterNams = new String[] {"str"};
	}

	@Override
	protected _Object execute(_Object self, Map<String, _Object> params) {
		StringValue str = (StringValue) params.get("str");

		StringValue selfStr = (StringValue) self;

		StringValue result = (StringValue) VirtualMachine.getInstance()
				.getObjectRepository().createObject(ObjectType.STRING);

		String newStr = selfStr.getValue().concat(str.getValue()); 
		
		result.setValue(newStr);

		return result;
	}
}