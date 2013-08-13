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
public class IntegerNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {

		if (methodName.equals("init")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			// constructor
			NativeObject<Integer> i = (NativeObject<Integer>) parameters[0];
			ioSelf.setNativeValue(i.getNativeValue());

			return null;

		} else if (methodName.equals("add")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];

			NativeObject<Integer> result = (NativeObject<Integer>) ClassLibrary
					.getClass(PrimitiveType.Integer).createObject();
			result.setNativeValue(ioSelf.getNativeValue() + p.getNativeValue());

			return result;

		} else if (methodName.equals("subtract")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> result = (NativeObject<Integer>) ClassLibrary
					.getClass(PrimitiveType.Integer).createObject();

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			result.setNativeValue(ioSelf.getNativeValue() - p.getNativeValue());

			return result;
		} else if (methodName.equals("multiple")) {
			NativeObject<Integer> result = (NativeObject<Integer>) ClassLibrary
					.getClass(PrimitiveType.Integer).createObject();

			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			result.setNativeValue(ioSelf.getNativeValue() * p.getNativeValue());
			return result;
		} else if (methodName.equals("divide")) {
			NativeObject<Integer> result = (NativeObject<Integer>) ClassLibrary
					.getClass(PrimitiveType.Integer).createObject();

			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			result.setNativeValue(ioSelf.getNativeValue() / p.getNativeValue());
			return result;
		} else if (methodName.equals("increase")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			ioSelf.setNativeValue(ioSelf.getNativeValue() + p.getNativeValue());
			return null;
		} else if (methodName.equals("valueOf")) {
			NativeObject<String> p = (NativeObject<String>) parameters[0];
			NativeObject<Integer> result = (NativeObject<Integer>) ClassLibrary
					.getClass(PrimitiveType.Integer).createObject();
			result.setNativeValue(Integer.valueOf(p.getNativeValue()));

			return result;

		} else if (methodName.equals("toString")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<String> so = (NativeObject<String>) ClassLibrary
					.getClass(PrimitiveType.String).createObject();
			so.setNativeValue(String.valueOf(ioSelf.getNativeValue()));
			return so;

		} else if (methodName.equals("lt")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			NativeObject<Boolean> bo = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			bo.setNativeValue(ioSelf.getNativeValue() < p.getNativeValue());
			return bo;

		} else if (methodName.equals("gt")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			NativeObject<Boolean> bo = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			bo.setNativeValue(ioSelf.getNativeValue() > p.getNativeValue());
			return bo;

		} else if (methodName.equals("equals")) {
			NativeObject<Integer> ioSelf = (NativeObject<Integer>) self;

			NativeObject<Integer> p = (NativeObject<Integer>) parameters[0];
			NativeObject<Boolean> bo = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			bo.setNativeValue(ioSelf.getNativeValue() == p.getNativeValue());
			return bo;

		} else {
			throw new RuntimeException("unknown message");
		}
	}

}
