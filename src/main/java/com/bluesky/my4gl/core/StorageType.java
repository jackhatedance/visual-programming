package com.bluesky.my4gl.core;

public enum StorageType {
	Class {
		@Override
		String toJavaString() {
			
			return "static";
		}
	}, Instance {
		@Override
		String toJavaString() {
			
			return "dynamic";
		}
	};

	abstract String toJavaString();
	public static StorageType getType(String s) {
		if (s != null && s.equalsIgnoreCase("static"))
			return Class;
		else if (s == null || s.equalsIgnoreCase("dynamic"))
			return Instance;
		else
			throw new RuntimeException("unknown storage type:" + s);

	}
	
	public static void main(String[] args) {
		System.out.println(StorageType.valueOf("Class"));
	}
	
	
}
