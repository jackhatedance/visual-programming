package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.VirtualMachine;

public enum Prototypes {

	Map {
		@Override
		public String getPath() {

			return "root.prototype.map";
		}
	},
	List {
		@Override
		public String getPath() {

			return "root.prototype.list";
		}
	};

	abstract public String getPath();

	public _Object createInstance() {
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		_Object newObj = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);

		_Object list = repo.getObjectByPath(getPath());
		newObj.setField("_prototype", list, false);

		return newObj;
	};
}