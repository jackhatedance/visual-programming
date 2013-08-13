package com.bluesky.my4gl.core.flow.block;

import com.bluesky.my4gl.core.flow.node.JudgementNode;
import com.bluesky.my4gl.core.flow.node.SequenceNode;

public class ForBlock extends Block {
	private String initialization;
	private String condition;
	private String incrementation;
	private Block body;

	public ForBlock(String initialization, String condition,
			String incrementation, Block body) {

		this.initialization = initialization;
		this.condition = condition;
		this.incrementation = incrementation;

		this.body = body;

		generateFlowChart();
	}

	@Override
	protected void generateFlowChart() {

		SequenceNode initializationNode = new SequenceNode(initialization);
		SequenceNode incrementationNode = new SequenceNode(incrementation);
		JudgementNode judge = new JudgementNode(condition);

		inPort.connect(initializationNode);
		initializationNode.connect(judge);
		judge.connect(true, body);
		judge.connect(false, outPort);

		body.connect(incrementationNode);

		incrementationNode.connect(judge);

		initializationNode.setName("for-initialization");
		incrementationNode.setName("for-incrementation");
		judge.setName("for-judge");
		body.setName("for-body");

		addChild(initializationNode);
		addChild(incrementationNode);
		addChild(judge);
		addChild(body);

	}

	public String getInitialization() {
		return initialization;
	}

	public void setInitialization(String initialization) {
		this.initialization = initialization;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getIncrementation() {
		return incrementation;
	}

	public void setIncrementation(String incrementation) {
		this.incrementation = incrementation;
	}

	public Block getBody() {
		return body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

}
