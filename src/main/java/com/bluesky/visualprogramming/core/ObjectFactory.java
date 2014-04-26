package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public interface ObjectFactory {
	_Object createObject();

	IntegerValue createInteger(long value);

	StringValue createString(String value);

	BooleanValue createBoolean(boolean value);

	Link createLink(String address);

	TimeValue createTime();

	VException createException(String message);
}
