package com.bluesky.visualprogramming.remote;

import org.junit.Assert;
import org.junit.Test;

public class RemoteAddressTest {

	@Test
	public void testValueOf() {
		String addr = "http://_jack.ding@webserver:8080";
		RemoteAddress ra = RemoteAddress.valueOf(addr);

		Assert.assertEquals(addr, ra.toString());

		// complex
		addr = "http://visitor__1demq0jghg2fh4hjclzqme797_459290d7-f1e7-4524-8a44-067e875bb905@localhost";
		ra = RemoteAddress.valueOf(addr);

		// no port
		addr = "http://jack@webserver";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals("jack", ra.userId);
		Assert.assertEquals(addr, ra.toString());
		xmpp: // jackding@cisco.com

		addr = "xmpp://jack@cisco.com";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals("jack", ra.userId);
		Assert.assertEquals(addr, ra.toString());
		// no id
		addr = "http://@webserver";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals(addr, ra.toString());

		// complex
		addr = "visitor__1b87dboctk8051jq0bj45r5w0w_a2c40c26-67a9-4a3a-83fe-d9bb10360084@localhost";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals(addr, ra.toString());

	}

}
