package com.bluesky.my4gl.core;

/**
 * Object means it is a object or instance of a certain class. define it as a
 * interface instead of a class, because I think maybe it is comfortable when I
 * want to implement some primitive data classes.
 * 
 * @author jack
 * 
 */
public interface Object {
	Class getClazz();

	void setClazz(Class clazz);

	boolean containsField(String fieldName);
	
	Object getFieldValue(String name);

	void setFieldValue(String name, Object obj);
	// Object sendMessage(String name, Object[] parameters);
}
