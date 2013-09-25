package com.bluesky.visualprogramming.vm;

import org.junit.Assert;
import org.junit.Test;

public class AppPropertiesTest {

	@Test
	public void test() {
		String value = AppProperties.getInstance().getRemoteSecurityConfig()
				.getProperty("foo");

		Assert.assertEquals("bar", value);
	}

}
