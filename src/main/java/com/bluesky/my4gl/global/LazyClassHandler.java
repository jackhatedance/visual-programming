package com.bluesky.my4gl.global;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;


/**
 * lazy initialized class delegate
 * 
 * @author jack
 * 
 */
public class LazyClassHandler implements InvocationHandler {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private ClassLibrary classLibrary;
	private String fullClassName;
	private Object delegate;

	public LazyClassHandler(ClassLibrary classLibrary, String fullClassName) {
		this.classLibrary = classLibrary;
		this.fullClassName = fullClassName;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Object o = null;

		// lazy initialization
		if (delegate == null)
			delegate = classLibrary.getRealClass(fullClassName);

		if (delegate == null)
			throw new RuntimeException("class not found:" + fullClassName);

		try {
			logger.debug("method starts..." + method);
			o = method.invoke(delegate, args);
			logger.debug("method ends..." + method);
		} catch (Exception e) {
			logger.debug("Exception happends...+"+e.getMessage());
			throw e;
		}
		return o;
	}
}
