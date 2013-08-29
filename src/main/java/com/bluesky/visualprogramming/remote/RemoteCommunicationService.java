package com.bluesky.visualprogramming.remote;

import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;

/**
 * all in one service
 * 
 * @author jackding
 * 
 */
public class RemoteCommunicationService {
	Map<ProtocolType, ProtocolService> services;

	public void register(ProtocolType protocol, String address, _Object obj,
			String connectionOptions) {
		services.get(protocol).register(address, obj, connectionOptions);
	}

	public _Object query(ProtocolType protocol, String address) {
		return services.get(protocol).query(address);
	}

	public void send(ProtocolType protocol, String receiverAddress,
			Message message) {
		services.get(protocol).send(receiverAddress, message);
	}
}
