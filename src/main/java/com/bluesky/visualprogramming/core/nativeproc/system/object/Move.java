package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * move field from self to another object.
 * 
 * procedure (srcObj, srcFieldName, dstObj, dstFieldName)
 * 
 * @author Administrator
 * 
 */
public class Move extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {
		
		_Object srcObj = ctx.get("srcObj");
		StringValue srcFieldName = (StringValue) ctx.get("srcFieldName");
		_Object dstObj = ctx.get("dstObj");
		StringValue dstFieldName = (StringValue) ctx.get("dstFieldName");
		if (dstFieldName == null)
			dstFieldName = srcFieldName;// same name

		_Object childObj = srcObj.getChild(srcFieldName.getValue());
		srcObj.removeChild(childObj);

		dstObj.setField(dstFieldName.getValue(), childObj, true);

		return self;
	}

}