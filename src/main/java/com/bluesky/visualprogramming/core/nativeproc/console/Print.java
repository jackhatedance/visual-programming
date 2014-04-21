package com.bluesky.visualprogramming.core.nativeproc.console;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class Print extends BaseNativeProcedure implements
		NativeProcedure {
	
	
	@Override
	protected _Object execute(_Object self, ProcedureExecutionContext ctx) {
		_Object content = (_Object) ctx.getObject("content");
		
		
		if (content != null)
			System.out.print(content.getValue());
		else
			System.out.print("null");
		
		return null;
	}
}
