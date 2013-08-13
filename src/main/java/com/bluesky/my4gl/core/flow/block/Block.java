package com.bluesky.my4gl.core.flow.block;

import com.bluesky.my4gl.core.flow.FlowChart;
import com.bluesky.my4gl.core.flow.node.Node;
import com.bluesky.my4gl.core.flow.node.SequenceNode;

/**
 * What is a block? in source code, a block is something wrapped by "{...}"<br>
 * it is for code parsing. block is a group of nodes/blocks. block has one out
 * transition, so extends from SequenceNode.
 * 
 * @author jack
 * 
 */
public abstract class Block extends SequenceNode {

	protected abstract void generateFlowChart();
}
