package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.ClassLibrary;

public class ArrayClass extends ClassImpl implements Class {

	public ArrayClass() {

		/**
		 * class stuff
		 */
		setDomain("com.bluesky.my4gl.lang");
		setName("Array");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new ArrayNativeClass());

		/*
		 * variables
		 */

		/*
		 * method: void init(int size){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void"); 
		method.setName("init");
		method.setNativeMethod(true);

		method.addParameter("size", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);

		/*
		 * method: int size(){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Integer");
		method.setName("size");
		method.setNativeMethod(true);

		addMethod(method);

		/*
		 * method: Object get(int index){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Object");
		method.setName("get");
		method.setNativeMethod(true);

		method.addParameter("index", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);

		/*
		 * method: void set(int index,Object obj){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("set");
		method.setNativeMethod(true);

		method.addParameter("index", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));
		method.addParameter("obj", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Object"));

		addMethod(method);
	}

	@Override
	public Object createObject() {

		NativeObject<Object[]> instance = new NativeObject<Object[]>();
		instance.setClazz(this);

		return instance;
	}

	public Object createObject(int size) {

		NativeObject<Object[]> instance = new NativeObject<Object[]>();
		instance.setClazz(this);

		instance.setNativeValue(new Object[size]);

		return instance;
	}

}
