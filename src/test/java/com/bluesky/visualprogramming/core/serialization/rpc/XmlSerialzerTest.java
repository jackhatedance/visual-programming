package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.serialization.XmlSerialzer;
import com.bluesky.visualprogramming.utils.Config;

public class XmlSerialzerTest {

	String xml = "<xml>"
			+ "<ToUserName name=\"xx\"><![CDATA[toUser]]></ToUserName>"
			+ "<FromUserName><![CDATA[fromUser]]></FromUserName>"
			+ "<CreateTime>1348831860</CreateTime>"
			+ "<MsgType><![CDATA[text]]></MsgType>"
			+ "<Content>http://tvunderground.org.ru/index.php?show=episodes&amp;sid=68159</Content>"
			+ "<MsgId>1234567890123456</MsgId>" + "<!--abc -->" + "</xml>";

	String xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+ "<current>\n"
			+ "  <city id=\"1796910\" name=\"Sandun\">\n"
			+ "    <coord lon=\"120.11\" lat=\"30.29\"/>\n"
			+ "    <country>CN</country>\n"
			+ "    <sun rise=\"2014-03-16T22:06:44\" set=\"2014-03-17T10:09:01\"/>\n"
			+ "  </city>\n"
			+ "  <temperature value=\"11.894\" min=\"11.894\" max=\"11.894\" unit=\"celsius\"/>\n"
			+ "  <humidity value=\"84\" unit=\"%\"/>\n"
			+ "  <pressure value=\"1013.16\" unit=\"hPa\"/>\n"
			+ "  <wind>\n"
			+ "    <speed value=\"1.41\" name=\"Calm\"/>\n"
			+ "    <direction value=\"221.502\" code=\"SW\" name=\"Southwest\"/>\n"
			+ "  </wind>\n" + "  <clouds value=\"20\" name=\"few clouds\"/>\n"
			+ "  <precipitation mode=\"no\"/>\n"
			+ "  <weather number=\"801\" value=\"few clouds\" icon=\"02n\"/>\n"
			+ "  <lastupdate value=\"2014-03-17T13:51:44\"/>\n"
			+ "</current>\n";

	@Test

	public void test() {
		XmlSerialzer s = new XmlSerialzer();

		Config config = new Config();

		_Object obj = s.deserialize(new StringReader(xml), config);

		Assert.assertTrue(obj.getChildCount() > 0);
		// _Object xml = obj.getChild("xml");
		_Object content = obj.getChild("Content");

		Assert.assertEquals(
				"http://tvunderground.org.ru/index.php?show=episodes&sid=68159",
				content.getValue());

		StringWriter sw = new StringWriter();
		s.serialize(obj, sw, config);

		System.out.println(sw.toString());

	}

	@Test
	public void test2() {
		XmlSerialzer s = new XmlSerialzer();

		Config config = new Config();

		_Object obj = s.deserialize(new StringReader(xml2), config);

		Assert.assertTrue(obj.getChildCount() > 0);
		// _Object xml = obj.getChild("xml");
		_Object temperatureValue = obj
				.getObjectByPath("temperature.attributes.value");

		Assert.assertEquals("11.894", temperatureValue.getValue());

		StringWriter sw = new StringWriter();
		s.serialize(obj, sw, config);

		System.out.println(sw.toString());

	}

}
