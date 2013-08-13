package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.StorageType;
import com.bluesky.my4gl.global.ClassLibrary;

public class IntegerClass extends ClassImpl implements Class {

	public IntegerClass() {

		setDomain("com.bluesky.my4gl.lang");
		setName("Integer");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new IntegerNativeClass());

		/*
		 * variables
		 */

		/*
		 * constructor method: init(int i){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("init");
		method.setNativeMethod(true);
		// null, primitive
		method.addParameter("i", ClassLibrary.getClass(PrimitiveType.Integer));

		addMethod(method);

		/*
		 * method: Integer add(Integer n){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Integer");
		method.setName("add");
		method.setNativeMethod(true);

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);

		/*
		 * method: Integer subtract(Integer n){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Integer");
		method.setName("subtract");
		method.setNativeMethod(true);

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);
		/*
		 * method: void increase(Integer i){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("increase");
		method.setNativeMethod(true);

		method.addParameter("i", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);

		/*
		 * method: boolean equals(Object obj){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("equals");
		method.setNativeMethod(true);

		method.addParameter("obj", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Object"));

		addMethod(method);

		/*
		 * method: boolean lt(Integer i){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("lt");
		method.setNativeMethod(true);

		method.addParameter("i", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);
		/*
		 * method: boolean gt(Integer i){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("gt");
		method.setNativeMethod(true);

		method.addParameter("i", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		addMethod(method);

		/*
		 * method: static Integer valueOf(String s){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);
		method.setStorageType(StorageType.Class);

		method.setReturnClassName(PrimitiveType.Integer.getFullName());
		method.setName("valueOf");
		method.setNativeMethod(true);

		method.addParameter("s", ClassLibrary.getClass(PrimitiveType.String));

		addMethod(method);

		/*
		 * method: void toString(){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.String.getFullName());
		method.setName("toString");
		method.setNativeMethod(true);

		addMethod(method);
	}

	@Override
	public Method findMethod(String name, Class visitorClass) {

		return super.findMethod(name, visitorClass);
	}

	@Override
	public Object createObject() {

		NativeObject<Integer> instance = new NativeObject<Integer>();
		instance.setClazz(this);

		return instance;
	}

	public Object createObject(Integer i) {
		NativeObject<Integer> instance = new NativeObject<Integer>();
		instance.setClazz(this);
		instance.setNativeValue(i);
		return instance;
	}
}
