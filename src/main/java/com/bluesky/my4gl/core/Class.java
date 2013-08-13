package com.bluesky.my4gl.core;

import java.util.List;
import java.util.Map;

/**
 * class are also object
 * 
 * @author jack
 * 
 */

public interface Class extends Object {

	NativeClass getNativeClass();

	Class getSuperClass();

	void setSuperClass(Class superClass);

	List<Field> getFields();

	void setFields(List<Field> fields);

	List<Method> getMethods();

	void setMethods(List<Method> methods);

	void addMethod(Method method);

	/**
	 * do not search in the super classes
	 * 
	 * @param name
	 * @param accessScopes
	 *            TODO
	 * @return
	 */
	Method findLocalMethod(String name);

	/**
	 * search in the super classes. judge the visibility depends on callerClass
	 * 
	 * @param name
	 * @callerClass
	 * @return
	 */
	Method findMethod(String name, Class visitorClass);

	String getDomain();

	void setDomain(String domain);

	Map<String, String> getImports();

	void setImports(Map<String, String> imports);

	String getName();

	void setName(String name);

	String getFullName();

	Object createObject();

	boolean isAncestorClass(Class clazz);

	/**
	 * 
	 * @param scope
	 * @param visitorClazz
	 * @return
	 */
	boolean isVisible(AccessScope scope, Class visitorClass);

	String getFullClassName(String className);

	/**
	 * compile here means convert optimize performance. such as lookup class in
	 * class library by class name.
	 * 
	 * @return
	 */
	boolean isCompiled();

	void compile();

	Class createClassForClass();
}
