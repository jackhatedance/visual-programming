package com.bluesky.my4gl.core;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.my4gl.core.flow.FlowChart;
import com.bluesky.my4gl.core.interpreter.ExceutionContext;

/**
 * it is method signature. no parameter values stored.
 * 
 * @see ExceutionContext
 * @author jack
 * 
 */
public class Method {
	private Class clazz;
	private AccessScope accessScope;
	private StorageType storageType;

	/*
	 * if it is a native method, we delegate the execution to the native method;
	 * Otherwise it is null;
	 */
	private boolean nativeMethod;

	private String returnClassName;
	private Class returnClass;
	private String name;
	private Map<String, Class> parameters;
	private Map<String, Class> localVariables;
	/*
	 * the body of a method is a flow chart.
	 */
	private FlowChart body;

	public Method() {
		storageType = StorageType.Instance;

		nativeMethod = false;

		parameters = new HashMap<String, Class>();
		localVariables = new HashMap<String, Class>();

	}

	public void addParameter(String name, Class clazz) {
		parameters.put(name, clazz);
	}

	public void addLocalVariable(String name, Class clazz) {
		localVariables.put(name, clazz);
	}
		 
	public boolean isNativeMethod() {
		return nativeMethod;
	}

	public void setNativeMethod(boolean nativeMethod) {
		this.nativeMethod = nativeMethod;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public AccessScope getAccessScope() {
		return accessScope;
	}

	public void setAccessScope(AccessScope accessScope) {
		this.accessScope = accessScope;
	}

	 

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FlowChart getBody() {
		return body;
	}

	public void setBody(FlowChart body) {
		this.body = body;
	}

	public Class getReturnClass() {
		return returnClass;
	}

	public void setReturnClass(Class returnClass) {
		this.returnClass = returnClass;
	}

	public Map<String, Class> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Class> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Class> getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(Map<String, Class> localVariables) {
		this.localVariables = localVariables;
	}

	public String getReturnClassName() {
		return returnClassName;
	}

	public void setReturnClassName(String returnClassName) {
		this.returnClassName = returnClassName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		
		sb.append(String
				.format("%s %s %s %s(", accessScope, storageType.toJavaString(), returnClassName, name));
		int i = 0;
		for (String n : parameters.keySet()) {
			if (i > 0)
				sb.append(",");

			Class cls = parameters.get(n);
			sb.append(cls.getFullName() + " " + n);
			i++;
		}
		sb.append(") {\r\n");

		if (!isNativeMethod())
			sb.append("method body here...\r\n");
		else
			sb.append(String
					.format("<native>\r\n%s\r\n</native>", nativeMethod));

		sb.append("\r\n}");
		return sb.toString();
	}
}
