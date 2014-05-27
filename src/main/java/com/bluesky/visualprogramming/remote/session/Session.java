package com.bluesky.visualprogramming.remote.session;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
/**
 * remote session related info
 * @author jackding
 *
 */
public class Session {
	/**
	 * local stub object.
	 * 
	 */
	public _Object local;
	/**
	 * remote user address
	 */
	public Link remote;
	
	public Session(_Object local, Link remote) {
		this.local = local;
		this.remote = remote;
	}
}
