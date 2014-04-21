package com.bluesky.visualprogramming.core.nativeproc.string;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class ReplaceAll extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		StringValue oldSV = (StringValue) ctx.get("old");
		StringValue newSV = (StringValue) ctx.get("new");

		StringValue selfStr = (StringValue) self;

		StringValue resultSV = getObjectFactory().createString();

		String oldStr = null;
		if (oldSV != null)
			oldStr = oldSV.getValue();

		String newStr = "";
		if (newSV != null)
			newStr = newSV.getValue();

		String resultStr = selfStr.getValue().replaceAll(oldStr, newStr);

		resultSV.setValue(resultStr);

		return resultSV;
	}
}
