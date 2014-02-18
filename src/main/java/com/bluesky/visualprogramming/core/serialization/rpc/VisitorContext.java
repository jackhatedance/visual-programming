package com.bluesky.visualprogramming.core.serialization.rpc;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Branch;

import com.bluesky.visualprogramming.core.Field;

public class VisitorContext {
	static final String FIELD = "field";
	static final String BRANCH = "branch";

	private Map<String, Object> map;

	public VisitorContext() {
		map = new HashMap<String, Object>();

	}

	public Field getField() {
		return (Field) map.get(FIELD);
	}

	public void setField(Field field) {
		map.put(FIELD, field);
	}

	public Branch getBranch() {
		return (Branch) map.get(BRANCH);
	}

	public void setBranch(Branch branch) {
		map.put(BRANCH, branch);
	}
	public Object get(String key) {
		return map.get(key);
	}

	public void set(String key, Object value) {
		map.put(key, value);
	}

	@Override
	public String toString() {

		return String.format("field:%s, branch: %s", getField().name,
				getBranch().getName());
	}
}
