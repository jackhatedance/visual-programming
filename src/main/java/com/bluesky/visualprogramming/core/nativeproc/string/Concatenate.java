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

public class Concatenate extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		StringValue strSV = (StringValue) ctx.get("str");

		StringValue selfStr = (StringValue) self;

		StringValue result = (StringValue) virtualMachine.getObjectRepository()
				.createObject(ObjectType.STRING, ObjectScope.ExecutionContext);

		
		String str = null;
		if(strSV==null)
			str="null";
		else
			str = strSV.getValue();
		
			
		String newStr = selfStr.getValue().concat(str);

		result.setValue(newStr);

		return result;
	}
}
