package com.bluesky.my4gl.core.instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.instruction.parameter.ClassParameter;
import com.bluesky.my4gl.core.instruction.parameter.ConstantParameter;
import com.bluesky.my4gl.core.instruction.parameter.Parameter;
import com.bluesky.my4gl.core.instruction.parameter.ParameterType;
import com.bluesky.my4gl.core.instruction.parameter.VariableParameter;
import com.bluesky.my4gl.global.ClassLibrary;

public class MethodInvocationInstruction extends Instruction {

	private String returnName;
	private String objectName;
	private String methodName;
	private String[] stringParameters;
	private Parameter[] parameters = new Parameter[] {};

	/**
	 * [return]=[object].[method]([parameter...])
	 */
	@Override
	public void parseExpression(String exp) {

		Pattern p = Pattern.compile(InstructionType.Invocation.getLinePattern());
		Matcher matcher = p.matcher(exp);

		if (matcher.matches()) {
			// for (int i = 0; i <= matcher.groupCount(); i++)
			// System.out.println(i + ":[" + matcher.group(i) + "]");

			returnName = matcher.group(2);

			objectName = matcher.group(3);
			// remove the last '.'
			objectName = objectName.substring(0, objectName.length() - 1);

			methodName = matcher.group(5);

			String parameterPart = matcher.group(6);
			if (parameterPart != null) {
				// a, b , c

				Pattern p2 = Pattern.compile("([^,]+)");
				Matcher m2 = p2.matcher(parameterPart);

				List<Parameter> parameterList = new ArrayList<Parameter>();
				while (m2.find()) {
					// System.out.println(m2.group(1));

					String pExp = m2.group(1);

					Parameter param = ParameterType.createPatameter(pExp);

					parameterList.add(param);
				}
				parameters = parameterList.toArray(new Parameter[0]);
			}
		} else
			throw new RuntimeException("unable to parse:" + exp);

	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i].getExpression());
			if (i < parameters.length - 1)
				sb.append(", ");
		}

		if (returnName != null && returnName != "")
			return String.format("%s=%s.%s(%s)", returnName, objectName,
					methodName, sb.toString());
		else
			return String.format("%s.%s(%s)", objectName, methodName, sb
					.toString());
	}

	public static void main(String[] args) {
		ClassLibrary.init();

		String exp = "Foo foo = a.b$.init(\"ab\",b)";
		MethodInvocationInstruction mii = new MethodInvocationInstruction();
		mii.parseExpression(exp);

		System.out.println(mii.toString());
	}
}
