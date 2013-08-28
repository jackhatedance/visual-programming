package com.bluesky.my4gl.core.parser.java;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Field;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.StorageType;
import com.bluesky.my4gl.core.flow.FlowChart;
import com.bluesky.my4gl.core.flow.block.Block;
import com.bluesky.my4gl.core.flow.block.BlockType;
import com.bluesky.my4gl.core.flow.block.ForBlock;
import com.bluesky.my4gl.core.flow.block.IfBlock;
import com.bluesky.my4gl.core.flow.block.SequenceBlock;
import com.bluesky.my4gl.core.flow.block.WhileBlock;
import com.bluesky.my4gl.core.flow.node.EndNode;
import com.bluesky.my4gl.core.flow.node.SequenceNode;
import com.bluesky.my4gl.core.flow.node.StartNode;
import com.bluesky.my4gl.core.interpreter.Interpreter;
import com.bluesky.my4gl.core.parser.java.expression.Expression;
import com.bluesky.my4gl.core.parser.java.expression.ExpressionType;
import com.bluesky.my4gl.core.parser.java.expression.InvocationExpression;
import com.bluesky.my4gl.core.parser.java.expression.assignmentExpression.DeclarationAssignmentExpression;
import com.bluesky.my4gl.core.parser.java.expression.assignmentExpression.NormalAssignmentExpression;
import com.bluesky.my4gl.core.parser.java.expression.tree.TreeParser;
import com.bluesky.my4gl.global.ClassLibrary;
import com.bluesky.my4gl.global.GlobalSettings;
import com.bluesky.my4gl.internalClass.lang.NativeObject;
import com.bluesky.my4gl.internalClass.lang.PrimitiveType;

/**
 * a java source code parser, that translate code to my4gl flow chart and
 * classes.
 * 
 * @author hz00260
 * 
 */
public class Parser {
	private Logger logger = Logger.getLogger(Parser.class);

	public Parser() {

	}

	public Class parseClass(List<String> codeLines) {

		// find imports until class appear
		// find class
		// //find fields until methods appear
		// //find methods until class end.

		Class result = new ClassImpl();

		int lineNumber = 0;
		String line;
		// find package, first not empty line.
		do {
			line = codeLines.get(lineNumber).trim();

			lineNumber++;
		} while (line.length() == 0);

		if (ClassTokenType.getType(line) != ClassTokenType.Package)
			throw new RuntimeException("it is not a package:" + line);

		// parse the package name
		result.setDomain(getPackageName(line));

		if (logger.isDebugEnabled())
			logger.debug("find package:" + result.getDomain());

		// find imports
		while (true) {
			line = codeLines.get(lineNumber).trim();

			if (line.length() > 0) {
				if (ClassTokenType.getType(line) == ClassTokenType.Import) {

					String[] imports = getImport(line);
					String className = imports[0];
					String fullClassName = imports[1];
					result.getImports().put(className, fullClassName);

					if (logger.isDebugEnabled())
						logger.debug("find import:" + className + ",["
								+ fullClassName + "]");
				} else {
					break;

				}

			}
			lineNumber++;
		}

		// find class
		do {
			line = codeLines.get(lineNumber).trim();

			lineNumber++;
		} while (line.length() == 0);
		// backward 1 step, for the last self increasing.
		lineNumber--;

		if (ClassTokenType.getType(line) != ClassTokenType.Class)
			throw new RuntimeException("it is not a class:" + line);

		if (logger.isDebugEnabled())
			logger.debug("find class:" + line);
		// find the end of the class
		int blockEndLineNumber = endOfBlock(codeLines, lineNumber, true);
		parseClassBody(codeLines.subList(lineNumber, blockEndLineNumber + 1),
				result);

		return result;
	}

	private String[] getImport(String s) {

		Pattern pattern = Pattern.compile(ClassTokenType.Import.getPattern());
		Matcher matcher = pattern.matcher(s);
		if (matcher.matches()) {
			// for (int i = 1; i < matcher.groupCount() + 1; i++)
			// logger.info(i + ":[" + matcher.group(i) + "]");

			String className = matcher.group(4);
			String fullClassName = matcher.group(1);
			return new String[] { className, fullClassName };
		} else
			throw new RuntimeException("it is not a import statement:" + s);

	}

	private String getPackageName(String s) {
		return s.substring("package".length() + 1, s.length() - 1);
	}

	/**
	 * class without package and import.it is the class body to be parsed.
	 * 
	 * @param codelines
	 * @param clazz
	 */
	public void parseClassBody(List<String> codeLines, Class clazz) {

		// parse class head
		String classHead = codeLines.get(0);
		Pattern pattern = Pattern.compile(ClassTokenType.Class.getPattern());
		Matcher matcher = pattern.matcher(classHead);
		if (matcher.matches()) {
			int count = matcher.groupCount();
			// for (int i = 1; i < count + 1; i++)
			// logger.info(i + ":[" + matcher.group(i) + "]");

			if (count == 5) {
				String accessScope = matcher.group(2);
				String className = matcher.group(3);
				String superClassName = matcher.group(5);

				clazz.setName(className);
				clazz.setSuperClass(ClassLibrary
						.getClass(ClassLibrary.CLASS_NAME_Object));

				if (logger.isDebugEnabled())
					logger.debug("class:" + className);

				if (logger.isDebugEnabled())
					logger.debug("super class:" + superClassName);
			} else
				throw new RuntimeException("unknown class declaration.");
		}

		String line;
		int lineNumber = 1;
		// find field
		while (true) {
			line = codeLines.get(lineNumber).trim();

			if (line.length() > 0) {

				pattern = Pattern.compile(ClassTokenType.Field.getPattern());
				matcher = pattern.matcher(line);
				if (matcher.matches()) {
					// for (int i = 1; i < matcher.groupCount() + 1; i++)
					// logger.info(i + ":" + matcher.group(i) + ";");

					String accessScope = matcher.group(2);
					String storageType = matcher.group(4);
					String varTypeName = matcher.group(5);
					String varName = matcher.group(6);

					String fullClassName = clazz.getFullClassName(varTypeName);

					Field field = new Field(AccessScope.getScope(accessScope),
							StorageType.getType(storageType), fullClassName,
							varName);
					clazz.getFields().add(field);

					if (logger.isDebugEnabled())
						logger.debug("find field:" + field);

				} else
					break;

			}
			lineNumber++;
		}

		// find methods, the last line('}') is excluded.
		while (lineNumber < codeLines.size() - 1) {
			line = codeLines.get(lineNumber).trim();
			if (line.length() > 0) {

				int endIndex = endOfBlock(codeLines, lineNumber, false);

				Method method = parseMethod(
						codeLines.subList(lineNumber, endIndex + 1), clazz);

				if (logger.isDebugEnabled())
					logger.debug("got method:" + method.getName());

				clazz.addMethod(method);

				lineNumber = endIndex + 1;
				continue;

			}
			lineNumber++;
		}

	}

	private Method parseMethod(List<String> codeLines, Class clazz) {

		Method method = new Method();

		String line = codeLines.get(0);

		// parse the method head
		Pattern pattern = Pattern.compile(ClassTokenType.Method.getPattern());
		Matcher matcher = pattern.matcher(line);

		String params;
		if (matcher.matches()) {
			// for (int i = 1; i < matcher.groupCount() + 1; i++)
			// logger.info(i + ":[" + matcher.group(i) + "]");

			String accessScope = matcher.group(2);
			String storageType = matcher.group(4);
			String className = matcher.group(6);

			method.setAccessScope(AccessScope.getScope(accessScope));
			method.setStorageType(StorageType.getType(storageType));

			method.setReturnClassName(className);
			method.setName(matcher.group(7));

			params = matcher.group(8);
			// parse parameters.
			String[] paramArray = params.split(",");
			for (String p : paramArray) {
				if (p.trim().length() > 0) {

					String[] ss = p.split(" ");
					String fullClassName = clazz.getFullClassName(ss[0]);
					method.addParameter(ss[1],
							ClassLibrary.getClass(fullClassName));
				}
			}

			if (logger.isDebugEnabled())
				logger.debug("find method:" + method);
		} else
			throw new RuntimeException("it is not a method:" + line);

		// find nodes and blocks

		// first create a sequential block, the last line("}") is excluded
		List<String> methodBody = codeLines.subList(1, codeLines.size() - 1);

		// TODO here the method body could be empty.

		SequenceBlock seqBlock = parseCodeLines(methodBody);

		// FlowChart
		FlowChart fc = makeFlowChart(seqBlock);
		method.setBody(fc);

		return method;
	}

	/**
	 * parse the block body and add children nodes into the parent node.
	 * 
	 * @param block
	 * @param codeLines
	 */
	private Block parseBlock(List<String> codeLines) {

		// decide what type of block it is
		String blockHead = codeLines.get(0);
		BlockType blockType = BlockType.getType(blockHead);

		String condition = BlockType.getCondition(blockHead);

		if (blockType == BlockType.While) {

			// get body
			List<String> bodyCodeLines = codeLines.subList(1,
					codeLines.size() - 1);
			SequenceBlock bodyBlock = parseCodeLines(bodyCodeLines);
			WhileBlock block = new WhileBlock(condition, bodyBlock);
			return block;
		} else if (blockType == BlockType.For) {

			// the for condition should be break down to 3 parts
			String ss[] = condition.split(";");
			List<String> bodyCodeLines = codeLines.subList(1,
					codeLines.size() - 1);
			SequenceBlock bodyBlock = parseCodeLines(bodyCodeLines);
			String init = ss[0];
			String condition_ = ss[1];
			String increment = ss[2];
			ForBlock block = new ForBlock(init, condition_, increment,
					bodyBlock);
			return block;

		} else if (blockType == BlockType.If) {
			// does 'else' exist?
			int endIndexOfIf = endOfBlock(codeLines, 0, false);
			String endLine = codeLines.get(endIndexOfIf);

			// head and end lines are excluded
			List<String> ifBlockCodeLines = codeLines.subList(1, endIndexOfIf);
			List<String> endBlockCodeLines = null;
			// if the end line is like "} else {", there is a ELSE block
			// following
			if (endLine
					.matches("[\\t\\s]*\\}[\\t\\s]*else[\\t\\s]*\\{[\\t\\s]*")) {

				int endIndexOfElseBlock = endOfBlock(codeLines,
						endIndexOfIf + 1, false);
				endBlockCodeLines = codeLines.subList(endIndexOfIf + 1,
						endIndexOfElseBlock);
			}

			SequenceBlock ifBlock = parseCodeLines(ifBlockCodeLines);
			SequenceBlock elseBlock = parseCodeLines(endBlockCodeLines);
			Block block = new IfBlock(condition, ifBlock, elseBlock);
			return block;
		} else
			throw new RuntimeException("unknown block:" + blockHead);

	}

	/**
	 * it can be a method invocation/assignment/return instrument.
	 * 
	 * @param statement
	 * @return
	 */
	private SequenceNode parseOneLineCode(String line) {
		// remove last ';'
		if (line.matches("^.*;[\\t\\s]*$")) {
			int index = line.lastIndexOf(';');
			line = line.substring(0, index);
		}

		// [Foo] foo = exp
		ExpressionType et = ExpressionType.getType(line);
		Expression exp = et.parse(line);

		if (et == ExpressionType.DeclarationAssignment) {
			DeclarationAssignmentExpression dae = (DeclarationAssignmentExpression) exp;

			TreeParser treeParser = new TreeParser();
			List<String> primitiveInstructions = treeParser.parse(
					dae.getRightExpression(), dae.getReturnName());

			// add a local variable declaration instruction on top

			primitiveInstructions.add(0, dae.getDeclarationInstruction());

			SequenceBlock block = new SequenceBlock(primitiveInstructions);
			return block;
		} else if (et == ExpressionType.NormalAssignment) {
			NormalAssignmentExpression nae = (NormalAssignmentExpression) exp;

			TreeParser treeParser = new TreeParser();
			List<String> primitiveInstructions = treeParser.parse(
					nae.getRightExpression(), nae.getLeftExpression());

			// the root node is a single-value node. like 'a=b'
			if (primitiveInstructions.isEmpty()) {
				SequenceNode node = new SequenceNode(line);
				return node;
			}

			SequenceBlock block = new SequenceBlock(primitiveInstructions);
			return block;
		} else if (et == ExpressionType.VariableDeclaration) {
			SequenceNode node = new SequenceNode(line);
			return node;
		} else {
			// it is not a assignment expression, it is a ordinary expression

			TreeParser treeParser = new TreeParser();
			List<String> primitiveInstructions = treeParser.parse(line, null);

			SequenceBlock block = new SequenceBlock(primitiveInstructions);
			return block;

		}

	}

	/**
	 * parse code lines, gather all top level node/block into a sequenceblock.
	 * 
	 * @param codeLines
	 * @return
	 */
	private SequenceBlock parseCodeLines(List<String> codeLines) {
		List<SequenceNode> list = new ArrayList<SequenceNode>();

		int i = 0;

		while (i < codeLines.size()) {
			String line = codeLines.get(i);

			// it is a empty line
			if (line.matches("^[\\t\\s]*$")) {
				i++;
				continue;
			}

			// either block or one line expression.

			if (isBlock(line)) {
				// i would get the whole block of 'IfElse'
				int endIndex = endOfBlock(codeLines, i, true);
				Block newBlock = parseBlock(codeLines.subList(i, endIndex + 1));
				list.add(newBlock);

				i = endIndex + 1;
				continue;
			} else {
				// one line express could be a complex instruction, but for now,
				// we only assume it is a primitive instruction.
				// TODO support complex instruction in the future.
				SequenceNode seqNode = parseOneLineCode(line);
				if (seqNode == null)
					throw new RuntimeException("statement can not be parsed:"
							+ line);

				list.add(seqNode);
			}

			i++;
		}

		SequenceBlock block = new SequenceBlock();
		block.setSubNodes(list);

		return block;
	}

	private boolean isBlock(String line) {
		// if,while,for,return
		line = line.trim();

		if (line.matches("[\\t\\s]*(if|else|for|while).*")) {
			return true;
		} else
			return false;

	}

	private FlowChart makeFlowChart(SequenceBlock mainBlock) {
		FlowChart fc = new FlowChart();
		StartNode start = new StartNode();

		EndNode end = new EndNode();

		start.connect(mainBlock);
		mainBlock.connect(end);

		fc.addNode(start);
		fc.addNode(mainBlock);
		fc.addNode(end);

		return fc;
	}

	/**
	 * 
	 * 
	 * @param code
	 * @param startIndex
	 *            index of char '{'
	 * @return index of the paired '}'
	 */
	private static int endOfBlock(List<String> codeLines, int startIndex,
			boolean allowContinusBlock) {
		int depth = 1;

		int i;
		for (i = startIndex + 1; i < codeLines.size(); i++) {
			String line = codeLines.get(i).trim();

			if (allowContinusBlock) {

				/**
				 * a ContinusBlock, such as if else, we should write like '}
				 * else {' on one line. so we check depth after one line.
				 */
				if (line.matches("[\\t\\s]*\\}.*"))
					depth--;

				if (line.matches(".*\\{[\\t\\s]*"))
					depth++;

				if (depth == 0)
					break;

			} else {

				if (line.matches("[\\t\\s]*\\}.*")) {
					depth--;
					if (depth == 0)
						break;
				}

				if (line.matches(".*\\{[\\t\\s]*")) {
					depth++;
					if (depth == 0)
						break;
				}

			}

		}
		return i;
	}

	public Class parseAndCompile(InputStream stream, String charEncodingName) {
		try {
			Reader reader = new InputStreamReader(stream, charEncodingName);

			BufferedReader input = new BufferedReader(reader);

			String line = null;

			List<String> codeLines = new ArrayList<String>();
			while ((line = input.readLine()) != null) {
				codeLines.add(line);
				// System.out.println(line);
			}
			// System.out.println("line count:" + codeLines.size());

			Parser parser = new Parser();
			Class clazz = parser.parseClass(codeLines);

			clazz.compile();

			return clazz;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		Interpreter interpreter = new Interpreter();

		// interpreter.run("com.bluesky.my4gl.example.HelloWorld2", "main",
		// new String[] { "Jack" });

		interpreter.run("com.bluesky.my4gl.example.HelloWorld2", "mainFib",
				new String[] { "10" });

		// interpreter.run("com.bluesky.my4gl.example.HelloWorld2", "testInt",
		// new String[] { "5" });

		NativeObject<String> so = (NativeObject<String>) ClassLibrary.getClass(
				PrimitiveType.String).createObject();
		so.setNativeValue("Jack");
		NativeObject<String> result = (NativeObject<String>) interpreter
				.invoke("com.bluesky.my4gl.example.HelloWorld2", "sayHello",
						new com.bluesky.my4gl.core.Object[] { so });

		System.out.println("result of sayHello():\t" + result.getNativeValue());
	}
}
