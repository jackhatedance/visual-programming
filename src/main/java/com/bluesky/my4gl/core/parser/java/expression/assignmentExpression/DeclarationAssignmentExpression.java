package com.bluesky.my4gl.core.parser.java.expression.assignmentExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.parser.java.expression.Expression;
import com.bluesky.my4gl.core.parser.java.expression.ExpressionType;

/**
 * Foo foo = expression
 * 
 * @author jack
 * 
 */
public class DeclarationAssignmentExpression extends Expression {

	private String returnClassName;
	private String returnName;

	private String rightExpression;
	private ExpressionType type;

	public DeclarationAssignmentExpression() {
		type = ExpressionType.DeclarationAssignment;
	}

	@Override
	public void parse(String expression) {

		super.parse(expression);

		String p = type.getLinePattern();
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(expression);
		if (matcher.matches()) {
			// RegExpUtils.printGroups("DeclarationAssignment", matcher);

			returnClassName = matcher.group(1);
			returnName = matcher.group(2);

			rightExpression = matcher.group(3);
			// rightExpression = ExpressionType.createExpression(rightPart);
		}
	}

	public String getDeclarationInstruction() {
		return String.format("%s %s;", returnClassName, returnName);
	}

	public String getReturnClassName() {
		return returnClassName;
	}

	public void setReturnClassName(String returnClassName) {
		this.returnClassName = returnClassName;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public String getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(String rightExpression) {
		this.rightExpression = rightExpression;
	}

}
