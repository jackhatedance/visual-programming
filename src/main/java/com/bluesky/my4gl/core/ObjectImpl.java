package com.bluesky.my4gl.core;

import java.util.HashMap;

public class ObjectImpl implements Object {
	private Class clazz;

	private HashMap<String, Object> fieldMap;

	public ObjectImpl() {
		fieldMap = new HashMap<String, Object>();
	}

	public Object getFieldValue(String name) {
		if (!fieldMap.containsKey(name))
			throw new RuntimeException("field do not exist:" + name + " in "
					+ clazz.getFullName() + " class.");

		return fieldMap.get(name);
	}

	public void setFieldValue(String name, Object obj) {
		fieldMap.put(name, obj);
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;

		initFields(clazz);
	}

	public HashMap<String, Object> getVariableMap() {
		return fieldMap;
	}

	public void setVariableMap(HashMap<String, Object> variableMap) {
		this.fieldMap = variableMap;
	}

	@Override
	public boolean containsField(String fieldName) {
		return fieldMap.containsKey(fieldName);
	}

	private void initFields(Class cls) {
		fieldMap.clear();

		if (cls != null) {
			for (Field f : cls.getFields()) {

				fieldMap.put(f.name, null);
			}
		}
	}

}
