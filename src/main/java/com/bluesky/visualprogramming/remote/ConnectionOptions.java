package com.bluesky.visualprogramming.remote;

import java.util.HashMap;
import java.util.Map;

public class ConnectionOptions {
	public Map<String, String> map;

	public ConnectionOptions(String options) {
		map = new HashMap<String, String>();

		String[] kvs = options.split(";");
		for (String kv : kvs) {
			if (kv.trim().isEmpty())
				continue;

			String[] ss = kv.split("=");

			if (ss.length < 2)
				throw new RuntimeException("failed to parse key value pair:"
						+ kv);

			map.put(ss[0], ss[1]);
		}

	}
}
