package com.bluesky.visualprogramming.core.link;

import com.bluesky.visualprogramming.core.Link;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;

public class HardLink extends Link {
	public HardLink(long id) {
		super(id);
		type=ObjectType.HARD_LINK;
		 
	}

	int objId;
	
	@Override
	public _Object getTarget() {
		// TODO query the object table
		return null;
	}

	@Override
	public String getValue() {
		
		return String.valueOf( objId);
	}
}
