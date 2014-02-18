package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public interface ObjectVisitor {

	Object enter(_Object object);

	Object leave(_Object object);

	Object enter(Field field);

	Object leave(Field field);

	Object enter(StringValue object);

	Object leave(StringValue object);

	Object enter(IntegerValue object);

	Object leave(IntegerValue object);

	Object enter(BooleanValue object);

	Object leave(BooleanValue object);

	Object enter(FloatValue object);

	Object leave(FloatValue object);

	Object enter(TimeValue object);

	Object leave(TimeValue object);

	Object enter(Link object);

	Object leave(Link object);

	Object enter(Procedure object);

	Object leave(Procedure object);

}
