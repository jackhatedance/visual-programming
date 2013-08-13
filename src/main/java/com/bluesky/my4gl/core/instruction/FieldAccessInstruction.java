package com.bluesky.my4gl.core.instruction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.global.ClassLibrary;

public class FieldAccessInstruction extends Instruction {

	private String returnName;
	private String objectName;
	private String fieldName;

	/**
	 * [return]=[object].[field]
	 */
	@Override
	public void parseExpression(String exp) {

		Pattern p = Pattern.compile(InstructionType.FieldAccess.getLinePattern());
		Matcher matcher = p.matcher(exp);

		if (matcher.matches()) {
			for (int i = 0; i <= matcher.groupCount(); i++)
				System.out.println(i + ":[" + matcher.group(i) + "]");

			returnName = matcher.group(1);

			objectName = matcher.group(2);

			fieldName = matcher.group(3);

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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (returnName != null && returnName != "")
			return String
					.format("%s=%s.%s ", returnName, objectName, fieldName);
		else
			return String.format("%s.%s ", objectName, fieldName);
	}

	public static void main(String[] args) {
		ClassLibrary.init();

		String exp = "Foo foo = a.b$.init(\"ab\",b)";
		FieldAccessInstruction mii = new FieldAccessInstruction();
		mii.parseExpression(exp);

		System.out.println(mii.toString());
	}
}
