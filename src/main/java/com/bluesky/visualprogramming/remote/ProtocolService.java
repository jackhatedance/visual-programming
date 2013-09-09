package com.bluesky.visualprogramming.remote;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;

public interface ProtocolService {
	/**
	 * means 1. create a agent for client; 2. allow for later query to tell
	 * whether a ID has local object.
	 * 
	 * 
	 * @param id
	 * @param obj
	 */
	void register(String address, _Object obj, String connectionOptions);

	/**
	 * 
	 * @param id
	 * @return local client
	 */
	_Object getLocalObject(String address);

	void send(String receiverId, Message message);
	
	ProtocolType getType();

}
