package com.bluesky.visualprogramming.core.nativeproc.time;

import java.text.SimpleDateFormat;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class ToString extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		TimeValue selfTime = (TimeValue) self;

		StringValue formatSV = (StringValue) ctx.get("format");

		String format = "yyMMddHHmmssZ";
		if (formatSV != null)
			format = formatSV.getValue();

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		StringValue result = getObjectFactory().createString();

		result.setValue(String.valueOf(sdf.format(selfTime.getDateValue())));

		return result;
	}
}
