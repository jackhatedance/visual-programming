package com.bluesky.visualprogramming.core.nativeproc.console;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class PrintLine extends BaseNativeProcedure implements
		NativeProcedure {
	
	
	@Override
	protected _Object execute(VirtualMachine virtualMachine,_Object self, ProcedureExecutionContext ctx) {
		_Object content = (_Object) ctx.getObject("content");

		System.out.println(content.getValue());

		return null;
	}
}
