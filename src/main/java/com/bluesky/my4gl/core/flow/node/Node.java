package com.bluesky.my4gl.core.flow.node;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.my4gl.core.flow.port.Port;
import com.bluesky.my4gl.core.instruction.Instruction;
import com.bluesky.my4gl.core.instruction.InstructionType;
import com.bluesky.my4gl.core.instruction.MethodInvocationInstruction;

/**
 * Node is used for Flow Control. either Sequence, Judge, Fork, Merge,
 * Loop,..etc.<br>
 * every node should initialize its transitions. dynamic-transition-node is a
 * exception.
 * 
 * @author hz00260
 * 
 */
public abstract class Node {
	public static enum State {
		Entering, Executing, Leaving;
	}

	protected State state;

	/**
	 * optional,
	 */
	protected String name = "unnamed";
	/**
	 * code expression, such as a=b+2;
	 */
	protected String expression;

	/**
	 * node value, if it is a judgment node, then it need a value.
	 */
	protected com.bluesky.my4gl.core.Object value;

	protected List<Port> inPorts;
	protected List<Port> outPorts;

	protected Instruction instruction;

	protected Node parent;
	protected List<Node> children = new ArrayList<Node>();

	public Node() {
		inPorts = new ArrayList<Port>();
		outPorts = new ArrayList<Port>();

	}

	public Node(String expression) {
		this.expression = expression;
		setExpression(expression);

		inPorts = new ArrayList<Port>();
		outPorts = new ArrayList<Port>();
	}

	@Override
	public String toString() {

		return String.format("%s|%s, in(%d),out(%d)", name, expression, inPorts
				.size(), outPorts.size());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;

		instruction = InstructionType.getType(expression).createInstruction(
				expression);

	}

	public void addChild(Node node) {
		children.add(node);
		node.parent = this;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public com.bluesky.my4gl.core.Object getValue() {
		return value;
	}

	public void setValue(com.bluesky.my4gl.core.Object value) {
		this.value = value;
	}

	public List<Port> getInPorts() {
		return inPorts;
	}

	public void setInPorts(List<Port> inPorts) {
		this.inPorts = inPorts;
	}

	public List<Port> getOutPorts() {
		return outPorts;
	}

	public void setOutPorts(List<Port> outPorts) {
		this.outPorts = outPorts;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public boolean isSimpleNode() {
		return children.isEmpty();
	}

	public abstract Node getNextNode();
}
