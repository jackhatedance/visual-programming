package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.BasicObjectFactory;
import com.bluesky.visualprogramming.core._Object;

public abstract class ValueObject extends _Object {

	public ValueObject(long id) {
		super(id);
	}

	@Override
	public void copy(_Object src, boolean deep, BasicObjectFactory factory) {
	
		super.copy(src, deep, factory);
		
		setValue(src.getValue());		
	}

	public void copyValue(_Object src) {
		setValue(src.getValue());

	}

	@Override
	public void setField(String name, _Object child, boolean own) {

		// only accept system field
		if (name != null && name.charAt(0) != '_')
			throw new RuntimeException("cannot add user field to value object");
		else
			super.setField(name, child, own);

	}

	@Override
	public String toString() {
		return getValue();
	}
}
