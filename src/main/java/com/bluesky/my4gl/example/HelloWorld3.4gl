package com.bluesky.my4gl.example;

import com.bluesky.my4gl.lang.Object;
import com.bluesky.my4gl.lang.Integer;
import com.bluesky.my4gl.lang.String;
import com.bluesky.my4gl.lang.Array;
import com.bluesky.my4gl.io.DebugConsole;

public class HelloWorld2 extends Object {

	public static void main(Array args)	{
		Integer i;
		String s;
		
		i=Integer$.new();	
		i.init(0);
		
		for(i.init(0);i.lt(10);i.increase(1)){
			s = i.toString();
			DebugConsole$.println(s);
			DebugConsole$.println("Hello 世界!");			 
		}
	}
}