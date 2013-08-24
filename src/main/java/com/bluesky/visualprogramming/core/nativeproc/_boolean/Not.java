package com.bluesky.visualprogramming.core.nativeproc._boolean;

import java.util.Map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Not extends BaseNativeProcedure implements NativeProcedure {

	public Not() {
		this.parameterNams = new String[] {};
	}

	@Override
	protected _Object execute(_Object self, Map<String, _Object> params) {

		BooleanValue selfValue = (BooleanValue) self;

		BooleanValue bv = (BooleanValue) VirtualMachine.getInstance()
				.getObjectRepository().createObject(ObjectType.BOOLEAN);

		bv.setBooleanValue(!selfValue.getBooleanValue());

		return bv;
	}
}
