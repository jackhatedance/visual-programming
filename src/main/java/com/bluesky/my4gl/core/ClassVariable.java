package com.bluesky.my4gl.core;

public class ClassVariable extends Variable {
	protected AccessScope accessScope;

	/*
	 * the object refer to.
	 */
	private Object object;

	public AccessScope getAccessScope() {
		return accessScope;
	}

	public void setAccessScope(AccessScope accessScope) {
		this.accessScope = accessScope;
	}
 

	@Override
	public String toString() {
		String typeName = "";
		if (clazz != null)
			typeName = clazz.getName();
		return String.format("%s %s %s;", accessScope, typeName, name);
	}

}
