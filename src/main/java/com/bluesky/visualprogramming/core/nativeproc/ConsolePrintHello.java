package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;

public class ConsolePrintHello implements NativeProcedure {
	public com.bluesky.visualprogramming.core._Object execute(
			com.bluesky.visualprogramming.core._Object self,
			com.bluesky.visualprogramming.core.Message msg) {

		//no body required
		
		
		System.out.println("hello");

		return null;
	};
}
