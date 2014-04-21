package com.bluesky.visualprogramming.core.nativeproc;

import java.util.Map;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * when native method executed in instruction executor
 * 
 * @author jack
 * 
 */
public interface NativeMethod {

	String[] getParameterNames();

	_Object execute(VirtualMachine virtualMachine,
			Map<String, _Object> parameters);
}
