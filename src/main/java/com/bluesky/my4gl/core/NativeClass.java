package com.bluesky.my4gl.core;

import com.bluesky.my4gl.core.interpreter.ExceutionContext;

public interface NativeClass {
	/**
	 * 
	 * @param ctx TODO
	 * @param self
	 * @param methodName
	 * @param parameters
	 *            we intend to use java.lang.Object instead of my4gl.Object
	 *            because primitive types support.
	 * @return
	 */
	Object invoke(ExceutionContext ctx, Object self, String methodName, Object[] parameters);
}
