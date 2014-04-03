package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;

public interface ObjectFactory {
	_Object createObject();

	IntegerValue createInteger();

	StringValue createString();

	BooleanValue createBoolean();

	Link createLink(String address);

	VException createException();
}
