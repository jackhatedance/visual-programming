package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.RemoteAddress;

public class Link extends _Object {
	private RemoteAddress remoteAddress;
	
	public Link(long id) {
		super(id);
		type = ObjectType.LINK;
		
		remoteAddress = new RemoteAddress();
	}

	

	@Override
	public String getValue() {

		if(remoteAddress==null)
			remoteAddress=new RemoteAddress();
		
		return remoteAddress.toString();
	}

	@Override
	public void setValue(String value) {

		try {
			remoteAddress = RemoteAddress.valueOf(value);
		} catch (Exception e) {
			remoteAddress = new RemoteAddress("null", "", "", -1);
		}
	}

	public RemoteAddress getRemoteAddress() {
		return remoteAddress;
	}

	
	public String getProtocol(){
		return remoteAddress.protocol.name();
	}
	
	public String getAddress(){
		return remoteAddress.getAddress();
	}
}
