package com.bluesky.visualprogramming.core.nativeproc;

import java.util.Map;

import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;

public class ConsolePrint extends BaseNativeProcedure implements
		NativeProcedure {
	
	public ConsolePrint() {
		this.parameterNams = new String[]{"content"};
	}
	
	@Override
	protected _Object execute(_Object self, Map<String, _Object> params) {
		StringValue content = (StringValue) params.get("content");

		if (content == null)
			System.out.println("nothing");

		System.out.println(content.getValue());

		return null;
	}
}
