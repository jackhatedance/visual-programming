package com.bluesky.visualprogramming.remote.email;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.jivesoftware.smack.XMPPException;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;

public class EmailService extends AbstractProtocolService implements
		ProtocolService {

	private ProtocolType[] supportedTypes = new ProtocolType[] { ProtocolType.EMAIL };

	Map<String, EmailAgent> agents = new HashMap<String, EmailAgent>();

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			String connectionOptions) {

		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		EmailAgent agent = new EmailAgent(address, obj, connectionOptions);
		agents.put(address, agent);

	}

	@Override
	public void send(String receiverAddress, Message message) {

		String senderAddress = getAddress(message.sender);
		EmailAgent agent = agents.get(senderAddress);

		agent.send(receiverAddress, message);

	}

	@Override
	public ProtocolType[] getSupportedTypes() {

		return this.supportedTypes;
	}
}
