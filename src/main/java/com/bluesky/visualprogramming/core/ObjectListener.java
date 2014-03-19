package com.bluesky.visualprogramming.core;

public interface ObjectListener {
	
	
	//field changes
	void beforeRemoveField(_Object target, String name);
	void onAddField(_Object target, Field field);
}
