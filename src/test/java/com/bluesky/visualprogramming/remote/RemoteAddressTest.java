package com.bluesky.visualprogramming.remote;

import org.junit.Assert;
import org.junit.Test;

public class RemoteAddressTest {

	@Test
	public void testValueOf() {
		String addr = "http://_jack.ding@webserver:8080";
		RemoteAddress ra = RemoteAddress.valueOf(addr);

		Assert.assertEquals(addr, ra.toString());

		// no port
		addr = "http://jack@webserver";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals("jack", ra.userId);
		Assert.assertEquals(addr, ra.toString());
		xmpp://jackding@cisco.com
			
			addr = "xmpp://jack@cisco.com";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals("jack", ra.userId);
		Assert.assertEquals(addr, ra.toString());
		// no id
		addr = "http://@webserver";
		ra = RemoteAddress.valueOf(addr);
		Assert.assertEquals(addr, ra.toString());

		

	}

}
