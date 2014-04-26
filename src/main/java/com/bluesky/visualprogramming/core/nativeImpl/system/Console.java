package com.bluesky.visualprogramming.core.nativeImpl.system;

import com.bluesky.visualprogramming.core.nativeproc.NativeClassSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.StringValue;

public class Console extends NativeClassSupport {

	@ParameterList({ "content" })
	public static void print(StringValue content){
		if (content != null)
			System.out.print(content.getValue());
		else
			System.out.print("null");
		
		 
	}
	
	@ParameterList({ "content" })
	public static void println(StringValue content){
		if (content != null)
			System.out.println(content.getValue());
		else
			System.out.println("null");
		
		 
	}
}
