package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * the Float class extends from Object class, with no field, and some methods:
 * add,sub,multiple,divide;equal,greatThan,lessThan,greatThanOrEqual,
 * lessThanOrEqual,
 * 
 * @author xp
 * 
 */
public class FloatNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		NativeObject<Float> noSelf = (NativeObject<Float>) self;

		if (methodName.equals("init")) {
			// constructor
			NativeObject<Float> i = (NativeObject<Float>) parameters[0];
			noSelf.setNativeValue(i.getNativeValue());

			return null;

		} else if (methodName.equals("add")) {
			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			noSelf.setNativeValue(noSelf.getNativeValue() + p.getNativeValue());
			return noSelf;

		} else if (methodName.equals("subtract")) {
			NativeObject<Float> result = (NativeObject<Float>) ClassLibrary
					.getClass(PrimitiveType.Float).createObject();

			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			result.setNativeValue(noSelf.getNativeValue() - p.getNativeValue());

			return result;
		} else if (methodName.equals("multiple")) {
			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			noSelf.setNativeValue(noSelf.getNativeValue() * p.getNativeValue());
			return noSelf;
		} else if (methodName.equals("divide")) {
			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			noSelf.setNativeValue(noSelf.getNativeValue() / p.getNativeValue());
			return noSelf;
		} else if (methodName.equals("valueOf")) {
			NativeObject<String> p = (NativeObject<String>) parameters[0];
			NativeObject<Float> result = (NativeObject<Float>) ClassLibrary
					.getClass(PrimitiveType.Float).createObject();
			result.setNativeValue(Float.valueOf(p.getNativeValue()));

			return result;

		} else if (methodName.equals("toString")) {

			NativeObject<String> so = (NativeObject<String>) ClassLibrary
					.getClass(PrimitiveType.String).createObject();
			so.setNativeValue(String.valueOf(noSelf.getNativeValue()));
			return so;

		} else if (methodName.equals("lt")) {
			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			NativeObject<Boolean> bo = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			bo.setNativeValue(noSelf.getNativeValue() < p.getNativeValue());
			return bo;

		} else if (methodName.equals("gt")) {
			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			NativeObject<Boolean> bo = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			bo.setNativeValue(noSelf.getNativeValue() > p.getNativeValue());
			return bo;

		} else if (methodName.equals("equals")) {
			NativeObject<Float> p = (NativeObject<Float>) parameters[0];
			NativeObject<Boolean> bo = (NativeObject<Boolean>) ClassLibrary
					.getClass(PrimitiveType.Boolean).createObject();
			bo.setNativeValue(noSelf.getNativeValue() == p.getNativeValue());
			return bo;

		} else {
			throw new RuntimeException("unknown message");
		}
	}

}
