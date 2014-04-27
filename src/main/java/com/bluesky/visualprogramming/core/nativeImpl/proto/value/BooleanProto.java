package com.bluesky.visualprogramming.core.nativeImpl.proto.value;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class BooleanProto extends NativeMethodSupport {

	@ParameterList({ "self", "b" })
	public static _Object equals(_Object self, BooleanValue b) {

		BooleanValue selfValue = (BooleanValue) self;

		BooleanValue result = getObjectFactory().createBoolean(
				selfValue.getBooleanValue() == b.getBooleanValue());

		return result;
	}

	@ParameterList({ "self" })
	public static _Object isFalse(BooleanValue self) {

		BooleanValue bv = getObjectFactory().createBoolean(
				!self.getBooleanValue());

		return bv;
	}

	@ParameterList({ "self", "b" })
	public static  _Object notEquals(BooleanValue self, BooleanValue b) {

		BooleanValue result = getObjectFactory().createBoolean(
				self.getBooleanValue() != b.getBooleanValue());

		return result;
	}
}
