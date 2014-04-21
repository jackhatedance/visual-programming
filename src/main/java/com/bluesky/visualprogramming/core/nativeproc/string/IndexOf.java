package com.bluesky.visualprogramming.core.nativeproc.string;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class IndexOf extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		StringValue strSV = (StringValue) ctx.get("str");

		StringValue selfStr = (StringValue) self;

		IntegerValue indexObj = getObjectFactory().createInteger();

		int index = selfStr.getValue().indexOf(strSV.getValue());
		indexObj.setIntValue(index);
		
		return indexObj;
	}
}
