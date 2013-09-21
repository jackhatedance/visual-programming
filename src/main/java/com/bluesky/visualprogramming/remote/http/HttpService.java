package com.bluesky.visualprogramming.remote.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;


public class HttpService implements ProtocolService {
	static Logger logger = Logger.getLogger(HttpService.class);

	
	
	private ProtocolType type = ProtocolType.HTTP;

	// key is address, value is object
	BidiMap addressObjectMap = new DualHashBidiMap();

	Map<String, HttpAgent> agents = new HashMap<String, HttpAgent>();

	@Override
	public void register(String address, _Object obj, String connectionOptions) {
		if (addressObjectMap.containsKey(address))
			throw new RuntimeException("already registered:" + address);

		addressObjectMap.put(address, obj);

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
		HttpAgent agent = agents.get(senderAddress);

		try {
			agent.send(receiverAddress, message);
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
	}

	@Override
	public ProtocolType getType() {

		return type;
	}
}
