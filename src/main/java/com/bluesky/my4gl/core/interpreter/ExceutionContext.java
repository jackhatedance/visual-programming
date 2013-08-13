package com.bluesky.my4gl.core.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.my4gl.core.Method;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.core.flow.node.Node;

/**
 * invocation is a instance of Method Call. as the method object store the
 * declaration info, the invocation stores the state of certain invocation
 * 
 * @author jack
 * 
 */
public class ExceutionContext {
	/**
	 * object instance and method are being executed
	 */
	protected Object object;
	protected Method method;

	protected Map<String, com.bluesky.my4gl.core.Object> parameters;
	protected Map<String, com.bluesky.my4gl.core.Object> localVariables;
	/**
	 * objects that have no explicit variable name,but generated and used by
	 * interpreter.
	 */
	//protected Map<String, com.bluesky.my4gl.core.Object> tempVariables;

	/**
	 * each method has a flow chart which describes the control logic, and this
	 * is the currently executing node
	 */
	private Node currentNode;

	public ExceutionContext() {
		parameters = new HashMap<String, Object>();
		localVariables = new HashMap<String, Object>();
		//tempVariables = new HashMap<String, Object>();
	}

	public com.bluesky.my4gl.core.Object getVariable(String name) {
		if (localVariables.containsKey(name))
			return localVariables.get(name);
		else if (parameters.containsKey(name))
			return parameters.get(name);
		else if (object.containsField(name)) {
			return object.getFieldValue(name);
		} else
			throw new RuntimeException("unknow variable:" + name);
	}

	private boolean isTemporaryVariable(String name) {
		return name.startsWith("_T_");
	}

	public void setVariable(String name, com.bluesky.my4gl.core.Object value) {
		//green gate for temporary variables
		if (isTemporaryVariable(name)) {
			localVariables.put(name, value);
			return;
		}

		if (localVariables.containsKey(name))
			localVariables.put(name, value);
		else if (parameters.containsKey(name))
			parameters.put(name, value);
		else if (this.object.containsField(name)) {
			this.object.setFieldValue(name, value);
		} else
			throw new RuntimeException("unknow variable:" + name);
	}

	public com.bluesky.my4gl.core.Object getReturnValue() {
		return getVariable("result");
	}

	@Override
	public String toString() {
		String nodeDesc = "";
		if (currentNode != null)
			nodeDesc = currentNode.toString();

		String classDesc = "";
		if (object != null && object.getClazz() != null)
			classDesc = object.getClazz().getFullName();
		return String.format("%s.%s():%s", classDesc, method.getName(),
				nodeDesc);
	}

	public Object getObj() {
		return object;
	}

	public void setObj(Object obj) {
		this.object = obj;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public Map<String, com.bluesky.my4gl.core.Object> getParameters() {
		return parameters;
	}

	public void setParameters(
			Map<String, com.bluesky.my4gl.core.Object> parameters) {
		this.parameters = parameters;
	}

	public Map<String, com.bluesky.my4gl.core.Object> getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(
			Map<String, com.bluesky.my4gl.core.Object> localVariables) {
		this.localVariables = localVariables;
	}

	

}
