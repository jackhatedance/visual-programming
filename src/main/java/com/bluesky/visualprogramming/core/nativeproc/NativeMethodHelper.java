package com.bluesky.visualprogramming.core.nativeproc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.dialect.goo.GooCompiler;

public class NativeMethodHelper {
	static Logger logger = Logger.getLogger(NativeMethodHelper.class);

	public static _Object executeNativeMethod(String className,
			String methodName, _Object msgBody, ParameterStyle parameterStyle)
			throws Exception {



			Method m = null;
			Class<?> clazz = Class.forName(className);

			for (Method method : clazz.getMethods()) {
				if (method.getName().equals(methodName)) {
					m = method;
					break;
				}
			}

		if (m == null) {
			throw new RuntimeException("native method not found: " + className
					+ "." + methodName);
		}

			Annotation ann = m.getAnnotation(ParameterList.class);
			ParameterList paramList = (ParameterList) ann;

			String[] paramNames = paramList.value();
			
			if(logger.isDebugEnabled()){
				logger.debug("native method:"+methodName);
				StringBuilder sb = new StringBuilder();
				
				for(String name: paramNames){
					sb.append(name);
					sb.append(",");					
				}
				logger.debug(sb.toString());
			}

			_Object[] parameters = parameterObjectToArray(msgBody,
					parameterStyle, paramNames);

		return (_Object) m.invoke(null, parameters);


	}

	private static _Object[] parameterObjectToArray(_Object parameters,
			ParameterStyle parameterStyle, String[] paramNames) {
		List<_Object> parameterList = new ArrayList<_Object>();

		if (parameters != null) {
			if (parameterStyle == ParameterStyle.ByName) {
				for (String name : paramNames) {
					_Object p = parameters.getChild(name);

					if (logger.isDebugEnabled())
						logger.debug("find parameter:" + name);

					parameterList.add(p);
				}
			} else {
				// by order
				int prefixLen = GooCompiler.PARAMETER_BY_ORDER_NAME_PREFIX
						.length();
				int paramLen = paramNames.length;
				
				for(int i=0;i<paramLen;i++)
				{
					String fieldName = String.format("%s%d",GooCompiler.PARAMETER_BY_ORDER_NAME_PREFIX,i); 
					 
					_Object p = parameters.getChild(fieldName);

					if (logger.isDebugEnabled())
						logger.debug("find parameter:" + fieldName);

					parameterList.add(p);
				}

			}
		}

		return parameterList.toArray(new _Object[0]);
	}

}
