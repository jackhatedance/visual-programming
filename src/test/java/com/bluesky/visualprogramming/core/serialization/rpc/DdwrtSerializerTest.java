package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeImpl.proto.ListProto;
import com.bluesky.visualprogramming.core.serialization.DdwrtSerializer;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.ui.Main;

public class DdwrtSerializerTest {
	DdwrtSerializer serializer;
	@Before
	public void setUp() {
		ObjectRepository objectRepository = new ObjectRepository(); 
		
		String runtimeXml = "images/"+Main.DEFAULT_RUNTIME_IMAGE_FILE_NAME;
		String userXml = "images/"+Main.DEFAULT_USER_IMAGE_FILE_NAME;
		
		objectRepository.load(runtimeXml,
				userXml);
		
		serializer = new DdwrtSerializer();
		serializer.setRepo(objectRepository);
	}

	String data = "{wl_mac::98:FC:11:92:74:D9}\n"
			+ "{wl_ssid::jackap6}\n"
			+ "{wl_channel::1}\n"
			+ "{wl_radio::Radio is On}\n"
			+ "{wl_xmit::71 mW}\n"
			+ "{wl_rate::72 Mbps}\n"
			+ "{wl_ack::}\n"
			+ "{active_wireless::'90:B2:1F:3A:C2:DA','eth1','N/A','N/A','N/A','-41','-87','46','651','B4:F0:AB:3E:1E:54','eth1','N/A','N/A','N/A','-39','-87','48','676','38:83:45:59:48:B8','eth1','N/A','N/A','N/A','-52','-87','35','515','98:D6:F7:5C:59:E3','eth1','N/A','N/A','N/A','-46','-87','41','589'}\n"
			+ "{active_wds::}\n"
			+ "{packet_info::SWRXgoodPacket=2526780;SWRXerrorPacket=19;SWTXgoodPacket=3787722;SWTXerrorPacket=328;}\n"
			+ "{uptime:: 23:34:49 up 8 days, 23:56, load average: 0.14, 0.05, 0.01}\n"
			+ "{ipinfo::&nbsp;IP: 115.193.161.250}";

	@Test
	public void testDeserialize() {
		Reader reader = new StringReader(data);
		_Object obj =serializer.deserialize(reader, null);
		_Object ipinfo = obj.getChild("ipinfo");
		
		IntegerValue idx=new IntegerValue(0);
		idx.setIntValue(0);
		_Object item0 = ListProto.get(ipinfo, idx);
		
		org.junit.Assert.assertEquals("&nbsp;IP: 115.193.161.250", item0.getValue());
		
	}

}
