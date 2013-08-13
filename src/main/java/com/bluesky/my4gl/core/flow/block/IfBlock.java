package com.bluesky.my4gl.core.flow.block;

import com.bluesky.my4gl.core.flow.node.JudgementNode;

public class IfBlock extends Block {
	private String condition;
	private Block thenBlock;
	private Block elseBlock;

	public IfBlock(String condition, Block thenBlock, Block elseBlock) {
		this.condition = condition;
		this.thenBlock = thenBlock;
		this.elseBlock = elseBlock;

		generateFlowChart();
	}

	protected void generateFlowChart() {

		JudgementNode judge = new JudgementNode(condition);

		inPort.connect(judge);

		judge.connect(true, thenBlock);

		thenBlock.connect(outPort);

		if (elseBlock != null) {
			judge.connect(false, elseBlock);
			elseBlock.connect(outPort);
		} else {
			judge.connect(false, outPort);
		}
		
		addChild(judge);
		addChild(thenBlock);
		addChild(elseBlock);
		
	}
}
