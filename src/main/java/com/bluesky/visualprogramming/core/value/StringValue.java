package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ObjectVisitor;
import com.bluesky.visualprogramming.core._Object;

public class StringValue extends _Object {
	public StringValue(long id) {
		super(id);
		type = ObjectType.STRING;
	}

	String value;

	@Override
	public String getValue() {

		return value;
	}

	@Override
	public void setValue(String value) {

		this.value = value;

	}

	@Override
	public void accept(ObjectVisitor visitor) {
		visitor.enter(this);

		visitor.leave(this);

	}
}
