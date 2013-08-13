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
public class ArrayNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		NativeObject<Object[]> aoSelf = (NativeObject<Object[]>) self;

		if (methodName.equals("init")) {

			NativeObject<Integer> size = (NativeObject<Integer>) parameters[0];

			aoSelf.setNativeValue(new Object[size.getNativeValue()]);
			return null;

		} else if (methodName.equals("size")) {
			NativeObject<Integer> size = (NativeObject<Integer>) ClassLibrary
					.getClass(PrimitiveType.Integer).createObject();
			size.setNativeValue(aoSelf.getNativeValue().length);
			return size;

		} else if (methodName.equals("get")) {
			NativeObject<Integer> index = (NativeObject<Integer>) parameters[0];
			return aoSelf.getNativeValue()[index.getNativeValue()];

		} else if (methodName.equals("set")) {
			NativeObject<Integer> index = (NativeObject<Integer>) parameters[0];
			Object obj = parameters[1];

			aoSelf.getNativeValue()[index.getNativeValue()] = obj;

			return null;
		} else {
			throw new RuntimeException("unknown message");
		}
	}

}
