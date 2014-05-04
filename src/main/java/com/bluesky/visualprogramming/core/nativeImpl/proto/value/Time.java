package com.bluesky.visualprogramming.core.nativeImpl.proto.value;

import java.text.SimpleDateFormat;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public class Time extends NativeMethodSupport {

	@ParameterList({ "self" })
	public static _Object ticks(TimeValue self) {

		IntegerValue result = getObjectFactory().createInteger(
				self.getLongValue());

		return result;
	}

	@ParameterList({ "self", "format" })
	public static _Object toString(TimeValue self, StringValue format) {

		String strFormat = "yyyy-MM-dd HH:mm:ss Z";
		if (format != null)
			strFormat = format.getValue();

		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

		StringValue result = getObjectFactory().createString(
				String.valueOf(sdf.format(self.getDateValue())));

		return result;
	}
}
