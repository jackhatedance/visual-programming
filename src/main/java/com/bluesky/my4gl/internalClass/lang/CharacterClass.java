package com.bluesky.my4gl.internalClass.lang;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.ClassLibrary;

public class CharacterClass extends ClassImpl implements Class {

	public CharacterClass() {

		/**
		 * class stuff
		 */
		setDomain("com.bluesky.my4gl.lang");
		setName("Character");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		setNativeClass(new CharacterNativeClass());

		/*
		 * variables
		 */

		/*
		 * method: void init(Character s){}
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");
		method.setName("init");
		method.setNativeMethod(true);

		method.addParameter("s", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Character"));

		addMethod(method);

	}

	@Override
	public Object createObject() {

		NativeObject instance = new NativeObject();
		instance.setClazz(this);

		return instance;
	}

	public Object createObject(Character c) {

		NativeObject<Character> instance = new NativeObject<Character>();
		instance.setClazz(this);

		instance.setNativeValue(c);

		return instance;
	}

}
