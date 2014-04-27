package com.bluesky.visualprogramming.core.nativeImpl.proto;

import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeClassSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class ObjectProto extends NativeClassSupport {

	@ParameterList({ "self", "owner", "name" })
	public static _Object attachTo(_Object self, _Object owner, StringValue name) {

		if (self.hasOwner())
			self.getOwner().detachOwnedChild(self);

		self.attachToOwner(owner, name.getValue());

		return self;
	}

	@ParameterList({ "self" })
	public static _Object detach(_Object self) {

		if (self.hasOwner())
			self.getOwner().detachOwnedChild(self);

		return self;
	}

	@ParameterList({ "self", "name", "child" })
	public static _Object forceOwn(_Object self, StringValue name, _Object child) {

		if (child.hasOwner())
			child.getOwner().detachOwnedChild(child);

		self.setField(name.getValue(), child, true);

		return self;
	}

	@ParameterList({ "self", "name", "child" })
	public static _Object get(_Object self, StringValue name) {

		_Object value = self.getChild(name.getValue());

		return value;
	}

	@ParameterList({ "self" })
	public static _Object getScope(_Object self) {

		StringValue result = getObjectFactory().createString(
				self.getScope().toString());

		return result;
	}

	@ParameterList({ "self", "srcFieldName", "dstObj", "dstFieldName" })
	public static _Object move(_Object self, StringValue srcFieldName,
			_Object dstObj, StringValue dstFieldName) {

		_Object srcObj = self;
		if (dstFieldName == null)
			dstFieldName = srcFieldName;// same name

		_Object childObj = srcObj.getChild(srcFieldName.getValue());
		srcObj.detachOwnedChild(childObj);

		dstObj.setField(dstFieldName.getValue(), childObj, true);

		return self;
	}

	@ParameterList({ "self", "name", "value" })
	public static _Object set(_Object self, StringValue name, _Object value) {

		boolean canIOwn = value.getScope() == ObjectScope.ExecutionContext;
		self.setField(name.getValue(), value, canIOwn);

		return null;
	}
}
