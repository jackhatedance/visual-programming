package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.ClassLibrary;

public class BooleanClass extends ClassImpl implements Class {

	public BooleanClass() {

		setDomain("com.bluesky.my4gl.lang");
		setName("Boolean");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new BooleanNativeClass());

		/*
		 * variables
		 */

		/*
		 * constructor method: init(true|false){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("init");
		method.setNativeMethod(true);
		// null, primitive
		method.addParameter("b", null);

		addMethod(method);

		/*
		 * constructor method:boolean: and(b){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("and");
		method.setNativeMethod(true);
		// null, primitive
		method.addParameter("b", ClassLibrary.getClass(PrimitiveType.Boolean));

		addMethod(method);

		/*
		 * constructor method: boolean or(b){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("or");
		method.setNativeMethod(true);
		// null, primitive
		method.addParameter("b", ClassLibrary.getClass(PrimitiveType.Boolean));

		addMethod(method);

		/*
		 * constructor method: boolean not(){}
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.Boolean.getFullName());
		method.setName("not");
		method.setNativeMethod(true);

		addMethod(method);

	}

	@Override
	public Object createObject() {
		NativeObject<Boolean> instance = new NativeObject<Boolean>();
		instance.setClazz(this);

		return instance;
	}

	public Object createObject(boolean b) {
		NativeObject<Boolean> instance = new NativeObject<Boolean>();
		instance.setClazz(this);
		instance.setNativeValue(b);
		return instance;
	}
}
