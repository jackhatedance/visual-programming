package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;

public class XmlSerialzerTest {

	String xml = "<xml>"
			+ "<ToUserName name=\"xx\"><![CDATA[toUser]]></ToUserName>"
			+ "<FromUserName><![CDATA[fromUser]]></FromUserName>"
			+ "<CreateTime>1348831860</CreateTime>"
			+ "<MsgType><![CDATA[text]]></MsgType>"
			+ "<Content><![CDATA[this is a test]]></Content>"
			+ "<MsgId>1234567890123456</MsgId>" 
			+"<!--abc -->"
			+ "</xml>";

	@Test
	public void test() {
		XmlSerialzer s = new XmlSerialzer();
		
		Config config = new Config();
		
		
		_Object obj = s.deserialize(new StringReader(xml), config);
		
		Assert.assertTrue(obj.getChildCount()>0);
		//_Object xml = obj.getChild("xml");
		_Object content = obj.getChild("Content");
		
		Assert.assertEquals("this is a test", content.getValue());

		StringWriter sw = new StringWriter();
		s.serialize(obj, sw, config);

		System.out.println(sw.toString());

	}

}
