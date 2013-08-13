package com.bluesky.my4gl.core.parser.java.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluesky.my4gl.core.parser.java.RegExpUtils;

public class InvocationExpression extends Expression {

	private String result;
	private Expression objectExpression;
	private String method;
	private Expression[] parameterExpressions;

	@Override
	public void parse(String expression) {

		super.parse(expression);

		String p = ExpressionType.Invocation.getPattern();
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(expression);
		if (matcher.matches()) {
			RegExpUtils.printGroups("Invocation", matcher);

			String object = matcher.group(1);
			objectExpression = ExpressionType.createExpression(object);

			method = matcher.group(3);

			String parameters = matcher.group(4);
			String[] parametersArray = parameters.split(",");
			parameterExpressions = new Expression[parametersArray.length];
			for (int i = 0; i < parametersArray.length; i++) {

				parameterExpressions[i] = ExpressionType
						.createExpression(parametersArray[i]);

			}

		}
	}
}
