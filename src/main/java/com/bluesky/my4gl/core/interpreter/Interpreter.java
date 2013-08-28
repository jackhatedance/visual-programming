package com.bluesky.my4gl.core.interpreter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.flow.node.EndNode;
import com.bluesky.my4gl.core.flow.node.Node;
import com.bluesky.my4gl.core.flow.node.StartNode;
import com.bluesky.my4gl.core.instruction.AssignmentInstruction;
import com.bluesky.my4gl.core.instruction.DeclareLocalVariableInstruction;
import com.bluesky.my4gl.core.instruction.FieldAccessInstruction;
import com.bluesky.my4gl.core.instruction.Instruction;
import com.bluesky.my4gl.core.instruction.MethodInvocationInstruction;
import com.bluesky.my4gl.core.instruction.parameter.Parameter;
import com.bluesky.my4gl.global.ClassLibrary;
import com.bluesky.my4gl.internalClass.lang.NativeObject;
import com.bluesky.my4gl.internalClass.lang.PrimitiveType;

/**
 * interpreter knows how to execute a flow chart. i.e. get into a method
 * calling, and returning from it. <br>
 * execute the code in the method can be in different ways. especially it is not
 * code, but a flow chart.<br>
 * anyway, here is the basic idea: if it is a local variable declaration, put it
 * into the execute context; if it is a assignment, let the variable point to
 * the new object;if it is a create object node, we create a object; if it is a
 * invocation, we recursively execute the method. and finally, if it is a native
 * method, we invoke the native method, which stop the call stack from
 * deepening. So it is very important to understand that any virtual code,
 * eventually will invoke native code.
 * 
 * @author HZ00260
 * 
 */
public class Interpreter {

	private Stack<ExceutionContext> stack;

	private Logger logger = Logger.getLogger(Interpreter.class);

	public Interpreter() {
		// init class library
		ClassLibrary.init();

		/**
		 * all classes are global variables
		 */

	}

	/*
	 * invoke a static method.
	 */
	public Object invoke(String fullClassName, String methodName,
			Object[] params) {
		logger.info("my4gl interpreter is starting...");

		stack = new Stack<ExceutionContext>();

		ExceutionContext initCtx = new ExceutionContext();
		initCtx.object = getGlobalVariable(fullClassName);// static
		if (initCtx.object == null) {

			throw new RuntimeException("class not found:" + fullClassName);
		}
		initCtx.method = initCtx.object.getClazz().findLocalMethod(methodName);
		if (initCtx.method == null) {
			throw new RuntimeException("method not found:" + fullClassName
					+ "." + methodName + "()");

		}
		/**
		 * prepare params
		 */
		Set<String> paramNameSet = initCtx.method.getParameters().keySet();
		int i = 0;
		for (String pName : paramNameSet) {
			Object pValue = params[i];
			initCtx.parameters.put(pName, pValue);

			i++;
		}

		initLocalVariables(initCtx.method, initCtx);

		stack.push(initCtx);

		Node currentNode = initCtx.method.getBody().getStartNode();
		Node nextNode = null;

		int count = 0;
		long t1 = System.nanoTime();
		while (currentNode != null) {
			ExceutionContext ctx = getCurrentInvocation();

			// if (!ctx.method.getBody().getNodes().contains(currentNode))
			// throw new RuntimeException("current node is not in method.");

			ctx.setCurrentNode(currentNode);

			if (logger.isDebugEnabled())
				logger.debug("begin execute ctx:" + ctx + "; stack depth:"
						+ stack.size());
			// logger.debug("begin execute node:" + currentNode.toString());

			nextNode = executeNode(currentNode);

			// logger.debug("end execute node:" + currentNode.toString());

			currentNode = nextNode;

			count++;
		}

		logger.info("my4gl interpreter has finished.");

		long t2 = System.nanoTime();
		long timeCost = (t2 - t1) / 1000000;
		float timePerNode = (float) timeCost / count;
		String statisticsInfo = String.format(
				"%d nodes executed in %d milliseconds, %f milliseconds/node",
				count, timeCost, timePerNode);
		logger.info("my4gl interpreter execution statistics: " + statisticsInfo);

		if (initCtx.method.getReturnClassName() != null)
			return initCtx.getReturnValue();
		else
			return null;
	}

	/**
	 * execute a main entry method
	 * 
	 * @param clazz
	 * @param mainMethod
	 */
	public void run(String fullClassName, String message, String[] params) {

		NativeObject<Object[]> args = (NativeObject<Object[]>) ClassLibrary
				.getClass(PrimitiveType.Array).createObject();
		args.setNativeValue(new Object[params.length]);
		for (int i = 0; i < params.length; i++) {
			NativeObject<String> so = (NativeObject<String>) ClassLibrary
					.getClass(PrimitiveType.String).createObject();
			so.setNativeValue(params[i]);
			args.getNativeValue()[i] = so;
		}

		invoke(fullClassName, message, new Object[] { args });

	}

	/**
	 * execute one step of the logic node
	 * 
	 * @param stack
	 * @param node
	 * @return next node
	 */
	public Node executeNode(Node node) {
		ExceutionContext ctx = stack.peek();
		ctx.setCurrentNode(node);

		Node result;
		if (node instanceof StartNode) {
			StartNode sn = (StartNode) node;
			result = sn.getNextNode();
		} else if (node instanceof EndNode) {
			EndNode en = (EndNode) node;
			// return to caller,pop stack
			stack.pop();// current

			if (!stack.isEmpty()) {
				ExceutionContext parentInv = stack.peek();// caller

				// set return value and next node
				MethodInvocationInstruction mii = (MethodInvocationInstruction) parentInv
						.getCurrentNode().getInstruction();
				parentInv
						.setVariable(mii.getReturnName(), ctx.getReturnValue());

				result = parentInv.getCurrentNode().getNextNode();
			} else {
				result = null;
			}
		} else {

			// normal node

			Instruction ins = node.getInstruction();

			int stackDepth1 = stack.size();

			com.bluesky.my4gl.core.Object executeResult = executeInstruction(node
					.getInstruction());

			int stackDepth2 = stack.size();
			if (stackDepth2 != stackDepth1) {
				// a new stack item is pushed
				result = stack.peek().method.getBody().getStartNode();
			} else {
				node.setValue(executeResult);
				result = node.getNextNode();
			}
		}

		return result;
	}

	private void initLocalVariables(Method method, ExceutionContext ctx) {
		Set<String> names = method.getLocalVariables().keySet();
		for (String name : names) {
			com.bluesky.my4gl.core.Class clazz = (com.bluesky.my4gl.core.Class) method
					.getLocalVariables().get(name);
			ctx.localVariables.put(name, null);
		}

		// special variables
		if (method.getReturnClassName() != null
				|| method.getReturnClass() != null)
			ctx.localVariables.put("result", null);

		ctx.localVariables.put("this", ctx.object);
	}

	private ExceutionContext getCurrentInvocation() {
		return stack.peek();
	}

	protected com.bluesky.my4gl.core.Object executeInstruction(
			Instruction instruction) {
		com.bluesky.my4gl.core.Object result = null;
		if (instruction instanceof MethodInvocationInstruction)
			result = executeInstruction_MethodInvoke((MethodInvocationInstruction) instruction);
		else if (instruction instanceof DeclareLocalVariableInstruction) {
			result = executeInstruction_DeclareLocalVariable((DeclareLocalVariableInstruction) instruction);
		} else if (instruction instanceof AssignmentInstruction) {
			result = executeInstruction_Assignment((AssignmentInstruction) instruction);
		} else if (instruction instanceof FieldAccessInstruction) {
			result = executeInstruction_FieldAccess((FieldAccessInstruction) instruction);
		}
		return result;
	}

	protected com.bluesky.my4gl.core.Object executeInstruction_DeclareLocalVariable(
			DeclareLocalVariableInstruction instruction) {

		ExceutionContext ctx = getCurrentInvocation();

		com.bluesky.my4gl.core.Object result = null;

		if (ctx.localVariables.containsKey(instruction.getVarName()))
			throw new RuntimeException("variable already exist:" + instruction);

		ctx.localVariables.put(instruction.getVarName(), null);

		return null;
	}

	protected com.bluesky.my4gl.core.Object executeInstruction_FieldAccess(
			FieldAccessInstruction instruction) {

		ExceutionContext ctx = getCurrentInvocation();

		Object object = ctx.getVariable(instruction.getObjectName());
		Object fieldValue = object.getFieldValue(instruction.getFieldName());
		ctx.setVariable(instruction.getReturnName(), fieldValue);

		return fieldValue;
	}

	protected com.bluesky.my4gl.core.Object executeInstruction_Assignment(
			AssignmentInstruction instruction) {

		ExceutionContext ctx = getCurrentInvocation();

		Object rightObject = ctx.getVariable(instruction.getRightVar());
		ctx.setVariable(instruction.getLeftVar(), rightObject);

		return null;
	}

	/**
	 * global variables are actually classes(as objects)
	 * 
	 * @param name
	 * @return
	 */
	private Object getGlobalVariable(String name) {
		String pureName = name.substring(0, name.length());

		com.bluesky.my4gl.core.Class cls = ClassLibrary.getClass(pureName);
		return cls;
	}

	protected com.bluesky.my4gl.core.Object executeInstruction_MethodInvoke(
			MethodInvocationInstruction instruction) {
		if (logger.isDebugEnabled())
			logger.debug("execute invocattion:" + instruction);

		ExceutionContext ctx = getCurrentInvocation();

		com.bluesky.my4gl.core.Object result = null;

		MethodInvocationInstruction mii = instruction;

		com.bluesky.my4gl.core.Object obj = null;
		String objName = mii.getObjectName();

		// firstly search in global variables
		if (objName.endsWith("$")) {
			obj = getGlobalVariable(objName.substring(0, objName.length() - 1));
		}

		// then search in context
		if (obj == null)
			obj = ctx.getVariable(objName);

		// still null? nullpoint exception
		if (obj == null)
			throw new RuntimeException("NullPointException occurs:"
					+ instruction);

		Method method = obj.getClazz().findMethod(mii.getMethodName(),
				obj.getClazz());
		if (method == null)
			throw new RuntimeException("unknow message:" + mii.getMethodName());

		// mii.
		if (method.isNativeMethod()) {
			NativeClass nc = method.getClazz().getNativeClass();

			result = nc.invoke(ctx, obj, method.getName(),
					getObjectArray(ctx, mii.getParameters()));

			// has return value
			if (mii.getReturnName() != null) {
				ctx.setVariable(mii.getReturnName(), result);
			}
		} else {
			/**
			 * create a new execute context, push into the stack.
			 */
			ExceutionContext newInv = new ExceutionContext();
			stack.push(newInv);

			newInv.object = ctx.getVariable(mii.getObjectName());

			newInv.method = newInv.object.getClazz().findMethod(
					mii.getMethodName(), newInv.object.getClazz());
			if (newInv.method == null)
				throw new RuntimeException("unknow message:"
						+ mii.getMethodName());

			newInv.parameters = getObjectMap(ctx, newInv.method,
					mii.getParameters());

			initLocalVariables(newInv.method, newInv);

		}

		return result;

	}

	private com.bluesky.my4gl.core.Object[] getObjectArray(
			ExceutionContext ctx, Parameter[] params) {

		List<com.bluesky.my4gl.core.Object> list = new ArrayList<com.bluesky.my4gl.core.Object>();
		for (Parameter p : params) {
			list.add(p.getTargetObject(ctx));
		}
		return list.toArray(new com.bluesky.my4gl.core.Object[0]);
	}

	private Map<String, com.bluesky.my4gl.core.Object> getObjectMap(
			ExceutionContext ctx, Method method, Parameter[] params) {

		String[] names = method.getParameters().keySet().toArray(new String[0]);

		Map<String, com.bluesky.my4gl.core.Object> map = new HashMap<String, com.bluesky.my4gl.core.Object>();
		for (int i = 0; i < params.length; i++) {

			map.put(names[i], params[i].getTargetObject(ctx));

		}
		return map;
	}
}
