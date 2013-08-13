package com.bluesky.my4gl.core.flow.port;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.my4gl.core.flow.node.Node;
import com.bluesky.my4gl.core.flow.node.OneInPortNode;

/**
 * every port has a head and a tail. and one port's head can link to another's
 * tail.
 * 
 * @author hz00260
 * 
 */
public abstract class Port {
	public static enum Direction {
		In, Out
	};

	protected Node owner;
	protected Direction direction;

	protected List<Port> sourcePorts=new ArrayList<Port>();
	protected Port next;

	public Port(Node owner) {
		this.owner = owner;
	}

	public void connect(Port target) {
		next = target;
		target.addSourcePort(this);
	}

	public void connect(OneInPortNode target) {
		Port targetPort = target.getInPort();
		connect(targetPort);
	}

	public void disconnect(Port target) {
		target.removeSourcePort(this);
		next = null;
	}

	public void addSourcePort(Port source) {
		if (sourcePorts.contains(source))
			throw new RuntimeException("already added.");

		sourcePorts.add(source);
	}

	public void removeSourcePort(Port source) {
		if (!sourcePorts.contains(source))
			throw new RuntimeException("not exist");

		sourcePorts.remove(source);
	}

	public abstract Node getNextNode();
}
