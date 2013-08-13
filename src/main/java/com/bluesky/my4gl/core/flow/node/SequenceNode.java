package com.bluesky.my4gl.core.flow.node;

import com.bluesky.my4gl.core.flow.port.InPort;
import com.bluesky.my4gl.core.flow.port.OutPort;
import com.bluesky.my4gl.core.flow.port.Port;

/**
 * only one out transition.
 * 
 * @author hz00260
 * 
 */
public class SequenceNode extends Node implements OneInPortNode, OneOutPortNode {

	protected Port inPort;
	protected Port outPort;

	public SequenceNode() {
		super();

		initPorts();
	}

	public SequenceNode(String expression) {
		super(expression);

		initPorts();

	}

	private void initPorts() {

		inPort = new InPort(this);
		outPort = new OutPort(this);

		inPorts.add(inPort);
		outPorts.add(outPort);
	}

	@Override
	public Node getNextNode() {
		return outPort.getNextNode();
	}

	public Port getOutPort() {
		return outPort;
	}

	public void connect(OneInPortNode target) {
		getOutPort().connect(target);
	}

	@Override
	public void connect(Port target) {
		getOutPort().connect(target);
	}

	public Port getInPort() {
		return inPort;
	}

	public void setInPort(Port inPort) {
		this.inPort = inPort;
	}

	public void setOutPort(Port outPort) {
		this.outPort = outPort;
	}

}
