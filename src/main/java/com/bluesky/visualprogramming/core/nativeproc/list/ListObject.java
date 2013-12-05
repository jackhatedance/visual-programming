package com.bluesky.visualprogramming.core.nativeproc.list;

import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class ListObject {

	private _Object obj;

	public ListObject(_Object obj) {
		this.obj = obj;
	}

	public void add(_Object item) {

		obj.setField(null, item, true);

	}

	public void clear() {

		int offset = obj.getSystemFieldsCount();

		while (obj.getChildCount() > offset)
			obj.removeField(offset);

	}

	public _Object get(int index) {

		long offset = obj.getSystemFieldsCount();
		long pos = offset + index;

		return obj.getChild((int) pos);

	}

	public _Object insert(int index, _Object item) {

		int offset = obj.getSystemFieldsCount();
		int pos = offset + index;
		obj.insertChild(pos, item, true);

		return null;
	}

	public _Object remove(int index) {

		int offset = obj.getSystemFieldsCount();
		int pos = offset + index;

		obj.removeField(pos);

		return null;
	}

	public _Object size() {

		int size = obj.getUserFieldsCount();

		VirtualMachine vm = VirtualMachine.getInstance();

		IntegerValue result = (IntegerValue) vm.getObjectRepository()
				.createObject(ObjectType.INTEGER, ObjectScope.ExecutionContext);
		result.setIntValue(size);

		return result;
	}
}
