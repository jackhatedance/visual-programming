package com.bluesky.visualprogramming.core;

/**
 * if tree operations all is done via this interface, we can intercept
 * operations, notify listeners, implement persistent memory model and operation
 * journal.
 * 
 * @author jack
 * 
 */
public interface ObjectTreeModel {
	//CRUD
	void setField(_Object obj,String name, _Object child, boolean own) ;
	//void addField(_Object obj, String name, _Object target);
	//void addField(_Object obj, String name);
	Field getField(_Object obj, String name);
	void removeField(_Object obj, String name);
	
	//listener
	void addListener(_Object target, ObjectListener listener);
	void removeListener(_Object target, ObjectListener listener);
	
}
