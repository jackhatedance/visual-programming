package com.bluesky.my4gl.internalClass.io;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.StorageType;
import com.bluesky.my4gl.global.ClassLibrary;
import com.bluesky.my4gl.internalClass.lang.NativeObject;

public class DebugConsoleClass extends ClassImpl implements Class {

	public DebugConsoleClass() {
		super();

		setDomain("com.bluesky.my4gl.io");
		setName("DebugConsole");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new DebugConsoleNativeClass());
		/*
		 * method: void println(string s);
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);
		method.setStorageType(StorageType.Class);
		method.setReturnClassName("void");;
		method.setName("println");
		method.setNativeMethod(true);

		addMethod(method);
	}

	public static Object println(Object self, Object str) {

		NativeObject<String> StringObject = (NativeObject<String>) str;

		System.out.println(StringObject.getNativeValue());

		return null;
	}

	@Override
	public Object createObject() {
		Object instance = super.createObject();
		instance.setClazz(this);
		return instance;
	}
}
