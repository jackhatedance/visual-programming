package com.bluesky.visualprogramming.remote.email;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.utils.Config;

public class EmailService extends AbstractProtocolService implements
		ProtocolService {

	Map<String, EmailAgent> agents = new HashMap<String, EmailAgent>();

	public EmailService() {
		supportedTypes = new ProtocolType[] { ProtocolType.EMAIL };
	}

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {

		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		EmailAgent agent = new EmailAgent(address, obj, config);
		agents.put(address, agent);

	}

	@Override
	public void send(String receiverAddress, Message message) {

		String senderAddress = getAddress(message.sender);
		EmailAgent agent = agents.get(senderAddress);

		agent.send(receiverAddress, message);

	}


}
