package com.bluesky.visualprogramming.core.nativeproc.list;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Size extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		
		
		int size = self.getUserFieldsCount();

		IntegerValue result = (IntegerValue) virtualMachine
				.getObjectRepository().createObject(ObjectType.INTEGER,
						ObjectScope.ExecutionContext);
		result.setIntValue(size);

		return result;
	}
}
