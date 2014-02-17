package com.bluesky.visualprogramming.utils;

import java.util.Map;
import java.util.Set;

public class Config {
	private Map<String, String> map;

	public Config(String str) {
		this.map = map;
	}

	public Config(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * get string value
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return map.get(key);
	}

	public String getString(String key, String defaultValue) {
		String value = map.get(key);
		if (value == null)
			return defaultValue;
		else {
			return value;
		}
	}

	public int getInteger(String key, int defaultValue) {
		String value = map.get(key);
		if (value == null)
			return defaultValue;
		else {
			return Integer.valueOf(value);
		}
	}

	public float getFloat(String key, float defaultValue) {
		String value = map.get(key);
		if (value == null)
			return defaultValue;
		else {
			return Float.valueOf(value);
		}
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = map.get(key);
		if (value == null)
			return defaultValue;
		else {
			return Boolean.valueOf(value);
		}
	}

	public boolean containsKey(String key){
		return map.containsKey(key);
	}

	public Set<String> keySet() {
		return map.keySet();
	}
}
