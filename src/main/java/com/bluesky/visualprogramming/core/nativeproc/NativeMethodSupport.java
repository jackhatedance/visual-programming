package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.BasicObjectFactory;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public abstract class NativeMethodSupport {


	protected static VirtualMachine getVM() {
		return VirtualMachine.getInstance();
	}
	
	protected static ObjectRepository getRepo() {
		return getVM().getObjectRepository();
	}
	
	protected static BasicObjectFactory getObjectFactory() {
		return getRepo().getFactory();
	}

}
