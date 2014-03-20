package com.bluesky.visualprogramming.core.nativeproc.collection;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class Sort extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(VirtualMachine virtualMachine, _Object self,
			ProcedureExecutionContext ctx) {

		CollectionObject mo = new CollectionObject(self);

		
		_Object config = self.getSystemChild("collection");

		String field = null;
		SortOrder order = SortOrder.Asc;

		// get config
		if (config != null) {
			StringValue fieldObj = (StringValue) config.getChild("field");
			if (fieldObj != null)
				field = fieldObj.getValue();

			StringValue orderObj = (StringValue) config.getChild("order");
			if (orderObj != null)
				order = SortOrder.valueOf(orderObj.getValue());
		}

		mo.sort(field, order);

		return null;
	}
}
