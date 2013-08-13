package com.bluesky.my4gl.core.flow.port;

import com.bluesky.my4gl.core.flow.node.Node;

public class InPort extends Port {

	public InPort(Node owner) {
		super(owner);

	}

	@Override
	public Node getNextNode() {

		if (owner.isSimpleNode())
			return owner;
		else
			return next.getNextNode();

	}
}
