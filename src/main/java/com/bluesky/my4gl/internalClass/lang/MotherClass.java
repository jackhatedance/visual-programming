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
public class MotherClass extends ClassImpl implements Class {

	public MotherClass() {
		super();

		setNativeClass(new MotherNativeClass());

		setDomain("com.bluesky.my4gl.lang");
		setName("Mother");
		setSuperClass(null);

		/*
		 * method: Object new()
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Object");
		method.setName("new");
		method.setNativeMethod(true);

		method.addParameter("class", null);

		addMethod(method);

	}

	@Override
	public Object createObject() {
		Object instance = super.createObject();
		instance.setClazz(this);

		return instance;
	}

}
