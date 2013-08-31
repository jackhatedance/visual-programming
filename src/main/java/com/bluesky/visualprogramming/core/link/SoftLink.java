package com.bluesky.visualprogramming.core.link;

import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;

public class SoftLink extends _Object {
	public SoftLink(long id) {
		super(id);
		type = ObjectType.LINK;
	}

	String protocol;
	String address;

	@Override
	public String getValue() {

		return protocol + "://" + address;
	}

	@Override
	public void setValue(String value) {

		try {
			int idx1 = value.indexOf(':');
			protocol = value.substring(0, idx1);
			address = value.substring(idx1 + 3);
		} catch (Exception e) {
			protocol = null;
			address = null;
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public String getAddress() {
		return address;
	}

	public static void main(String[] args) {
		String value = "xmpp://jack@abc.com";
		int idx1 = value.indexOf(':');
		String protocol = value.substring(0, idx1);
		String address = value.substring(idx1 + 3);
		System.out.println(protocol);
		System.out.println(address);

	}
}
