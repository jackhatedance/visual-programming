package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;

/**
 * the Integer class extends from Object class, with no field, and some methods:
 * add,sub,multiple,divide;equal,greatThan,lessThan,greatThanOrEqual,
 * lessThanOrEqual,
 * 
 * @author xp
 * 
 */
public class MathNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		NativeObject ioSelf = (NativeObject) self;

		if (methodName.equals("add")) {
			NativeObject p = (NativeObject) parameters[0];
			ioSelf.setNativeValue((Integer) ioSelf.getNativeValue()
					+ (Integer) p.getNativeValue());
			return ioSelf;

		} else if (methodName.equals("sub")) {
			NativeObject p = (NativeObject) parameters[0];
			ioSelf.setNativeValue((Integer) ioSelf.getNativeValue()
					- (Integer) p.getNativeValue());
			return ioSelf;
		} else if (methodName.equals("multiple")) {
			NativeObject p = (NativeObject) parameters[0];
			ioSelf.setNativeValue((Integer) ioSelf.getNativeValue()
					* (Integer) p.getNativeValue());
			return ioSelf;
		} else if (methodName.equals("divide")) {
			NativeObject p = (NativeObject) parameters[0];
			ioSelf.setNativeValue((Integer) ioSelf.getNativeValue()
					/ (Integer) p.getNativeValue());
			return ioSelf;
		}

		return null;
	}

}
