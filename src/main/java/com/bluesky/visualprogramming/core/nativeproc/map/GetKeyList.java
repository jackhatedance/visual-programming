package com.bluesky.visualprogramming.core.nativeproc.map;

import java.util.List;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.Prototypes;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.BaseNativeProcedure;
import com.bluesky.visualprogramming.core.nativeproc.list.ListObject;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.ProcedureExecutionContext;

public class GetKeyList extends BaseNativeProcedure implements NativeProcedure {

	@Override
	protected _Object execute(_Object self,
			ProcedureExecutionContext ctx) {

		MapObject mo = new MapObject(self);

		List<String> keyList = mo.getKeyList();

		_Object list = Prototypes.List.createInstance();
		ListObject listObject = new ListObject(list);

		for (String key : keyList) {
			StringValue keySV = (StringValue) getRepo().createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);
			
			keySV.setValue(key);
			
			listObject.add(keySV);
		}

		return list;
	}
}
