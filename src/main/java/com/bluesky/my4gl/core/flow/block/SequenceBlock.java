package com.bluesky.my4gl.core.flow.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bluesky.my4gl.core.flow.node.Node;
import com.bluesky.my4gl.core.flow.node.SequenceNode;
import com.bluesky.my4gl.core.flow.port.Port;

public class SequenceBlock extends Block {
	private List<String> statements = new ArrayList<String>();

	public SequenceBlock() {

	}

	public void setSubNodes(List<SequenceNode> nodes) {
		statements.clear();

		Port lastPort = getInPort();

		for (SequenceNode node : nodes) {
			if(node ==null)
			  System.out.println("node is null");
			 
			addChild(node);

			lastPort.connect(node);

			lastPort = node.getOutPort();
		}

		lastPort.connect(getOutPort());
	}

	public SequenceBlock(List<String> statements) {
		this.statements = statements;

		generateFlowChart();
	}

	protected void generateFlowChart() {
		Port lastPort = getInPort();

		for (String s : statements) {
			if (s.trim().length() == 0)
				continue;

			SequenceNode node = new SequenceNode(s);

			addChild(node);

			lastPort.connect(node);

			lastPort = node.getOutPort();
		}

		lastPort.connect(getOutPort());
	}
}
