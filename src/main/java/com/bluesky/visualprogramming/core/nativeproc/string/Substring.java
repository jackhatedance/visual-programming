package com.bluesky.visualprogramming.core.nativeproc.string;

import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Substring extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		IntegerValue startIndexObj = (IntegerValue) ctx.get("startIndex");
		IntegerValue endIndexObj = (IntegerValue) ctx.get("endIndex");
		
		
		StringValue selfStr = (StringValue) self;

		StringValue result = (StringValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.STRING, ObjectScope.ExecutionContext);
		
		String substr =null;
		if(endIndexObj==null)
			substr = selfStr.getValue().substring((int)startIndexObj.getIntValue());
		else
			substr = selfStr.getValue().substring((int)startIndexObj.getIntValue(), (int)endIndexObj.getIntValue());

		result.setValue(substr);
		
		return result;
	}
}
