package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.VirtualMachine;

public class ExtendedObjectFactory {

	public static _Object create(Prototype type) {

		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();
		BasicObjectFactory basic = repo.getFactory();

		_Object newObj = basic.createObject();

		_Object proto = repo.getObjectByPath(type.getPath());
		newObj.setPrototype(proto);
		return newObj;

	}
}
