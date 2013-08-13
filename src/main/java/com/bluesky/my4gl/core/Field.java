package com.bluesky.my4gl.core;

public class Field {
	protected AccessScope accessScope;
	protected StorageType storageType;
	protected String className;
	protected String name;

	public Field() {

	}

	public Field(AccessScope scope, StorageType storageType, String className,
			String name) {
		this.accessScope = scope;
		this.storageType = storageType;
		this.name = name;
		this.className = className;
	}

	public AccessScope getAccessScope() {
		return accessScope;
	}

	public void setAccessScope(AccessScope accessScope) {
		this.accessScope = accessScope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {

		return String.format("%s %s %s %s;", accessScope, storageType.toJavaString() ,className, name);
	}

}
