package com.bluesky.visualprogramming.core.link;

import com.bluesky.visualprogramming.core.Link;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;

public class SoftLink extends Link {
	public SoftLink(long id) {
		super(id);
		type=ObjectType.SOFT_LINK;
	}

	String pathName;

	@Override
	public _Object getTarget() {
		// TODO query the naming service
		return null;
	}
	@Override
	public String getValue() {
		
		return pathName;
	}
}
