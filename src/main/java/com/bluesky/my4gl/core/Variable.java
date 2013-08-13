package com.bluesky.my4gl.core;

/**
 * local variable, for method
 * @author hz00260
 *
 */
public class Variable {

	protected String name;

	protected Class clazz;

	/*
	 * the object refer to.
	 */
	private Object object;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		String typeName = "";
		if (clazz != null)
			typeName = clazz.getName();
		return String.format(" %s %s;", typeName, name);
	}

}
