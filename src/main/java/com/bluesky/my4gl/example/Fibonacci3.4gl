package com.bluesky.my4gl.example;

import com.bluesky.my4gl.lang.Object;
import com.bluesky.my4gl.lang.Integer;
import com.bluesky.my4gl.lang.String;
import com.bluesky.my4gl.lang.Array;
import com.bluesky.my4gl.io.DebugConsole;

public class Fibonacci3 extends Object {

	 
	
	public static Integer fib(Integer n)	{
		if(n.lt(2)){		   		  
		  result=Object$.assign(1);
		   
		}else{
		  result=this.fib(n.subtract(1)).add(this.fib(n.subtract(2)));
		}
					 
	}
	
	
	public static void main(Array args)	{
		Integer i = fib(8);
		DebugConsole$.println(i.toString());
		
	}
	
  

}