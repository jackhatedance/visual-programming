package com.bluesky.visualprogramming.core.serialization.rpc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CountMap {
	private Map<String, Integer> map = new HashMap<String, Integer>();

	public void inc(String key) {
		Integer count = map.get(key);
		if (count == null)
			map.put(key, 1);
		else
			map.put(key, count + 1);
	}

	public int get(String key) {
		Integer count = map.get(key);
		if (count == null)
			return 0;
		else
			return count;

	}

	public Set<String> keySet() {
		return map.keySet();
	}
}
