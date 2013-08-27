package com.bluesky.visualprogramming.messageEngine;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core._Object;

public interface Protocol {

	void register(String id, _Object obj);
	_Object query(String id);
	
	void send(String receiverId,Message message);
	
}
