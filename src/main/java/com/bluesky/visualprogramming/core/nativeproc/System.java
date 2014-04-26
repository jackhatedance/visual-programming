package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.VException;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public class System extends NativeClassSupport {


	@ParameterList({ "self" })
	public static _Object clone(_Object self) {
		// TODO

		return null;
	}

	@ParameterList({ "message" })
	public static _Object error(StringValue message) {

		VException ex = getObjectFactory().createException(message.getValue());

		return ex;
	}

	@ParameterList({ "self", "name" })
	public static _Object get(_Object self, StringValue name) {

		_Object value = self.getChild(name.getValue());

		return value;
	}

	@ParameterList({ "var" })
	public static _Object isNull(_Object var) {

		BooleanValue result = getObjectFactory().createBoolean(var == null);



		if (var != null) {
			java.lang.System.out.println("IsNull: obj ID" + var.getId());
		}

		return result;
	}

	@ParameterList({})
	public static _Object now() {

		TimeValue result = getObjectFactory().createTime();

		return result;
	}

	@ParameterList({ "time" })
	public static _Object sleep(IntegerValue time) {

		if (time == null)
			throw new RuntimeException("time is null");

		try {
			Thread.sleep(time.getIntValue());
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return null;
	}
}
