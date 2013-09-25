package com.bluesky.jack.jackap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

public class ExtractActiveUsersTest {

	@Test
	public void test() throws IOException {
		StatusWirelessLive eau = new StatusWirelessLive();

		StringBuffer sb = new StringBuffer();

		BufferedReader br = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream("Status_Wireless.live.asp"), "UTF-8"));
		for (int c = br.read(); c != -1; c = br.read())
			sb.append((char) c);

		List<String> list = eau.getWirelessUserMacs(sb.toString());

		for (String mac : list)
			System.out.println(mac);
	}
}
