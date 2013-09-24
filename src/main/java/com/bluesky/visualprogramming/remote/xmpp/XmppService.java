package com.bluesky.visualprogramming.remote.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.jivesoftware.smack.XMPPException;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;

public class XmppService extends AbstractProtocolService implements ProtocolService {

	private ProtocolType type = ProtocolType.XMPP;

	
	Map<String, XmppAgent> agents = new HashMap<String, XmppAgent>();

	@Override
	public void register(String address, _Object obj, String connectionOptions) {
		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		XmppAgent agent = new XmppAgent(address, obj, connectionOptions);
		agents.put(address, agent);
		agent.connect();
	}

	

	@Override
	public void send(String receiverAddress, Message message) {

		String senderAddress = getAddress(message.sender);
		XmppAgent agent = agents.get(senderAddress);

		try {
			agent.send(receiverAddress, message);
		} catch (XMPPException e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	public ProtocolType getType() {

		return this.type;
	}
}
