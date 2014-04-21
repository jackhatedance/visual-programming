package com.bluesky.visualprogramming.core.nativeproc.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
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
public class GetScope extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		StringValue result = getObjectFactory().createString();
		result.setValue(self.getScope().toString());

		return result;
	}

}
