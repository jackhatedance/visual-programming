package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.ObjectImpl;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

public class MotherNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		if (methodName.equals("new")) {

			Class cls = (Class) parameters[0];

			Object instance = cls.createObject();
			instance.setClazz(cls);
			return instance;
		}else {
			throw new RuntimeException("unknown message");
		}

	}

	/**
	 * self is the Class, such as Foo.new. we reflect the class and initialize
	 * the new object.
	 * 
	 * @return
	 */
	private Object new_(Object self) {
		Class cls = (Class) self;

		/**
		 * 
		 */

		Object obj = new ObjectImpl();
		obj.setClazz(cls);
		// cls.getVariables();
		return obj;
	}
}
