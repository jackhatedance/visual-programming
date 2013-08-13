package com.bluesky.my4gl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bluesky.my4gl.core.flow.FlowChart;
import com.bluesky.my4gl.core.flow.node.Node;
import com.bluesky.my4gl.core.instruction.Instruction;
import com.bluesky.my4gl.core.instruction.MethodInvocationInstruction;
import com.bluesky.my4gl.global.ClassLibrary;
import com.bluesky.my4gl.internalClass.lang.StringNativeClass;
import com.bluesky.my4gl.internalClass.lang.XClass;

/**
 * class is also a object
 * 
 * @author jack
 * 
 */
public class ClassImpl extends ObjectImpl implements Class {

	/**
	 * Class related
	 */

	private String domain;
	/**
	 * key/value: Object/com.xx.yy.Object
	 */
	private Map<String, String> imports;
	private String name;
	private Class superClass;
	private List<Field> fields;
	private List<Method> methods;

	private NativeClass nativeClass;

	private boolean compiled = false;

	/**
	 * for optimization purpose. <br>
	 * we can gather all of the methods(in local and in super classes) in one
	 * place. should doing this when compiling.
	 */
	private Map<String, Method> methodCache;

	public ClassImpl() {
		setClazz(XClass.getInstance());

		imports = new HashMap<String, String>();

		fields = new ArrayList<Field>();
		methods = new ArrayList<Method>();
	}

	public void addMethod(Method method) {
		methods.add(method);
		method.setClazz(this);
	}

	@Override
	public boolean isAncestorClass(Class ancestorClazz) {

		if (superClass == null)
			return false;
		else {
			if (superClass == ancestorClazz)
				return true;
			else
				return superClass.isAncestorClass(ancestorClazz);
		}
	}

	public Method findLocalMethod(String name) {

		return findLocalMethod(name, AccessScope.toSet());
	}

	public Method findLocalMethod(String name, Set<AccessScope> accessScopes) {
		for (Method m : methods) {
			if (m.getName().equals(name)
					&& accessScopes.contains(m.getAccessScope()))
				return m;
		}
		return null;
	}

	@Override
	public Method findMethod(String name, Class visitorClass) {

		// firstly, seach in local
		for (Method m : methods) {
			if (m.getName().equals(name)
					&& isVisible(m.getAccessScope(), visitorClass))

				return m;
		}

		/**
		 * if goes here, means not found locally,then search in ancestor class
		 */
		if (superClass != null)
			return superClass.findMethod(name, visitorClass);

		return null;
	}

	public Class getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Class superClass) {
		this.superClass = superClass;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NativeClass getNativeClass() {
		return nativeClass;
	}

	public void setNativeClass(NativeClass nativeClass) {
		this.nativeClass = nativeClass;
	}

	public String getFullName() {
		return domain + "." + name;
	}

	public Map<String, String> getImports() {
		return imports;
	}

	public void setImports(Map<String, String> imports) {
		this.imports = imports;
	}

	/**
	 * do not support wild card.
	 * 
	 * @param import_
	 */
	public void addImport(String import_) {
		String ss[] = import_.split("\\.");
		String shortClassName = ss[ss.length - 1];

		imports.put(shortClassName, import_);

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		// class head

		String supperClassName = "null";
		if (superClass != null)
			supperClassName = superClass.getName();
		sb.append(String.format("class %s extends %s {\r\n", name,
				supperClassName));

		// variables
		for (Field f : fields) {
			sb.append(f + "\r\n");
		}

		// methods
		for (Method m : methods) {
			sb.append(m + "\r\n");
		}

		// end class
		sb.append("}");

		return sb.toString();
	}

	public static void main(String[] args) {
		ClassImpl fooClass = new ClassImpl();
		fooClass.setDomain("com.bluesky.my4gl");
		fooClass.setName("Foo");
		fooClass.setSuperClass(null);

		Field barField = new Field();
		barField.setAccessScope(AccessScope.Private);
		barField.setName("bar");
		barField.setClassName("com.bluesky.my4gl.lang.String");

		fooClass.getFields().add(barField);

		Method helloMethod = new Method();
		helloMethod.setAccessScope(AccessScope.Public);
		helloMethod.setReturnClassName("void");
		helloMethod.setName("hello");

		Variable nameParameter = new Variable();
		nameParameter.setName("name");
		nameParameter.setClazz(ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));

		helloMethod.addParameter("name", ClassLibrary
				.getClass("com.bluesky.my4gl.lang.String"));
		fooClass.addMethod(helloMethod);

		System.out.println(fooClass);
	}

	@Override
	public Object createObject() {
		Object obj = new ObjectImpl();

		return obj;
	}

	@Override
	public boolean isVisible(AccessScope scope, Class visitorClass) {
		return AccessScope.isVisible(this, scope, visitorClass);

	}

	/**
	 * check if it is imported.
	 */
	@Override
	public String getFullClassName(String className) {
		String fullClassName = className;

		if (className.indexOf('.') < 0) {
			// it is a short class name

			if (getImports().containsKey(className))
				fullClassName = getImports().get(className);
			else
				throw new RuntimeException(
						"can not get the full class name for '" + className
								+ "'");
		}
		return fullClassName;
	}

	@Override
	public void compile() {
		// if a method invocation instruction, whose object-name is short,
		// convert it to full name.
		for (Method method : methods) {

			Class returnClass = null;
			String className = method.getReturnClassName();
			if (className != null && !className.equals("void")) {
				String fullClassName = getFullClassName(className);
				returnClass = ClassLibrary.getClass(fullClassName);
			}

			method.setReturnClass(returnClass);

			if (!method.isNativeMethod()) {
				FlowChart fc = method.getBody();
				for (Node node : fc.getNodes()) {

					compileNodeTree(node);

				}
			}
		}

		// set the flag
		compiled = true;
	}

	private void compileNodeTree(Node node) {
		if (node.isSimpleNode()) {
			Instruction ins = node.getInstruction();
			if (ins instanceof MethodInvocationInstruction) {
				MethodInvocationInstruction mii = (MethodInvocationInstruction) ins;
				// if it is a short name, like 'String$', then replace it with
				// full class name
				if (mii.getObjectName().endsWith("$")
						&& mii.getObjectName().indexOf('.') < 0) {
					String shortClassName = mii.getObjectName().substring(0,
							mii.getObjectName().length() - 1);
					String fullClassName = getFullClassName(shortClassName);
					mii.setObjectName(fullClassName + "$");
				}
			}
		} else {
			for (Node c : node.getChildren()) {
				compileNodeTree(c);
			}
		}
	}

	/**
	 * copy static attributes to a new class.
	 * 
	 * @return
	 */
	public Class createClassForClass() {
		ClassImpl classOfClass = new ClassImpl();

		classOfClass.setDomain(getDomain());
		classOfClass.setName(getName() + "MetaClass");
		classOfClass.setSuperClass(XClass.getInstance());

		classOfClass.setNativeClass(getNativeClass());

		// copy static fields
		for (Field f : getFields()) {
			if (f.storageType == StorageType.Class) {
				classOfClass.getFields().add(f);
			}
		}

		// copy static methods
		for (Method m : getMethods()) {
			if (m.getStorageType() == StorageType.Class) {
				classOfClass.getMethods().add(m);
			}
		}

		return classOfClass;
	}

	@Override
	public boolean isCompiled() {
		return compiled;
	}

}
