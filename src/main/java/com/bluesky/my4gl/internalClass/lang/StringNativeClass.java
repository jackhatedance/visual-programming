package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * 
 * @author xp
 * 
 */
public class StringNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		NativeObject soSelf = (NativeObject) self;

		if (methodName.equals("concat")) {
			NativeObject result = (NativeObject) new StringClass()
					.createObject();

			NativeObject s = (NativeObject) parameters[0];

			result.setNativeValue(((String) soSelf.getNativeValue())
					.concat(((String) s.getNativeValue())));
			return result;

		} else if (methodName.equals("init")) {
			NativeObject p1 = (NativeObject) parameters[0];
			soSelf.setNativeValue(p1.getNativeValue());
			return null;
		} else {
			throw new RuntimeException("unknown message");
		}
	}

}
