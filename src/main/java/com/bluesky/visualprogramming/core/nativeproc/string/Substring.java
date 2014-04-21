package com.bluesky.visualprogramming.core.nativeproc.string;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Substring extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		IntegerValue startIndexObj = (IntegerValue) ctx.get("startIndex");
		IntegerValue endIndexObj = (IntegerValue) ctx.get("endIndex");
		
		
		StringValue selfStr = (StringValue) self;

		StringValue result = getObjectFactory().createString();
		
		String substr =null;
		if(endIndexObj==null)
			substr = selfStr.getValue().substring((int)startIndexObj.getIntValue());
		else
			substr = selfStr.getValue().substring((int)startIndexObj.getIntValue(), (int)endIndexObj.getIntValue());

		result.setValue(substr);
		
		return result;
	}
}
