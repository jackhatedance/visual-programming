package com.bluesky.visualprogramming.core;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import org.omg.CORBA.RepositoryIdHelper;

import com.bluesky.visualprogramming.core.value.StringValue;

public class _ObjectTest {

	@Test
	public void test() {
		String decValue = "I'm a programmer that name=,./=-0@#$%^^&*&*((";
		String text ="type=STRING,id=4,name=desc,owner=1,value=I%27m+a+programmer+that+name%3D%2C.%2F%3D-0%40%23%24%25%5E%5E%26*%26*%28%28";
		_Object obj = new StringValue(0);
		obj.fromText(text);
		
		Assert.assertEquals(obj.getValue(),decValue);
	}

}
