package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;

public class BooleanValue extends _Object {
	public BooleanValue(long id) {
		super(id);
		type = ObjectType.BOOLEAN;
	}

	Boolean value;

	@Override
	public String getValue() {

		return String.valueOf(value);
	}

	@Override
	public void setValue(String value) {
		try {
			this.value = Boolean.valueOf(value);
		} catch (Exception e) {
			this.value = null;

		}

	}
	
	public Boolean getBooleanValue(){
		return this.value;
	}
}
