package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * every class can has a native class if it has any native method
 * 
 * @author jack
 * 
 */
public class ObjectNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {

		if (methodName.equals("toString")) {

			NativeObject so = (NativeObject) ClassLibrary.getClass(
					PrimitiveType.String).createObject();
			so.setNativeValue("my4glObject:" + self.toString());
			return so;
		} else if (methodName.equals("toNativeString")) {

			NativeObject so = (NativeObject) ClassLibrary.getClass(
					PrimitiveType.String).createObject();

			if (self instanceof NativeObject) {
				java.lang.Object nativeObject = ((NativeObject) self)
						.getNativeValue();
				String s = nativeObject.getClass().getName() + '@'
						+ Integer.toHexString(nativeObject.hashCode());

				so.setNativeValue(s);

			} else
				so.setNativeValue("my4glObject:" + self.toString());
			return so;
		} else if (methodName.equals("equals")) {

			NativeObject<Boolean> result = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			Object pObj = parameters[0];

			result.setNativeValue(self.equals(pObj));
			return result;
		} else if (methodName.equals("self")) {

			return self;
		} else if (methodName.equals("assign")) {
			Object pObj1 = parameters[0];

			return pObj1;
		}

		else {
			throw new RuntimeException("unknown message");
		}

	}

}
