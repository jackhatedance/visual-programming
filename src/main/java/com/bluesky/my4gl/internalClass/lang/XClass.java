package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * it is a self-service class, the class of itself is itself. which means as a
 * object, its class is itself(also as a class).
 * 
 * @author hz00260
 * 
 */
public class XClass extends ClassImpl implements Class {
	private static XClass instance = new XClass();

	public static XClass getInstance() {
		return instance;
	}

	private XClass() {
		super();

		setClazz(this);

		setNativeClass(new XNativeClass());

		setDomain("com.bluesky.my4gl.lang");
		setName("X");
		setSuperClass(null);

		/*
		 * method: Object new()
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Object");
		method.setName("new");
		method.setNativeMethod(true);

		addMethod(method);

	}

	@Override
	public Object createObject() {
		Object instance = super.createObject();
		instance.setClazz(this);

		return instance;
	}

}
