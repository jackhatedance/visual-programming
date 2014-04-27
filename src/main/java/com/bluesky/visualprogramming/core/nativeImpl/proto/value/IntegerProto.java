package com.bluesky.visualprogramming.core.nativeImpl.proto.value;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class IntegerProto extends NativeMethodSupport {

	@ParameterList({ "self", "num" })
	public static _Object increase(IntegerValue self, IntegerValue num) {

		if (num == null)
			throw new RuntimeException("error parameter 'num' is null.");

		self.setIntValue(self.getIntValue() + num.getIntValue());

		IntegerValue result = self;

		return result;
	}

	@ParameterList({ "self", "num" })
	public static _Object decrease(IntegerValue self, IntegerValue num) {

		self.setIntValue(self.getIntValue() - num.getIntValue());

		IntegerValue result = self;

		return result;
	}

	@ParameterList({ "self", "num" })
	public static _Object equals(IntegerValue self, IntegerValue num) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getIntValue() == num.getIntValue());

		return bv;
	}

	@ParameterList({ "self", "num" })
	public static _Object greaterThan(IntegerValue self, IntegerValue num) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getIntValue() > num.getIntValue());

		return bv;
	}

	@ParameterList({ "self", "num" })
	public static _Object lessThan(IntegerValue self, IntegerValue num) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getIntValue() < num.getIntValue());

		return bv;
	}

	@ParameterList({ "self", "num" })
	public static _Object greaterThanOrEquals(IntegerValue self,
			IntegerValue num) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getIntValue() >= num.getIntValue());

		return bv;
	}

	@ParameterList({ "self", "num" })
	public static _Object lessThanOrEquals(IntegerValue self, IntegerValue num) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getIntValue() <= num.getIntValue());

		return bv;
	}

	@ParameterList({ "self", "num" })
	public static _Object plus(IntegerValue self, IntegerValue num) {

		IntegerValue result = getObjectFactory().createInteger(
				self.getIntValue() + num.getIntValue());

		return result;
	}

	@ParameterList({ "self", "num" })
	public static _Object minus(IntegerValue self, IntegerValue num) {

		IntegerValue result = getObjectFactory().createInteger(
				self.getIntValue() - num.getIntValue());

		return result;
	}

	@ParameterList({ "self", "num" })
	public static _Object multiply(IntegerValue self, IntegerValue num) {

		IntegerValue result = getObjectFactory().createInteger(
				self.getIntValue() * num.getIntValue());

		return result;
	}

	@ParameterList({ "self", "num" })
	public static _Object divide(IntegerValue self, IntegerValue num) {

		IntegerValue result = getObjectFactory().createInteger(
				self.getIntValue() / num.getIntValue());

		return result;
	}

	@ParameterList({ "self", "num" })
	public static _Object modulus(IntegerValue self, IntegerValue num) {

		IntegerValue result = getObjectFactory().createInteger(
				self.getIntValue() % num.getIntValue());

		return result;
	}

	@ParameterList({ "self" })
	public static _Object toString(IntegerValue self) {

		StringValue result = getObjectFactory().createString(
				String.valueOf(self.getIntValue()));

		return result;
	}
}
