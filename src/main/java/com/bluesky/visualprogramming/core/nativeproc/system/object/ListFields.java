package com.bluesky.visualprogramming.core.nativeproc.system.object;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.Prototypes;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeImpl.prototype.ListObject;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

/**
 * only list user fields
 * 
 * @author jack
 * 
 */
public class ListFields extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		_Object obj = ctx.get("obj");
		String[] fieldNames = obj.getFieldNames();

		_Object list = Prototypes.List.createInstance();

		for (String field : fieldNames) {
			StringValue fieldNameSV = (StringValue) getRepo().createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);

			fieldNameSV.setValue(field);

			ListObject.add(list, fieldNameSV);
		}

		return list;
	}
}
