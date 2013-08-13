package com.bluesky.visualprogramming.core;

public class Procedure extends _Object {

	public Procedure(long id) {
		super(id);
		type = ObjectType.PROCEDURE;
	}

	boolean _native=false;
	
	String nativeProcedureClassName;
	
	
	String code;

	@Override
	public String getValue() {

		return code;
	}

	@Override
	public void setValue(String value) {
		try {
			this.code = value;
		} catch (Exception e) {
			this.code = null;

		}

	}
	
	public boolean isNative()
	{
		return _native;
	}
}
