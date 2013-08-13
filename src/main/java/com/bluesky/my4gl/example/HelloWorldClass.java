package com.bluesky.my4gl.example;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.my4gl.core.AccessScope;
import com.bluesky.my4gl.core.ClassImpl;
import com.bluesky.my4gl.core.Field;
import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.flow.FlowChart;
import com.bluesky.my4gl.core.flow.block.SequenceBlock;
import com.bluesky.my4gl.core.flow.node.EndNode;
import com.bluesky.my4gl.core.flow.node.StartNode;
import com.bluesky.my4gl.core.interpreter.Interpreter;
import com.bluesky.my4gl.global.ClassLibrary;
import com.bluesky.my4gl.internalClass.lang.PrimitiveType;

public class HelloWorldClass extends ClassImpl {
	public HelloWorldClass() {

		setDomain("com.bluesky.my4gl.example");
		setName("HelloWorld");
		setSuperClass(ClassLibrary.getClass("com.bluesky.my4gl.lang.Object"));

		/*
		 * variables
		 */
		Field intValueField = new Field();
		intValueField.setAccessScope(AccessScope.Private);
		intValueField.setName("intValue");
		intValueField.setClassName("com.bluesky.my4gl.lang.Integer");

		getFields().add(intValueField);

		/**
		 * method string sayHello(string name)
		 */
		Method method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName(PrimitiveType.String.getFullName());
		method.setName("sayHello");

		method.addParameter("name", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));

		FlowChart fc = makeSayHelloFlowChart(method);
		method.setBody(fc);

		addMethod(method);

		/**
		 * method void main(array args)
		 */
		method = new Method();
		method.setAccessScope(AccessScope.Public);

		method.setReturnClassName("void");;
		method.setName("main");

		method.addParameter("args", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.Array"));

		fc = makeMainFlowChart(method);
		method.setBody(fc);

		addMethod(method);

	}

	private FlowChart makeSayHelloFlowChart(Method method) {

		// 1 String s;//LocalVariableDeclaration
		method.addLocalVariable("s", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));

		List<String> statements = new ArrayList<String>();
		statements.add("s=com.bluesky.my4gl.lang.String$.new()");
		statements.add("s.init(\"Hello \")");
		statements.add("s=s.concat(name)");
		statements.add("s=s.concat(\"!\")");
		statements.add("result=s.self()");

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

	private FlowChart makeMainFlowChart(Method method) {
		method.addLocalVariable("helloWorld", ClassLibrary
				.getClass("com.bluesky.my4gl.example.HelloWorld"));

		method.addLocalVariable("name", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));
		method.addLocalVariable("s", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));

		List<String> statements = new ArrayList<String>();

		statements.add("name=args.get(0)");
		statements
				.add("helloWorld=com.bluesky.my4gl.example.HelloWorld$.new()");
		statements.add("s=helloWorld.sayHello(name)");
		statements.add("debugConsole.println(s)");

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

	public static void main(String[] args) {
		Interpreter interpreter = new Interpreter();

		interpreter.run("com.bluesky.my4gl.example.HelloWorld", "main",
				new String[] { "My4GL" });
	}
}
