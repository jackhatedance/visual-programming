package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.ObjectImpl;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

public class XNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		if (methodName.equals("new")) {

			// the self object here actually is a normal class
			Class cls = (Class) self;

			Object instance = cls.createObject();
			instance.setClazz(cls);
			
			
			
			return instance;
		} else {
			throw new RuntimeException("unknown message");
		}

	}

	 
}
