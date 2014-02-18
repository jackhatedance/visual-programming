package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ObjectVisitor;
import com.bluesky.visualprogramming.core._Object;

public class BooleanValue extends _Object {
	public BooleanValue(long id) {
		super(id);
		type = ObjectType.BOOLEAN;
	}

	Boolean booleanValue;

	@Override
	public String getValue() {

		return String.valueOf(booleanValue);
	}

	@Override
	public void setValue(String value) {
		try {
			this.booleanValue = Boolean.valueOf(value);
		} catch (Exception e) {
			this.booleanValue = null;

		}

	}
	
	public Boolean getBooleanValue(){
		return this.booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}
	
	@Override
	public void accept(ObjectVisitor visitor) {
		
		super.accept(visitor);
	}
}
