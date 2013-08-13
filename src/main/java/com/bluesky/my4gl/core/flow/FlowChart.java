package com.bluesky.my4gl.core.flow;

import java.util.HashSet;
import java.util.Set;

import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.flow.node.Node;
import com.bluesky.my4gl.core.flow.node.StartNode;

/**
 * flow chart and lines of code are different way to express logic. actually,
 * code can be translated to flow chart. and flow chart can also be translated
 * to code (with goto keyword support).
 * 
 * @author xp
 * 
 */
public class FlowChart {
	private Set<Node> nodes;

	// cache
	private StartNode startNode;

	public FlowChart() {
		nodes = new HashSet<Node>();
	}

	public void addNode(Node node) {
		if (node instanceof StartNode)
			startNode = (StartNode) node;

		nodes.add(node);
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;

	}

	public StartNode getStartNode() {
		return startNode;
	}

	public void setStartNode(StartNode startNode) {
		this.startNode = startNode;
	}

}
