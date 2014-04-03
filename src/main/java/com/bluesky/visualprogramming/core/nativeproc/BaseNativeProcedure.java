package com.bluesky.visualprogramming.core.nativeproc;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectFactory;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public abstract class BaseNativeProcedure implements NativeProcedure {

	public void execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx, Message msg) {

		_Object reply = execute(virtualMachine, self, ctx);

		msg.executionContext.setResult(reply);
		
	};

	protected abstract _Object execute(VirtualMachine virtualMachine,
			_Object self, ProcedureExecutionContext ctx);
	
	protected VirtualMachine getVM(){
		return VirtualMachine.getInstance();
	}
	
	protected ObjectRepository getRepo(){
		return getVM().getObjectRepository();
	}
	
	protected ObjectFactory getObjectFactory() {
		return getRepo().getFactory();
	}

}
