package com.bluesky.visualprogramming.remote;

import java.util.Map;

import org.junit.Test;

public class ConnectionOptionUtilsTest {

	@Test
	public void testParse() {
		String s = "a=1;b=2\nc=3\r\nd=4;e=5";
		Map<String, String> map = ConnectionOptionUtils.parse(s);
		for (String key : map.keySet()) {
			String value = map.get(key);
			System.out.println(String.format("'%s' = '%s'", key, value));
		}

		org.junit.Assert.assertEquals(5, map.size());
	}

}
