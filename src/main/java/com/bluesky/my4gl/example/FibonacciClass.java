package com.bluesky.my4gl.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Field;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.StorageType;
import com.bluesky.my4gl.core.flow.FlowChart;
import com.bluesky.my4gl.core.flow.block.SequenceBlock;
import com.bluesky.my4gl.core.flow.node.EndNode;
import com.bluesky.my4gl.core.flow.node.JudgementNode;
import com.bluesky.my4gl.core.flow.node.SequenceNode;
import com.bluesky.my4gl.core.flow.node.StartNode;
import com.bluesky.my4gl.core.interpreter.Interpreter;
import com.bluesky.my4gl.global.ClassLibrary;

public class FibonacciClass extends ClassImpl {
	public FibonacciClass() {

		setDomain("com.bluesky.my4gl.example");
		
		addImport("com.bluesky.my4gl.io.DebugConsole");
		
		setName("Fibonacci");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		/*
		 * variables
		 */
		Field intValueField = new Field();
		intValueField.setAccessScope(AccessScope.Private);
		intValueField.setName("x");
		intValueField.setClassName("com.bluesky.my4gl.lang.Integer");

		getFields().add(intValueField);

		/**
		 * method integer fib(int n)
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("com.bluesky.my4gl.lang.Integer");
		method.setName("fib");

		method.addParameter("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		FlowChart fc = makeFibFlowChart(method);
		method.setBody(fc);

		addMethod(method);

		/**
		 * method void main(array args)
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);
		method.setStorageType(StorageType.Class);
		method.setReturnClassName("void");
		method.setName("main");

		method.addParameter("args", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Array"));

		fc = makeMainFlowChart(method);
		method.setBody(fc);

		addMethod(method);

	}

	private FlowChart makeFibFlowChart(Method method) {

		/**
		 * if n==0 then return 1 if n==1 then return 1
		 * 
		 * else n1=n-1 n2=n-2 f1=fab(n1) f2=fab(n2) return f1+f2
		 */
		method.addLocalVariable("int1", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		method.addLocalVariable("sn", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));
		method.addLocalVariable("n1", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));
		method.addLocalVariable("n2", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));
		method.addLocalVariable("f1", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));
		method.addLocalVariable("f2", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		StartNode start = new StartNode();

		SequenceNode create1 = new SequenceNode(
				"int1=com.bluesky.my4gl.lang.Integer$.new()");
		create1.setName("int1:int");

		SequenceNode init1 = new SequenceNode("int1.init(1)");
		init1.setName("int1=1");

		SequenceNode stringN = new SequenceNode("sn=n.toString()");
		stringN.setName("sn=n.toString()");

		SequenceNode stringN2 = new SequenceNode("sn=sn.concat(\" fib()\")");
		stringN2.setName("sn=sn+' fib'");

		SequenceNode printSn = new SequenceNode("DebugConsole$.println(sn)");
		printSn.setName("print sn");

		JudgementNode ne0 = new JudgementNode();
		ne0.setExpression("n.equals(0)");
		ne0.setName("if n==0");

		JudgementNode ne1 = new JudgementNode();
		ne1.setExpression("n.equals(1)");
		ne1.setName("if n==1");

		SequenceNode re1 = new SequenceNode("result=int1.self()");
		re1.setName("result=1");

		SequenceNode n1 = new SequenceNode("n1=n.subtract(1)");
		n1.setName("n1=n-1");

		SequenceNode n2 = new SequenceNode("n2=n.subtract(2)");
		n2.setName("n2=n-2");

		SequenceNode f1 = new SequenceNode("f1=this.fib(n1)");
		f1.setName("f1=fib(n1)");

		SequenceNode f2 = new SequenceNode("f2=this.fib(n2)");
		f2.setName("f2=fib(n2)");

		SequenceNode fn = new SequenceNode("result=f1.add(f2)");
		fn.setName("result=f1+f2");

		EndNode end = new EndNode();

		start.connect(create1);
		create1.connect(init1);

		init1.connect(stringN);
		stringN.connect(stringN2);
		stringN2.connect(ne0);
		// printSn.connect(ne0);

		ne0.getTrueOutPort().connect(re1);
		ne0.getFalseOutPort().connect(ne1);
		ne1.getTrueOutPort().connect(re1);
		ne1.getFalseOutPort().connect(n1);

		n1.connect(n2);
		n2.connect(f1);
		f1.connect(f2);
		f2.connect(fn);

		re1.connect(end);
		fn.connect(end);

		FlowChart fc = new FlowChart();
		fc.addNode(start);

		fc.addNode(create1);
		fc.addNode(init1);
		fc.addNode(stringN);
		fc.addNode(stringN2);
		fc.addNode(printSn);

		fc.addNode(ne0);
		fc.addNode(ne1);
		fc.addNode(re1);
		fc.addNode(n1);
		fc.addNode(n2);
		fc.addNode(f1);
		fc.addNode(f2);
		fc.addNode(fn);
		fc.addNode(end);

		return fc;
	}

	private FlowChart makeMainFlowChart(Method method) {
		method.addLocalVariable("fib", ClassLibrary
				.getClass("com.bluesky.my4gl.example.Fibonacci"));
		method.addLocalVariable("str_n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));
		method.addLocalVariable("n", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));
		method.addLocalVariable("sfn", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));
		method.addLocalVariable("fn", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Integer"));

		List<String> statements = new ArrayList<String>();
		statements.add("str_n=args.get(0)");
		// statements.add("debugConsole.println(str_n)");
		statements.add("n=com.bluesky.my4gl.lang.Integer$.new()");
		statements.add("n=n.valueOf(str_n)");
		statements.add("fib=com.bluesky.my4gl.example.Fibonacci$.new()");
		statements.add("fn=fib.fib(n)");
		statements.add("sfn=fn.toString()");
		statements.add("DebugConsole$.println(sfn)");
		statements.add("fib=com.bluesky.my4gl.example.Fibonacci$.new()");
		statements.add("fib=com.bluesky.my4gl.example.Fibonacci$.new()");

		StartNode start = new StartNode();
		SequenceBlock sb = new SequenceBlock(statements);
		EndNode end = new EndNode();

		start.connect(sb);
		sb.connect(end);

		FlowChart fc = new FlowChart();
		fc.addNode(start);
		fc.addNode(sb);
		fc.addNode(end);

		return fc;
	}

	public int fib(int n) {
		if (n == 0 || n == 1)
			return 1;
		else
			return fib(n - 1) + fib(n - 2);
	}

	public static void main(String[] args) {

		Interpreter interpreter = new Interpreter();

		interpreter.run("com.bluesky.my4gl.example.Fibonacci", "main",
				new String[] { "20" });

//		Date d1 = new java.util.Date();
//		int fn = new FibonacciClass().fib(20);
//		System.out.println(fn);
//		Date d2 = new java.util.Date();
//		System.out.println(d2.getTime() - d1.getTime());

	}
}
