package com.bluesky.my4gl.core.flow.node;

import com.bluesky.my4gl.core.flow.port.OutPort;
import com.bluesky.my4gl.core.flow.port.Port;

/**
 * start node of a flow chart
 * 
 * @author hz00260
 * 
 */
public class StartNode extends Node implements OneOutPortNode {

	private OutPort outPort;

	public StartNode() {

		super();

		initPorts();

		name = "StartNode";
	}

	private void initPorts() {

		outPort = new OutPort(this);

		outPorts.add(outPort);
	}

	public Node getNextNode() {
		return outPort.getNextNode();
	}

	public OutPort getOutPort() {
		return outPort;
	}

	public void setOutPort(OutPort outPort) {
		this.outPort = outPort;
	}

	@Override
	public String toString() {

		return "StartNode";
	}

	@Override
	public void connect(OneInPortNode target) {
		outPort.connect(target.getInPort());

	}

	@Override
	public void connect(Port target) {
		outPort.connect(target);

	}
}
