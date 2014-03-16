package com.bluesky.visualprogramming.core.value;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.ObjectVisitor;
import com.bluesky.visualprogramming.remote.RemoteAddress;

public class Link extends ValueObject {
	private RemoteAddress remoteAddress;

	public Link(long id) {
		super(id);
		type = ObjectType.LINK;

		remoteAddress = new RemoteAddress();
	}

	@Override
	public String getValue() {

		if (remoteAddress == null)
			remoteAddress = new RemoteAddress();

		return remoteAddress.toString();
	}

	@Override
	public void setValue(String value) {

		try {
			remoteAddress = RemoteAddress.valueOf(value);
		} catch (Exception e) {

			remoteAddress = null;

			// allow empty link object when initializing
			// throw new RuntimeException("invalid address:" + value);
		}
	}

	public RemoteAddress getRemoteAddress() {
		return remoteAddress;
	}

	public String getProtocol() {
		if (remoteAddress != null)
			return remoteAddress.protocol;

		return null;
	}

	/**
	 * short address
	 * 
	 * @return
	 */
	public String getAddress() {
		if (remoteAddress != null)
			return remoteAddress.getAddress();

		return null;
	}

	@Override
	public String toString() {

		return getValue();
	}
	
	@Override
	public void accept(ObjectVisitor visitor) {
		visitor.enter(this);

		visitor.leave(this);

	}


}
