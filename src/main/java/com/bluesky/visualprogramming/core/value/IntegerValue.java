package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;

public class IntegerValue extends _Object {
	public IntegerValue(long id) {
		super(id);
		type = ObjectType.INTEGER;
	}

	Integer value;

	@Override
	public String getValue() {

		return String.valueOf(value);
	}

	@Override
	public void setValue(String value) {
		try {
			this.value = Integer.valueOf(value);
		} catch (Exception e) {
			this.value = null;

		}

	}

	public int getIntValue() {
		return value;
	}

	public void setIntValue(int i) {
		this.value = i;
	}
}
