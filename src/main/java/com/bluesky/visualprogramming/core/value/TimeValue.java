package com.bluesky.visualprogramming.core.value;

import java.util.Date;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ObjectVisitor;

public class TimeValue extends ValueObject {
	public TimeValue(long id) {
		super(id);
		type = ObjectType.TIME;
	}

	Date value = new Date();

	@Override
	public String getValue() {

		return String.valueOf(value.getTime());
	}

	@Override
	public void setValue(String value) {
		try {
			this.value.setTime(Long.valueOf(value));
		} catch (Exception e) {
			this.value = new Date();

		}

	}

	public long getLongValue() {		
		return value.getTime();
	}

	public void setLongValue(long l) {
		this.value.setTime(l);
	}
	
	public Date getDateValue() {
		return value;
	}
	
	@Override
	public String getHumanReadableText() {
		
		return value.toString();
	}
	@Override
	public void accept(ObjectVisitor visitor) {
		visitor.enter(this);

		visitor.leave(this);

	}


}
