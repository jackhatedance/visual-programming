package com.bluesky.visualprogramming.remote.http;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;

public class HttpService implements ProtocolService {
	private ProtocolType type = ProtocolType.HTTP;

	// key is address, value is object
	BidiMap addressObjectMap = new DualHashBidiMap();

	// Set<HttpAgent> connectors = new HashMap<String, HttpAgent>();

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
		//TODO: send via HTTP client
	}

	@Override
	public ProtocolType getType() {

		return type;
	}
}
