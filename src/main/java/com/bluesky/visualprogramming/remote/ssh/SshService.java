package com.bluesky.visualprogramming.remote.ssh;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;

public class SshService implements ProtocolService {
	private ProtocolType type = ProtocolType.SSH;

	// key is address, value is object
	BidiMap addressObjectMap = new DualHashBidiMap();

	Map<String, SshAgent> agents = new HashMap<String, SshAgent>();

	@Override
	public void register(String address, _Object obj, String connectionOptions) {
		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

		SshAgent agent = new SshAgent(address, obj, connectionOptions);
		agents.put(address, agent);
 
		agent.connect();		
	}

	@Override
	public _Object getLocalObject(String address) {

		return (_Object) addressObjectMap.get(address);
	}

	public String getAddress(_Object obj) {

		return (String) addressObjectMap.getKey(obj);
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

	@Override
	public ProtocolType getType() {

		return type;
	}
}
