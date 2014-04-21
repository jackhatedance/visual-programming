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
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		ListObject lo = new ListObject(self);

		int size = lo.size();

		VirtualMachine vm = VirtualMachine.getInstance();

		IntegerValue result = (IntegerValue) vm.getObjectRepository()
				.createObject(ObjectType.INTEGER, ObjectScope.ExecutionContext);
		result.setIntValue(size);

		return result;
	}
}
