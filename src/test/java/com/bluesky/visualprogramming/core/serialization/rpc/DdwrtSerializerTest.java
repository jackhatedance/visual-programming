package com.bluesky.visualprogramming.core.serialization.rpc;

import static org.junit.Assert.*;

import javax.sql.rowset.serial.SerialArray;

import org.junit.Before;
import org.junit.Test;

import com.bluesky.visualprogramming.core.serialization.DdwrtSerializer;

public class DdwrtSerializerTest {
	@Before
	public void setUp() {
		DdwrtSerializer serializer = new DdwrtSerializer();
	}

	String data = "{wl_mac::98:FC:11:92:74:D9}"
			+ "{wl_ssid::jackap6}"
			+ "{wl_channel::1}"
			+ "{wl_radio::Radio is On}"
			+ "{wl_xmit::71 mW}"
			+ "{wl_rate::72 Mbps}"
			+ "{wl_ack::}"
			+ "{active_wireless::'90:B2:1F:3A:C2:DA','eth1','N/A','N/A','N/A','-41','-87','46','651','B4:F0:AB:3E:1E:54','eth1','N/A','N/A','N/A','-39','-87','48','676','38:83:45:59:48:B8','eth1','N/A','N/A','N/A','-52','-87','35','515','98:D6:F7:5C:59:E3','eth1','N/A','N/A','N/A','-46','-87','41','589'}"
			+ "{active_wds::}"
			+ "{packet_info::SWRXgoodPacket=2526780;SWRXerrorPacket=19;SWTXgoodPacket=3787722;SWTXerrorPacket=328;}"
			+ "{uptime:: 23:34:49 up 8 days, 23:56, load average: 0.14, 0.05, 0.01}"
			+ "{ipinfo::&nbsp;IP: 115.193.161.250}";

	@Test
	public void testDeserialize() {
		
	}

}
