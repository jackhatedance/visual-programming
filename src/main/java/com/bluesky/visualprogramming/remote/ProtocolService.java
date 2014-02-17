package com.bluesky.visualprogramming.remote;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;

public interface ProtocolService {
	/**
	 * means 1. create a agent for client; 2. allow for later query to tell
	 * whether a ID has local object.
	 * 
	 * 
	 * @param id
	 * @param obj
	 */
	void register(ProtocolType protocol, String address, _Object obj,
			Config config);

	/**
	 * 
	 * @param id
	 * @return local client
	 */
	_Object getLocalObject(String address);

	Config getConfig(String address);


	void send(String receiverId, Message message);
	
	ProtocolType[] getSupportedTypes();

}
