package com.bluesky.visualprogramming.messageEngine;

import com.bluesky.visualprogramming.core._Object;

/**
 * collector of all supported protocols. has a table mapping all local objects
 * and their protocol & ID combination. send message to local object by pointer
 * is prefer. if no local object available, then send via specific protocol.
 * 
 * 
 * @author jack
 * 
 */
public class Communicator {

	public void register(String protocol, String id, _Object obj) {

	}
}
