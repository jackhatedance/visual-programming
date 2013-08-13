package com.bluesky.my4gl.global;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.parser.java.Parser;
import com.bluesky.my4gl.example.FibonacciClass;
import com.bluesky.my4gl.example.HelloWorldClass;
import com.bluesky.my4gl.internalClass.io.DebugConsoleClass;
import com.bluesky.my4gl.internalClass.lang.ArrayClass;
import com.bluesky.my4gl.internalClass.lang.BooleanClass;
import com.bluesky.my4gl.internalClass.lang.CharacterClass;
import com.bluesky.my4gl.internalClass.lang.FloatClass;
import com.bluesky.my4gl.internalClass.lang.IntegerClass;
import com.bluesky.my4gl.internalClass.lang.ObjectClass;
import com.bluesky.my4gl.internalClass.lang.PrimitiveType;
import com.bluesky.my4gl.internalClass.lang.StringClass;
import com.bluesky.my4gl.internalClass.lang.XClass;

/**
 * ObjectManager initialize the object from nothing.<br>
 * 1. it create a class and object X. X as a class has a method 'new'. <br>
 * 2. then create a class 'Object', it is the root class visible in the 4gl
 * scope.<br>
 * 3. then create primitive class like Integer, String, ...etc. which inherit
 * from Object.<br>
 * 4. the X is invisible from the 4gl scope.
 * 
 * @author hz00260
 * 
 */
public class ClassLibrary {
	public static String CLASS_NAME_Object = "com.bluesky.my4gl.lang.Object";

	private Map<String, Class> classes;
	private Map<String, Class> fakeClasses;

	private static ClassLibrary instance;

	private ClassLibrary() {

	}

	public static void init() {
		instance = new ClassLibrary();

		getInstance().classes = new HashMap<String, Class>();
		getInstance().fakeClasses = new HashMap<String, Class>();

		// register X class;
		Class xClass = XClass.getInstance();
		getInstance().classes.put(xClass.getFullName(), xClass);

		register(new ObjectClass());
		register(new BooleanClass());
		register(new IntegerClass());
		register(new FloatClass());
		register(new CharacterClass());
		register(new StringClass());

		register(new ArrayClass());

		// io
		register(new DebugConsoleClass());

		// examples
		register(new HelloWorldClass());
		register(new FibonacciClass());

	}

	public static ClassLibrary getInstance() {

		return instance;
	}

	/**
	 * create a Class for Normal Class , extends from XClass. copy the static
	 * field and method to the new class.
	 * 
	 * @param clazz
	 */
	public static void register(Class clazz) {

		Class classOfClass = clazz.createClassForClass();
		clazz.setClazz(classOfClass);

		getInstance().classes.put(clazz.getFullName(), clazz);
		getInstance().classes.put(classOfClass.getFullName(), classOfClass);

		if (!clazz.isCompiled())
			clazz.compile();

		if (getInstance().fakeClasses.containsKey(clazz.getFullName()))
			getInstance().fakeClasses.remove(clazz.getFullName());
	}

	/**
	 * fullName like 'com.bluesky.my4gl.lang.String'
	 * 
	 * @param fullName
	 * @return
	 */
	public static Class getClass(String fullName) {

		if (fullName != null && fullName.endsWith("MetaClass")) {
			throw new RuntimeException("should not get meta class." + fullName);

		}

		if (fullName == null || fullName.endsWith("$"))
			throw new RuntimeException("worng class name:" + fullName);

		if (!getInstance().classes.containsKey(fullName)) {

			if (getFakeClass(fullName) == null) {
				// put a delegate
				LazyClassHandler handler = new LazyClassHandler(getInstance(),
						fullName);

				Class proxy = (Class) Proxy.newProxyInstance(getInstance()
						.getClass().getClassLoader(),
						new java.lang.Class[] { Class.class }, handler);

				getInstance().fakeClasses.put(fullName, proxy);
			}

			return getFakeClass(fullName);
		} else
			return getRealClass(fullName);

	}

	public static Class getFakeClass(String fullName) {
		return getInstance().fakeClasses.get(fullName);
	}

	public static Class getRealClass(String fullName) {
		if (!getInstance().classes.containsKey(fullName)) {
			// try load from class path(file system)
			Class cls = loadClassFromClassPath(fullName);
			if (cls != null)
				register(cls);
		}

		return getInstance().classes.get(fullName);
	}

	public static Class getClass(PrimitiveType pt) {

		return getInstance().classes.get(pt.getFullName());

	}

	/**
	 * load from file system
	 * 
	 * @param fullNname
	 * @return
	 */
	private static Class loadClassFromClassPath(String fullName) {
		// such as: from a.b.Foo to a/b/Foo
		String resourceName = fullName.replaceAll("\\.", "/");
		resourceName = "/" + resourceName + ".4gl";

		InputStream is = ClassLibrary.class.getResourceAsStream(resourceName);
		if (is == null)
			return null;

		Parser parser = new Parser();
		Class cls = parser.parseAndCompile(is,GlobalSettings.getDefaultEncoding());

		return cls;
	}

}
