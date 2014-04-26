package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.ObjectFactory;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public abstract class NativeClassSupport {


	protected static VirtualMachine getVM() {
		return VirtualMachine.getInstance();
	}
	
	protected static ObjectRepository getRepo() {
		return getVM().getObjectRepository();
	}
	
	protected static ObjectFactory getObjectFactory() {
		return getRepo().getFactory();
	}

}
