package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ObjectVisitor;
import com.bluesky.visualprogramming.core._Object;

public class FloatValue extends _Object {
	public FloatValue(long id) {
		super(id);
		type = ObjectType.FLOAT;
	}

	Float value;

	@Override
	public String getValue() {

		return String.valueOf(value);
	}

	@Override
	public void setValue(String value) {
		try {
			this.value = Float.valueOf(value);
		} catch (Exception e) {
			this.value = null;

		}

	}

	public float getFloatValue() {
		return value;
	}

	public void setFloatValue(float f) {
		this.value = f;
	}
	
	@Override
	public void accept(ObjectVisitor visitor) {
		visitor.enter(this);

		visitor.leave(this);

	}
}
