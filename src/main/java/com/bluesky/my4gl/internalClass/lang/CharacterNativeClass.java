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
public class CharacterNativeClass implements NativeClass {

	@Override
	public Object invoke(ExceutionContext ctx, Object self, String methodName,
			Object[] parameters) {
		NativeObject<Character> noSelf = (NativeObject<Character>) self;

		if (methodName.equals("init")) {
			NativeObject<Character> p1 = (NativeObject<Character>) parameters[0];
			noSelf.setNativeValue(p1.getNativeValue());
			return null;
		} else {
			throw new RuntimeException("unknown message");
		}
	}

}
