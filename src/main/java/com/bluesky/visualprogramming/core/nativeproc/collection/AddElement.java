package com.bluesky.visualprogramming.core.nativeproc.collection;

import java.util.UUID;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * auto create child name.
 * 
 * @author Administrator
 * 
 */
public class AddElement extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		_Object child = ctx.get("element");

		String name = UUID.randomUUID().toString().replaceAll("-", "");

		boolean canIOwn = child.hasOwner() ? false : true;
		self.setField(name, child, canIOwn);

		return null;
	}

	public static void main(String[] args) {
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(name);
	}
}
