package com.bluesky.my4gl.core.instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.Class;
import com.bluesky.my4gl.core.Variable;
import com.bluesky.my4gl.core.instruction.parameter.Parameter;
import com.bluesky.my4gl.global.ClassLibrary;

/**
 * 
 * @author jack
 * 
 */
public class CreateObjectInstruction extends Instruction {
	protected String varClassName;
	protected String varName;
	protected String className;
	protected Parameter[] parameters = new Parameter[] {};

	// protected Class clazz;

	/**
	 * a = new Foo(), b = new Integer(), c = new String();
	 */
	@Override
	public void parseExpression(String exp) {
		Pattern p = Pattern.compile(InstructionType.CreateObject.getLinePattern());
		Matcher matcher = p.matcher(exp);
		if (matcher.matches()) {
			
//			for (int i = 1; i <= matcher.groupCount(); i++)
//				System.out.println(i + ":[" + matcher.group(i)+"]");

			
			varClassName = matcher.group(2);
			varName = matcher.group(3);

			className = matcher.group(4);

			// clazz = ClassLibrary.getClass(className);

			// a, b , c
			String parameters = matcher.group(5);
			if (parameters != null && parameters.trim().length() > 0) {
				Pattern p2 = Pattern.compile("([\\w\\d]+)");
				Matcher m2 = p2.matcher(parameters);

				List<String> parameterList = new ArrayList<String>();
				while (m2.find()) {
					// System.out.println(m2.group(1));
					parameterList.add(m2.group(1));
				}
			}
		}

	}

	@Override
	public String toString() {

		return String.format("%s = new %s()", varName, className);
	}

	public String getVarClassName() {
		return varClassName;
	}

	public void setVarClassName(String varClassName) {
		this.varClassName = varClassName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public static void main(String[] args) {
		CreateObjectInstruction ci = new CreateObjectInstruction();
		ci.parseExpression("Foo obj=new Bar(a,b);");

		System.out.println(ci);

	}
}
