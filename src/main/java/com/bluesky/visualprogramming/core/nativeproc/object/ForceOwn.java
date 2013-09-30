package com.bluesky.visualprogramming.core.nativeproc.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * detach from owner
 * 
 * @author Administrator
 * 
 */
public class ForceOwn extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		
		StringValue name = (StringValue) ctx.get("name");
		_Object child = (_Object) ctx.get("child");
		
		if (child.hasOwner())
			child.getOwner().detachChild(child);
		
		self.setField(name.getValue(), child, true);

		return self;
	}

}
