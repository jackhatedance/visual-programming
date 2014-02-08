package com.bluesky.visualprogramming.core.nativeproc._boolean;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class IsFalse extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		BooleanValue selfValue = (BooleanValue) self;

		BooleanValue bv = (BooleanValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.BOOLEAN, ObjectScope.ExecutionContext);

		bv.setBooleanValue(!selfValue.getBooleanValue());

		return bv;
	}
}
