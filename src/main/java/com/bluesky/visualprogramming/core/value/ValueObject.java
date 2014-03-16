package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core._Object;

public abstract class ValueObject extends _Object {

	public ValueObject(long id) {
		super(id);
	}


	public void copyValue(_Object src) {
		setValue(src.getValue());


	}

}
