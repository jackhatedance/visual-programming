package com.bluesky.my4gl.core.parser.java.expression.tree;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private static int count = 0;

	public static String getTemporaryVariableName() {
		String result = String.format("t%d", count);
		count++;
		return result;
	}

	TreeNode parent;
	List<TreeNode> children = new ArrayList<TreeNode>();

	String expression;

	String variableName;

	public TreeNode(String expression) {
		this.expression = expression;
	}

	public void addChild(TreeNode child) {
		children.add(child);
		child.parent = this;
	}

	public boolean isParametersNode() {
		return expression.equals("()");
	}

	/**
	 * get temporary variable name if
	 * 
	 * @return
	 */
	public String getVariable() {

		if (getNodeType() == NodeType.SingleValue)
			return expression;

		if (variableName == null) {
			variableName = "_T_" + getTemporaryVariableName();
		}
		return variableName;

	}

	public NodeType getNodeType() {
		if (children.isEmpty()) {
			if (expression.equals("()"))
				return NodeType.Parameters;
			else
				return NodeType.SingleValue;
		} else if (children.size() == 2 && children.get(1).isParametersNode())
			return NodeType.Invocation;
		else if (children.size() == 1)
			return NodeType.FieldAccess;
		else
			throw new RuntimeException("unknow type");
	}

	public String getInstruction() {
		if (getNodeType() == NodeType.SingleValue)
			throw new RuntimeException(
					"it is a single value node, no instruction at all.");

		if (getNodeType() == NodeType.FieldAccess) {
			TreeNode tn = children.get(0);
			String s = getVariable() + "=" + tn.getVariable() + "."
					+ expression;
			return s;
		} else if (getNodeType() == NodeType.Invocation) {
			StringBuilder sb = new StringBuilder();
			// parameters
			List<TreeNode> paramList = children.get(1).children;
			for (int i = 0; i < paramList.size(); i++) {
				TreeNode pNode = paramList.get(i);
				if (i > 0)
					sb.append(",");

				sb.append(pNode.getVariable());
			}
			String returnName = getVariable();
			String objectName = children.get(0).getVariable();
			String methodName = expression;
			String params = sb.toString();

			return String.format("%s=%s.%s(%s)", returnName, objectName,
					methodName, params);

		} else {
			throw new RuntimeException("unknown node:" + expression);
		}
	}

	public void putInstructions(List<String> instructions) {

		if (getNodeType() == NodeType.SingleValue
				|| getNodeType() == NodeType.Parameters)
			return;

		// let object first
		if (getNodeType() == NodeType.FieldAccess
				|| getNodeType() == NodeType.Invocation)// field access or
		// method invocation
		{
			TreeNode tn = children.get(0);
			tn.putInstructions(instructions);
		}
		// let parameters first
		if (getNodeType() == NodeType.Invocation) {
			for (TreeNode c : children.get(1).children) {
				c.putInstructions(instructions);
			}
		}
		// last, self
		instructions.add(getInstruction());
	}

	public TreeNode getParent() {
		return parent;
	}

	public TreeNode getRoot() {
		if (parent == null)
			return this;
		else
			return parent.getRoot();
	}

	public int getDepth() {
		if (parent == null)
			return 0;
		else
			return parent.getDepth() + 1;
	}

	public void print(PrintStream ps) {
		for (int i = 0; i < getDepth(); i++)
			ps.print("   ");
		ps.println(expression);

		for (TreeNode tn : children) {
			tn.print(ps);
		}
	}

	@Override
	public String toString() {

		return expression;
	}
}
