package com.bluesky.visualprogramming.remote.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.XMPPException;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.utils.Config;

public class XmppService extends AbstractProtocolService implements
		ProtocolService {

	Map<String, XmppAgent> agents = new HashMap<String, XmppAgent>();

	public XmppService() {
		supportedTypes = new ProtocolType[] { ProtocolType.XMPP };
	}

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {

		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		XmppAgent agent = new XmppAgent(this, address, obj, config);
		agents.put(address, agent);
		agent.connect();
	}

	@Override
	public void send(String receiverAddress, Message message) {

		String senderAddress = getPrimaryAddress(message.sender);
		XmppAgent agent = agents.get(senderAddress);

		try {
			agent.send(receiverAddress, message);
		} catch (XMPPException e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	public ProtocolType[] getSupportedTypes() {

		return this.supportedTypes;
	}
}
