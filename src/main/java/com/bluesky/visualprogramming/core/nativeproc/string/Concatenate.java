package com.bluesky.visualprogramming.core.nativeproc.string;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Concatenate extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		StringValue strSV = (StringValue) ctx.get("str");

		StringValue selfStr = (StringValue) self;



		
		String str = null;
		if(strSV==null)
			str="null";
		else
			str = strSV.getValue();
		
			
		String newStr = selfStr.getValue().concat(str);

		StringValue result = getObjectFactory().createString(newStr);

		return result;
	}
}
