package com.bluesky.visualprogramming.core;

import static org.junit.Assert.*;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.omg.CORBA.RepositoryIdHelper;

import com.bluesky.visualprogramming.core.value.StringValue;

public class _ObjectTest {

	@Ignore
	@Test
	public void test() {
		String decValue = "I'm a programmer that name=,./=-0@#$%^^&*&*((";
		String text ="type=STRING,id=4,name=desc,owner=1,value=I%27m+a+programmer+that+name%3D%2C.%2F%3D-0%40%23%24%25%5E%5E%26*%26*%28%28";
		_Object obj = new StringValue(0);
		//obj.fromText(text);
		
		Assert.assertEquals(obj.getValue(),decValue);
	}
	
	@Test
	public void testGroovy(){
		Binding binding = new Binding();
		binding.setVariable("str", "你好！");
		binding.setVariable("keyword", "你好");
		GroovyShell shell = new GroovyShell(binding);

		Object value = shell
				.evaluate("return str.contains(keyword)");
		Assert.assertEquals(true, value);
	}

}
