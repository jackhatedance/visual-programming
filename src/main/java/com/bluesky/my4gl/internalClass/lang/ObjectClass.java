package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.StorageType;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * this is the root class for all normal classes.
 * 
 * @author jack
 * 
 */
public class ObjectClass extends ClassImpl implements Class {

	public ObjectClass() {
		super();

		setNativeClass(new ObjectNativeClass());

		setDomain("com.bluesky.my4gl.lang");
		setName("Object");
		setSuperClass(null);

		/*
		 * method: String toString(){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.String");
		method.setName("toString");
		method.setNativeMethod(true);

		addMethod(method);

		/*
		 * method: String toNativeString(){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.String");
		method.setName("toNativeString");
		method.setNativeMethod(true);

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
		 * return itself method: Object self(){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Object");
		method.setName("self");
		method.setNativeMethod(true);

		addMethod(method);

		/*
		 * method: static assign(Object o)
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);
		method.setStorageType(StorageType.Class);

		method.setReturnClassName("com.bluesky.my4gl.lang.Object");
		method.setName("assign");
		method.setNativeMethod(true);

		addMethod(method);

		/*
		 * method: Class getClass();
		 */
		// Method methodGetClass = new Method();
		// methodGetClass.setAccessScope(AccessScope.Public);
		//
		// methodGetClass.setReturnType(clazz);
		// methodGetClass.setName("getClass");
		// methodGetClass.setMethodImplementationType(MethodImplementationType.
		// Native);
		//		
		// addMethod(methodGetClass);
	}

}
