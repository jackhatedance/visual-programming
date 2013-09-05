package com.bluesky.visualprogramming.core;

import org.junit.Test;

import com.bluesky.visualprogramming.core.serialization.SerializationService;
import com.bluesky.visualprogramming.core.serialization.SerializerType;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class ObjectConverterTest {

	@Test
	public void test() {
		_Object root = new _Object(0);
		root.setName("root");

		IntegerValue age = new IntegerValue(1);
		age.setIntValue(18);
		age.setName("_age");
		age.setScope(ObjectScope.ExecutionContext);

		root.addChild(age, "age", true);

		StringValue name = new StringValue(2);
		name.setValue("jack");
		name.setName("_name");
		name.setScope(ObjectScope.ExecutionContext);
		root.addChild(name, "name", true);

		root.addChild(name, "nameLink", false);

		Link link = new Link(3);
		link.setValue("xmpp://jack@example.com");
		link.setScope(ObjectScope.ExecutionContext);
		root.addChild(link, "jacklink", true);

		SerializationService svc = new SerializationService();

		System.out.println(svc.serialize(root, SerializerType.Json, true));
	}

}
