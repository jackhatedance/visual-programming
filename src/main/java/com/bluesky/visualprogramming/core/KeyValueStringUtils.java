package com.bluesky.visualprogramming.core;

import java.util.HashMap;
import java.util.Map;

public class KeyValueStringUtils {

	public static Map<String, String> parse(String text) {
		// the content of value could be anything, so it is safe to cut it
		// before split
		int idx = text.indexOf("value=");

		String[] segments = text.substring(0, idx).split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (String seg : segments) {
			String[] keyvalue = seg.split("=");
			
			String value = keyvalue.length==2? keyvalue[1]:"";
			
			map.put(keyvalue[0], value);
		}
		
		String encValue = text.substring(idx + "value=".length());
		map.put("value", encValue);
		
		return map;
	}
}
