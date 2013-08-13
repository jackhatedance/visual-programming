package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.ClassLibrary;

public class StringClass extends ClassImpl implements Class {

	public StringClass() {

		/**
		 * class stuff
		 */
		setDomain("com.bluesky.my4gl.lang");
		setName("String");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new StringNativeClass());

		/*
		 * variables
		 */

		/*
		 * method: void init(string s){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("init");
		method.setNativeMethod(true);

		method.addParameter("s", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));

		addMethod(method);

		/*
		 * method: String concat(String s){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName( "com.bluesky.my4gl.lang.String") ;
		method.setName("concat");
		method.setNativeMethod(true);

		method.addParameter("s", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));

		addMethod(method);
	}

	@Override
	public Object createObject() {

		NativeObject instance = new NativeObject();
		instance.setClazz(this);

		return instance;
	}

	public Object createObject(String s) {

		NativeObject instance = new NativeObject();
		instance.setClazz(this);
		
		instance.setNativeValue(s);

		return instance;
	}

}
