package com.bluesky.visualprogramming.vm;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class AppPropertiesTest {

	@Ignore
	@Test
	public void test() {
		String value = AppProperties.getInstance()
				.getProperty("foo");

		Assert.assertEquals("bar", value);
	}

}
