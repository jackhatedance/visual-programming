package com.bluesky.visualprogramming.protocol.xmpp;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.Agent;
import com.bluesky.visualprogramming.remote.ProtocolService;

public class XmppService implements ProtocolService {

	Map<String, Agent> agents = new HashMap<String, Agent>();

	@Override
	public void register(String address, _Object obj, String connectionOptions) {

	}

	@Override
	public _Object query(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String receiverId, Message message) {
		// TODO Auto-generated method stub

	}

}
