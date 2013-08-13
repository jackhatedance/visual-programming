package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.ClassLibrary;

public class FloatClass extends ClassImpl implements Class {

	public FloatClass() {

		setDomain("com.bluesky.my4gl.lang");
		setName("Float");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new FloatNativeClass());

		/*
		 * variables
		 */

		/*
		 * constructor method: init(float n){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("init");
		method.setNativeMethod(true);
		// null, primitive
		method.addParameter("n", ClassLibrary.getClass(PrimitiveType.Float));

		addMethod(method);

		/*
		 * method: Float add(Float n){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Float");
		method.setName("add");
		method.setNativeMethod(true);

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Float"));

		addMethod(method);

		/*
		 * method: Float subtract(Float n){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Float");
		method.setName("subtract");
		method.setNativeMethod(true);

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Float"));

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
		 * method: boolean lt(Float n){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("lt");
		method.setNativeMethod(true);

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Float"));

		addMethod(method);
		/*
		 * method: boolean gt(Float n){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("gt");
		method.setNativeMethod(true);

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Float"));

		addMethod(method);

		/*
		 * method: Float valueOf(String s){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Float.getFullName());
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

		NativeObject<Float> instance = new NativeObject<Float>();
		instance.setClazz(this);

		return instance;
	}

	public Object createObject(Float n) {
		NativeObject<Float> instance = new NativeObject<Float>();
		instance.setClazz(this);
		instance.setNativeValue(n);
		return instance;
	}
}
