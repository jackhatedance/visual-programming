package com.bluesky.visualprogramming.core;

import org.junit.Test;

import com.bluesky.visualprogramming.core.serialization.dump.SerializationService;
import com.bluesky.visualprogramming.core.serialization.dump.SerializerType;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;

public class ObjectConverterTest {

	@Test
	public void test() {
		_Object root = new _Object(0);
		root.setName("root");

		IntegerValue age = new IntegerValue(1);
		age.setIntValue(18);
		age.setName("_age");
		age.setScope(ObjectScope.ExecutionContext);

		root.setField("age", age, true);

		StringValue name = new StringValue(2);
		name.setValue("jack");
		name.setName("_name");
		name.setScope(ObjectScope.ExecutionContext);
		root.setField("name", name, true);

		root.setField("nameLink", name, false);

		Link link = new Link(3);
		link.setValue("xmpp://jack@example.com");
		link.setScope(ObjectScope.ExecutionContext);
		root.setField("jacklink", link, true);

		SerializationService svc = new SerializationService();

		//System.out.println(svc.serialize(root, SerializerType.Json, true));
	}

}
