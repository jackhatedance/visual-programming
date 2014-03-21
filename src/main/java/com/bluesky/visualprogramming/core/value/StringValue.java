package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ObjectVisitor;

public class StringValue extends ValueObject {
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

	@Override
	public String toString() {		
		return value;
	}

}
