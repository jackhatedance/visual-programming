package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * every object is a map.
 * 
 * @author Administrator
 * 
 */
public class GetChild extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		
		_Object obj = ctx.get("obj");
		StringValue nameSV = (StringValue) ctx.get("name");

		if(obj==null)
			throw new RuntimeException("'obj' is null");
		if(nameSV==null)
			throw new RuntimeException("'name' is null");
		
		_Object value = obj.getChild(nameSV.getValue());

		return value;
	}

}
