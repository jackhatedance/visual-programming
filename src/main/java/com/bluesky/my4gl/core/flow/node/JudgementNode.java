package com.bluesky.my4gl.core.flow.node;

import com.bluesky.my4gl.core.flow.port.InPort;
import com.bluesky.my4gl.core.flow.port.OutPort;
import com.bluesky.my4gl.core.flow.port.Port;
import com.bluesky.my4gl.internalClass.lang.NativeObject;

public class JudgementNode extends Node implements OneInPortNode {

	protected Port inPort;

	protected Port trueOutPort;
	protected Port falseOutPort;

	private void initPorts() {

		inPort = new InPort(this);

		trueOutPort = new OutPort(this);
		falseOutPort = new OutPort(this);

		inPorts.add(inPort);

		outPorts.add(trueOutPort);
		outPorts.add(falseOutPort);
	}

	public JudgementNode() {
		super();

		initPorts();
	}

	public JudgementNode(String expression) {
		super(expression);

		initPorts();
	}

	@Override
	public Node getNextNode() {
		NativeObject<Boolean> bo = (NativeObject<Boolean>) value;

		if (bo.getNativeValue())
			return trueOutPort.getNextNode();
		else
			return falseOutPort.getNextNode();

	}

	public void connect(boolean condition, OneInPortNode target) {
		if (condition)
			getTrueOutPort().connect(target.getInPort());
		else
			getFalseOutPort().connect(target.getInPort());
	}

	public void connect(boolean condition, Port target) {
		if (condition)
			getTrueOutPort().connect(target);
		else
			getFalseOutPort().connect(target);
	}

	public Port getInPort() {
		return inPort;
	}

	public void setInPort(Port inPort) {
		this.inPort = inPort;
	}

	public Port getTrueOutPort() {
		return trueOutPort;
	}

	public void setTrueOutPort(OutPort trueOutPort) {
		this.trueOutPort = trueOutPort;
	}

	public Port getFalseOutPort() {
		return falseOutPort;
	}

	public void setFalseOutPort(OutPort falseOutPort) {
		this.falseOutPort = falseOutPort;
	}
}
