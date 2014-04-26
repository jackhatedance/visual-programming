package com.bluesky.visualprogramming.core.nativeproc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.bluesky.visualprogramming.core.ObjectFactory;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class NativeClass {
	static private VirtualMachine getVM() {
		return VirtualMachine.getInstance();
	}

	static private ObjectFactory getObjectFactory() {
		return getVM().getObjectRepository().getFactory();
	}

	@ParameterList({ "a", "b" })
	static public IntegerValue add(IntegerValue a, IntegerValue b) {
		IntegerValue c = getObjectFactory().createInteger(
				a.getIntValue() + b.getIntValue());

		return c;
	}

	public static void main(String[] args) throws NoSuchMethodException,
			SecurityException {

		Method m = null;
		for (Method method : NativeClass.class.getMethods()) {
			if (method.getName().equals("add")) {
				m = method;
				break;
			}
		}

		Annotation ann = m.getAnnotation(ParameterList.class);
		ParameterList nmP = (ParameterList) ann;

		for (String name : nmP.value())
			java.lang.System.out.println(name);

		
	}
}
