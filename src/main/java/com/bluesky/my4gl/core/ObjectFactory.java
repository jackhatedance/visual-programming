package com.bluesky.my4gl.core;

import com.bluesky.my4gl.internalClass.lang.NativeObject;

public class ObjectFactory {

	public Object createPrimitiveObject(Class clazz, String expression) {
		ObjectImpl obj;

		if (clazz.getName().equals("Integer")) {
			NativeObject<Integer> integerObject = new NativeObject<Integer>();

			integerObject.setNativeValue(Integer.valueOf(expression));
			obj = integerObject;
		} else if (clazz.getName().equals("String")) {
			NativeObject<String> strObj = new NativeObject<String>();
			strObj.setNativeValue(expression);
			obj = strObj;
		} else
			throw new RuntimeException("it is not a primitive class:"
					+ clazz.getFullName());

		obj.setClazz(clazz);

		return obj;
	}

	public Object createObject(Class clazz) {
		Object obj = new ObjectImpl();
		obj.setClazz(clazz);

		return obj;

	}

}
