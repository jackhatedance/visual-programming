package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;

public class ConsolePrint implements NativeProcedure {
	public com.bluesky.visualprogramming.core._Object execute(
			com.bluesky.visualprogramming.core._Object self,
			com.bluesky.visualprogramming.core.Message msg) {

		StringValue str = (StringValue) msg.body.getChild("str");

		System.out.println(str.getValue());

		return null;
	};
}
