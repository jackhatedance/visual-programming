package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.vm.CompiledProcedure;
import com.bluesky.visualprogramming.vm.ProcedureExecutor;

public class Worker {

	public void feed(_Object obj) {
		Message msg = obj.getMessageQueue().poll();
		Procedure proc = obj.getProcedure(msg.subject);

		if (proc.isNative()) {
			try {
				Class cls = Class.forName(proc.nativeProcedureClassName);
				NativeProcedure nativeP = (NativeProcedure) cls.newInstance();

				nativeP.execute(obj, msg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			CompiledProcedure cp = obj.getCompiledProcedure(msg.subject);

			ProcedureExecutor executor = new ProcedureExecutor();
			// e.setPolicy(step);

			executor.execute(cp, msg.executionContext);
		}
	}
}
