package com.bluesky.visualprogramming.remote.ssh;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.AbstractProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.utils.Config;

public class SshService extends AbstractProtocolService implements
		ProtocolService {


	Map<String, SshAgent> agents = new HashMap<String, SshAgent>();

	public SshService() {
		supportedTypes = new ProtocolType[] { ProtocolType.SSH };
	}
	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {

		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		SshAgent agent = new SshAgent(address, obj, config);
		agents.put(address, agent);

		agent.connect();
	}

	@Override
	public void send(String receiverAddress, Message message) {

		String senderAddress = getAddress(message.sender);
		SshAgent agent = agents.get(senderAddress);

		try {
			agent.sendMessage(message);

		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}


}
