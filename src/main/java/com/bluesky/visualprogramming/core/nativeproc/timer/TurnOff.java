package com.bluesky.visualprogramming.core.nativeproc.timer;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class TurnOff extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		BooleanValue enabledObj = (BooleanValue) self.getChild("enabled");
		enabledObj.setBooleanValue(false);

		virtualMachine.getTimerService().unsubscribe(self);

		return null;
	}
}
