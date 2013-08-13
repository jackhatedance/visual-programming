package com.bluesky.my4gl.core.parser.java.expression.assignmentExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.parser.java.RegExpUtils;
import com.bluesky.my4gl.core.parser.java.expression.Expression;
import com.bluesky.my4gl.core.parser.java.expression.ExpressionType;

/**
 * exp.field = exp or b=exp
 * 
 * @author jack
 * 
 */
public class NormalAssignmentExpression extends Expression {

	private String leftExpression;

	private String rightExpression;

	private ExpressionType type;

	public NormalAssignmentExpression() {
		type = ExpressionType.NormalAssignment;
	}

	@Override
	public void parse(String expression) {

		super.parse(expression);

		String p = type.getLinePattern();
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(expression);
		if (matcher.matches()) {
			//RegExpUtils.printGroups("NormalAssignmentExpression", matcher);

			leftExpression = matcher.group(1);

			rightExpression = matcher.group(2);
			// rightExpression = ExpressionType.createExpression(rightPart);
		}
	}

	public String getLeftExpression() {
		return leftExpression;
	}

	public void setLeftExpression(String leftExpression) {
		this.leftExpression = leftExpression;
	}

	public String getRightExpression() {
		return rightExpression;
	}

	public void setRightExpression(String rightExpression) {
		this.rightExpression = rightExpression;
	}

}
