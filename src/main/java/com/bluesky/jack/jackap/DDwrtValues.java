package com.bluesky.jack.jackap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVUtils;

public class DDwrtValues {

	Map<String, String[]> map = new HashMap<String, String[]>();

	public DDwrtValues(String s) {
		try {
			parse(s);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void parse(String s) throws IOException {

		BufferedReader reader = new BufferedReader(new StringReader(s));
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;

			// strip {}
			line = line.substring(1, line.length() - 1);

			int index = line.indexOf("::");
			String key = line.substring(0, index);
			String valueString = line.substring(index + 2);

			String valueArray[] = null;
			if (valueString.indexOf("','") > 0) {
				valueString = valueString.replaceAll("'", "\"");
				valueArray = CSVUtils.parseLine(valueString);
			} else {
				valueArray = new String[1];
				valueArray[0] = valueString;
			}

			map.put(key, valueArray);

		}
	}

	public Map<String, String[]> getMap() {
		return map;
	}

	public void setMap(Map<String, String[]> map) {
		this.map = map;
	}

	public static void main(String[] args) {
		String s = "{lan_mac::98:FC:11:92:74:D7}\n"
				+ "{dhcp_leases:: 'nexus4','192.168.0.33','98:D6:F7:5C:59:E3','1 day 00:00:00','33','blue2009','192.168.0.107','00:24:1D:D6:47:F9','1 day 00:00:00','107','mk802a-wlan','192.168.0.7','48:02:2A:B2:C8:B6','1 day 00:00:00','7','iPad','192.168.0.139','90:B2:1F:3A:C2:DA','1 day 00:00:00','139','e350','192.168.0.8','50:E5:49:4D:C7:AC','1 day 00:00:00','8'}\n"
				+ "{pptp_leases::}\n" + "{ipinfo::&nbsp;IP: 115.193.166.226}\n";
		DDwrtValues d = new DDwrtValues(s);
		for (String key : d.getMap().keySet()) {
			String[] valueArray = d.getMap().get(key);

			System.out.print(key + "=");

			for (String v : valueArray) {
				System.out.print(v);
				System.out.print("; ");
			}

			System.out.println();
		}
	}
}
