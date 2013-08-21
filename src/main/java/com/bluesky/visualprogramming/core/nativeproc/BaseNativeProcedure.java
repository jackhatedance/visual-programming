package com.bluesky.visualprogramming.core.nativeproc;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;

public abstract class BaseNativeProcedure implements NativeProcedure {
	public _Object execute(_Object self, Message msg) {

		Map<String, _Object> params = processParameters(
				new String[] { "content" }, msg);

		return execute(self, params);
	};

	protected abstract _Object execute(_Object self, Map<String, _Object> params);

	/**
	 * order parameters to named parameters
	 */
	protected Map<String, _Object> processParameters(String[] parameterNames,
			Message msg) {
		Map<String, _Object> map = new HashMap<String, _Object>();

		if (msg.parameterStyle == ParameterStyle.ByOrder)
		{
			for (int i = 0; i < parameterNames.length; i++) {
				String name = parameterNames[i];
				map.put(name, msg.body.getChild(i));
			}
		} else
			map = msg.body.getChildrenMap();
		
		return map;
	}
}
