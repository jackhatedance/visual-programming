package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * create a new list object.
 * 
 * @author Administrator
 * 
 */
public class NewLink extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {
		
		StringValue address = (StringValue) ctx.get("address");
		Link link = getObjectFactory().createLink(address.getValue());
		if (link.isValid())
			return link;
		else
			return null;
	}

}
