package com.bluesky.my4gl.example;

import com.bluesky.my4gl.lang.Object;
import com.bluesky.my4gl.lang.Integer;
import com.bluesky.my4gl.lang.String;
import com.bluesky.my4gl.lang.Array;
import com.bluesky.my4gl.io.DebugConsole;

public class HelloWorld2 extends Object {

	private static Integer i;
	private Integer i2;

	public Integer HelloWorld2(){
	
	}
	
	public static String sayHello(String name)	{
		String s;
		s = String$.new();
		s.init("Hello ");
		s=s.concat(name);
		
		result=s.concat("!");
			
	}
	
	public static Integer fib(Integer n)	{
		if(n.lt(2)){		   		  
		  result=Object$.assign(1);
		   
		}else{
		  result=this.fib(n.subtract(1)).add(this.fib(n.subtract(2)));
		}
					 
	}
	
	
	public static void mainFib(Array args)	{
		
		Integer n = Integer$.valueOf(args.get(0));
		Integer i = fib(n);
		DebugConsole$.println(i.toString());
		
		DebugConsole$.println(Object$.assign(1121).toString());
		
	}
	
	public static void testInt(Array args)	{
		
		Integer i1=Object$.assign(1);
		Integer i2=Object$.assign(99);
		i1=i2.add(10);
		
		DebugConsole$.println(i1.toString());
		DebugConsole$.println(i2.toString());
		
	}
	
	public static void main(Array args)	{
				 
	
		DebugConsole$.println(args.get(0));
		 	 
				 
		i=Integer$.new();	
		i.init(0);
		
		
		String s;		
		s = String$.new();		
		s.init("hello ");
		
		String name;
		name= args.get(0);
		
		String name2;
		name2=name;
		
		s=s.concat(name2);
		s=s.concat("!");
		
		String s2;
		s2=String$.new();
		
		for(i.init(0);i.lt(10);i.increase(1)){
			s2 = i.toString();
			DebugConsole$.println(s2);
			DebugConsole$.println(s);
			 
		}
		
		if(i.gt(100)){
			DebugConsole$.println("i>100");
		} else {
			DebugConsole$.println("i<=100");
		}
	}

}