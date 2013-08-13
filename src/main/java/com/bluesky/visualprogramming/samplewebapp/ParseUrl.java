package com.bluesky.visualprogramming.samplewebapp;

import java.util.HashMap;
import java.util.Map;


public class ParseUrl implements Processor {

	public ParseUrl() {

	}

	@Override
	public void process(Map context) {
		String url = (String) context.get("url");

		int idx = url.indexOf('?');
		String action = url.substring(0, idx);
		String params = url.substring(idx + 1);
		String[] ps = params.split("&");

		Map<String, String> map = new HashMap<String, String>();

		for (String kv : ps) {
			String[] ss = kv.split("=");
			map.put(ss[0], ss[1]);
		}
		context.put("action", action);
		context.put("paramMap", map);
	}

}
