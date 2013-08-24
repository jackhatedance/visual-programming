package com.bluesky.visualprogramming.core.nativeproc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.NativeProcedure;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.ExecutionStatus;

public abstract class BaseNativeProcedure implements NativeProcedure {

	protected String[] parameterNams;
	
	protected _Object msgBody;

	public BaseNativeProcedure() {
		//this.parameterNams = new String[] { "content" };
	}

	public ExecutionStatus execute(_Object self, Message msg) {

		Map<String, _Object> params = processParameters(parameterNams, msg);

		_Object reply = execute(self, params);
		
		msg.executionContext.setResult(reply);
		
		return ExecutionStatus.COMPLETE;
	};

	protected abstract _Object execute(_Object self, Map<String, _Object> params);

	/**
	 * order parameters to named parameters
	 */
	protected Map<String, _Object> processParameters(String[] parameterNames,
			Message msg) {
		if(parameterNames.length==0)
			return null;
		
		Map<String, _Object> map = new HashMap<String, _Object>();

		if (msg.parameterStyle == ParameterStyle.ByOrder) {
			for (int i = 0; i < parameterNames.length; i++) {
				String name = parameterNames[i];
				map.put(name, msg.body.getChild(i));
			}
		} else {
			for (String name : msg.body.getChildrenNames())
				map.put(name, msg.body.getChild(name));

		}
		
		msgBody = msg.body;

		return map;
	}
	
	protected _Object getUniqueParameter(){
		return msgBody.getChild(0);
	}
}