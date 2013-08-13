package com.bluesky.my4gl.core.parser.java.expression.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * convert a expression a tree.
 * 
 * @author hz00260
 * 
 */
public class TreeParser {
	private Logger logger = Logger.getLogger(TreeParser.class);

	/**
	 * return root node
	 * 
	 * @param tokens
	 * @param startIndex
	 * @return
	 */
	public TreeNode createTree(TokenList tokens) {

		// create first node
		// String object = tokens.poll();
		// TreeNode currentNode = new TreeNode(object);
		TreeNode currentNode = null;
		while (!tokens.eof()) {

			// read separator and next node
			String separator = tokens.poll();
			if (separator.equals(",") || separator.equals(")")) {
				// end of tree

				// in case of 'b.get(y.get(x),z)'. if this right-parentheses is
				// the one after the 'x'.
				// if (!tokens.eof() && separator.equals(")")) {
				// String nextSeparator = tokens.peek();
				// if (nextSeparator.equals(","))
				// tokens.poll();
				// }

				break;
			} else if (separator.equals(".")) {
				// get field that accessing
				String field = tokens.poll();
				TreeNode newNode = new TreeNode(field);

				newNode.addChild(currentNode);

				currentNode = newNode;

			} else if (separator.equals("(")) {
				// find parameter
				TreeNode methodNode = currentNode;

				// if object is empty, put 'this' as default
				if (methodNode.children.isEmpty())
					methodNode.addChild(new TreeNode("this"));

				TreeNode parametersNode = new TreeNode("()");
				methodNode.addChild(parametersNode);

				// check if there is no parameter,means a ')' followed by a '('
				String separator2 = tokens.peek();
				if (separator2.equals(")"))// end of parameters
				{
					tokens.poll();
					continue;
				}

				while (!tokens.eof()) {

					TreeNode parameterNode = createTree(tokens);
					parametersNode.addChild(parameterNode);

					String separator3 = tokens.last();
					if (separator3.equals(")"))// end of parameters
					{
						break;
					}
				}

			} else {

				currentNode = new TreeNode(separator);
			}
		}

		return currentNode.getRoot();

	}

	public List<String> parse(String expression, String returnName) {

		char c = '.';
		// identifier, ( ) , .
		Pattern pattern = Pattern
				.compile("(([\'][.]{2}[\'])|([\"](([^\"]*)|(([^\"]*\\\\\")*[^\"]*))[^\\\\][\"])|([^\\(\\)\\.\\,]+)|([\\(\\)\\.\\,]))");
		Matcher matcher = pattern.matcher(expression);
		List<String> tokens = new ArrayList<String>();
		while (matcher.find()) {
			// RegExpUtils.printGroups("x", matcher);

			String token = matcher.group(1);
			// String separator = matcher.group(2);
			tokens.add(token);
			// tokens.add(separator);

			logger.debug(token);
		}

		TokenList tl = new TokenList(tokens);

		TreeNode rootNode = createTree(tl);
		rootNode.variableName = returnName;

		// tn.print(System.out);

		List<String> instructionlist = new ArrayList<String>();
		rootNode.putInstructions(instructionlist);

		if (logger.isDebugEnabled()) {
			for (String ins : instructionlist)
				logger.debug(ins);
		}

		return instructionlist;
	}

	public static void main(String[] args) {
		TreeParser parser = new TreeParser();
		String s = "a.b.c.bar.x(a,b.foo(x,y.getz()).getBar(),c);";
		s = "a.b.get(x,y.get(t).getV(),123,\"1.s(c),xxx\",'t')";
		// s="a.get(t).getb()";
		// s="a+(b*(c))-(d)".replaceAll("\\+", "\\.plus").replaceAll("-",
		// "\\.sub").replaceAll("\\*", "\\.mul").replaceAll("/", "\\.div");
		// s="a.plus(b.mul(c)).sub(d)";
		s = "Integer$.new().init(0)";
		s = "b";
		s = "this.fib(n.subtract(1)).plus(this.fib(n.subtract(2)))";
		s = "a.b(Object$.assign(1))";
		List<String> instructionlist = parser.parse(s, "x");

		for (String ins : instructionlist)
			System.out.println(ins);

	}

}
