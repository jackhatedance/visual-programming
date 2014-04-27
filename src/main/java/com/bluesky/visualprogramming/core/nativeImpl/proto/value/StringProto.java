package com.bluesky.visualprogramming.core.nativeImpl.proto.value;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeClassSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class StringProto extends NativeClassSupport {

	@ParameterList({ "self", "str" })
	public static _Object concatenate(StringValue self, StringValue str) {

		String str2 = null;
		if (str == null)
			str2 = "null";
		else
			str2 = str.getValue();

		String newStr = self.getValue().concat(str2);

		StringValue result = getObjectFactory().createString(newStr);

		return result;
	}

	@ParameterList({ "self", "str" })
	public static _Object equals(StringValue self, StringValue str) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getValue().equals(str.getValue()));

		return bv;
	}

	@ParameterList({ "self", "str" })
	public static _Object indexOf(StringValue self, StringValue str) {

		int index = self.getValue().indexOf(str.getValue());

		IntegerValue indexObj = getObjectFactory().createInteger(index);
		return indexObj;
	}

	@ParameterList({ "self" })
	public static _Object isEmpty(StringValue self) {

		BooleanValue bv = getObjectFactory().createBoolean(
				self.getValue().isEmpty());

		return bv;
	}

	@ParameterList({ "self" })
	public static _Object length(StringValue self) {

		IntegerValue result = getObjectFactory().createInteger(
				Integer.valueOf(self.getValue().length()));

		return result;
	}

	@ParameterList({ "self", "old", "new" })
	public static _Object replaceAll(StringValue self, StringValue old,
			StringValue new1) {

		String oldStr = null;
		if (old != null)
			oldStr = old.getValue();

		String newStr = "";
		if (new1 != null)
			newStr = new1.getValue();

		String resultStr = self.getValue().replaceAll(oldStr, newStr);

		StringValue resultSV = getObjectFactory().createString(resultStr);

		return resultSV;
	}

	@ParameterList({ "self", "str" })
	public static _Object startWith(StringValue self, StringValue str) {

		boolean result = self.getValue().startsWith(str.getValue());
		BooleanValue resultObj = getObjectFactory().createBoolean(result);

		return resultObj;
	}

	@ParameterList({ "self", "startIndex", "endIndex" })
	public static _Object substring(StringValue self, IntegerValue startIndex,
			IntegerValue endIndex) {

		String substr = null;
		if (endIndex == null)
			substr = self.getValue().substring((int) startIndex.getIntValue());
		else
			substr = self.getValue().substring((int) startIndex.getIntValue(),
					(int) endIndex.getIntValue());

		StringValue result = getObjectFactory().createString(substr);

		return result;
	}

	@ParameterList({ "self" })
	public static _Object toInteger(StringValue self) {

		IntegerValue result = getObjectFactory().createInteger(
				Integer.valueOf(self.getValue()));

		return result;
	}

	@ParameterList({ "self" })
	public static _Object toLowerCase(StringValue self) {

		StringValue result = getObjectFactory().createString(
				String.valueOf(self.getValue().toLowerCase()));

		return result;
	}

	@ParameterList({ "self" })
	public static _Object toUpperCase(StringValue self) {

		StringValue result = getObjectFactory().createString(
				String.valueOf(self.getValue().toUpperCase()));

		return result;
	}

	@ParameterList({ "self" })
	public static _Object trim(StringValue self) {

		StringValue result = getObjectFactory().createString(
				String.valueOf(self.getValue().trim()));

		return result;
	}
}
