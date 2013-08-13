package com.bluesky.my4gl.core.flow.port;

import com.bluesky.my4gl.core.flow.node.Node;

public class OutPort extends Port {

	public OutPort(Node owner) {
		super(owner);

	}

	@Override
	public Node getNextNode() {

		return next.getNextNode();

	}
}
