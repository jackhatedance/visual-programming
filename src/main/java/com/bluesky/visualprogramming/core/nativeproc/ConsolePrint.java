package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;

public class ConsolePrint implements NativeProcedure {
	public com.bluesky.visualprogramming.core._Object execute(
			com.bluesky.visualprogramming.core._Object self,
			com.bluesky.visualprogramming.core.Message msg) {

		StringValue content = (StringValue) msg.body.getChild("content");

		if(content==null)
			System.out.println("nothing");	
		
		System.out.println(content.getValue());

		return null;
	};
}
