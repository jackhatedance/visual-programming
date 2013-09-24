package com.bluesky.visualprogramming.remote;

import org.junit.Assert;
import org.junit.Test;

public class RemoteAddressTest {

	@Test
	public void testValueOf() {
		String addr = "http://jack@webserver:8080";
		RemoteAddress ra = RemoteAddress.valueOf(addr);

		Assert.assertEquals(addr, ra.toString());

		// no port
		addr = "http://jack@webserver";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals(addr, ra.toString());

		// no protocol
		addr = "jack@webserver";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals(addr, ra.toString());

	}

}
