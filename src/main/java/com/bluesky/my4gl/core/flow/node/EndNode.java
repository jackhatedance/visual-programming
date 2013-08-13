package com.bluesky.my4gl.core.flow.node;

import com.bluesky.my4gl.core.flow.port.InPort;
import com.bluesky.my4gl.core.flow.port.Port;

/**
 * end node of a flow chart. act as a Return Instruction.
 * 
 * @author hz00260
 * 
 */
public class EndNode extends Node implements OneInPortNode {
	private Port inPort;

	public EndNode() {
		super();

		initPorts();
		name = "EndNode";
	}

	private void initPorts() {

		inPort = new InPort(this);

		inPorts.add(inPort);
	}

	@Override
	public String toString() {

		return "EndNode";
	}

	@Override
	public Node getNextNode() {
		throw new RuntimeException("End Node has no output transitions.");
	}

	@Override
	public Port getInPort() {

		return inPort;
	}

}
