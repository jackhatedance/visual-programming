package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;

/**
 * Integer is actually a Long. won't bother to introduce int and long. As
 * long(64bit) is an better int(32bit).
 * 
 * @author jack
 * 
 */
public class IntegerValue extends _Object {
	public IntegerValue(long id) {
		super(id);
		type = ObjectType.INTEGER;
	}

	Long value;

	@Override
	public String getValue() {

		return String.valueOf(value);
	}

	@Override
	public void setValue(String value) {
		try {
			this.value = Long.valueOf(value);
		} catch (Exception e) {
			this.value = null;

		}

	}

	public long getIntValue() {
		return value;
	}

	public void setIntValue(long i) {
		this.value = i;
	}
}
