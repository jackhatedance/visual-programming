package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * the Integer class extends from Object class, with no field, and some methods:
 * add,sub,multiple,divide;equal,greatThan,lessThan,greatThanOrEqual,
 * lessThanOrEqual,
 * 
 * @author xp
 * 
 */
public class BooleanNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		NativeObject<Boolean> boSelf = (NativeObject<Boolean>) self;

		if (methodName.equals("init")) {
			// constructor
			NativeObject<Boolean> b = (NativeObject<Boolean>) parameters[0];
			boSelf.setNativeValue(b.getNativeValue());

			return null;
		} else if (methodName.equals("and")) {
			NativeObject<Boolean> result = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean);

			NativeObject<Boolean> p = (NativeObject<Boolean>) parameters[0];

			result
					.setNativeValue(boSelf.getNativeValue()
							&& p.getNativeValue());

			return result;

		} else if (methodName.equals("or")) {
			NativeObject<Boolean> result = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean);

			NativeObject<Boolean> p = (NativeObject<Boolean>) parameters[0];

			result
					.setNativeValue(boSelf.getNativeValue()
							|| p.getNativeValue());

			return result;
		} else if (methodName.equals("not")) {
			NativeObject<Boolean> result = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean);

			NativeObject<Boolean> p = (NativeObject<Boolean>) parameters[0];

			result.setNativeValue(!boSelf.getNativeValue());

			return result;
		} else {
			throw new RuntimeException("unknown message");
		}
	}

}
