package com.bluesky.my4gl.core.parser.java.expression;

import org.apache.log4j.Logger;

import com.bluesky.my4gl.core.parser.java.expression.assignmentExpression.DeclarationAssignmentExpression;
import com.bluesky.my4gl.core.parser.java.expression.assignmentExpression.NormalAssignmentExpression;

/**
 * a line of code, a.k.a expression can be very complex. such as 'Foo foo =
 * a.b.bar(g,1+2,f.get())'. we should decompose it to many primitive
 * instructions. <br>
 * such as:<br>
 * <ul>
 * <li>
 * t1=f.get();</li>
 * <li>
 * t2=1+2;</li>
 * <li>
 * t3=a.b;</li>
 * <li>
 * t4=t3.bar(g,t2,t1);</li>
 * <li>
 * Foo foo;</li>
 * <li>
 * foo = t4;</li>
 * </ul>
 * 
 * @author hz00260
 * 
 */
public enum ExpressionType {

	/*
	 * start with "//"
	 */
	Comment {
		@Override
		public String getPattern() {
			return "//.*";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return CommentExpression.class;
		}

	},
	/**
	 * Foo foo = bar(or any expression)
	 */
	DeclarationAssignment {
		@Override
		public String getPattern() {
			return "([A-Z]{1}[\\w\\d]*)[\\t\\s]+([a-z_]{1}[\\w\\d]*)[\\t\\s]*=[\\t\\s]*(.+)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return DeclarationAssignmentExpression.class;
		}

	},
	/**
	 * foo = bar
	 */
	NormalAssignment {
		@Override
		public String getPattern() {
			return "([\\w\\d_]+)[\\t\\s]*=[\\t\\s]*(.*)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return NormalAssignmentExpression.class;
		}

	},
	/**
	 * new Foo(a,b,c);
	 */
	ObjectCreation {
		@Override
		public String getPattern() {
			return "new[\\t\\s]+([A-Z]{1}[\\w\\d]*)\\((.*)\\)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return ObjectCreationExpression.class;
		}

	},
	/**
	 * obj.field or expression.field
	 */
	FieldAcess {
		@Override
		public String getPattern() {
			return "(.*)\\.([a-z_]{1}[\\w\\d]*)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return FieldAccessExpression.class;
		}

	},

	/**
	 * a.b.bar(a,b,c);
	 */
	Invocation {
		@Override
		public String getPattern() {
			return "(([\\w_]{1}[\\w\\d_]*\\$?\\.)*)([a-z_]{1}[\\w\\d_]*)\\((.*)\\)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return InvocationExpression.class;
		}

	},
	/**
	 * Integer i
	 */
	VariableDeclaration {
		@Override
		public String getPattern() {
			return "([A-Z_]{1}[\\w\\d_]*)[\\t\\s]+([a-z_][\\w\\d_]*)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return VariableDeclarationExpression.class;
		}
	},
	/**
	 * constant can be: Integer, Float, String
	 */
	Constant {
		@Override
		public String getPattern() {
			String s = String
					.format("(true|false|\\d+|\\d\\.\\d|\\'\\w\\'|\\\".*\\\")");
			return s;
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return ConstantExpression.class;
		}

	},
	/**
	 * name of local variable, parameter, temporary variable, global
	 * variable(class as object).such as: a,String
	 */
	Variable {
		@Override
		public String getPattern() {
			return "([a-z_]{1}[\\w\\d]*\\$?)";
		}

		@Override
		public Class<? extends Expression> getExpressionClass() {
			return VariableExpression.class;
		}
	};

	public abstract String getPattern();

	public String getLinePattern() {
		String linePattern = "^[\\t\\s]*" + getPattern() + ";?[\\t\\s]*$";
		return linePattern;
	}

	public abstract Class<? extends Expression> getExpressionClass();

	private static Logger logger = Logger.getLogger(ExpressionType.class);

	/**
	 * default implementation to create a Expression instance. you can override
	 * it if need.
	 * 
	 * @param expression
	 * @return
	 */
	public Expression parse(String expression) {
		try {
			Expression exp = getExpressionClass().newInstance();
			if (logger.isDebugEnabled())
				logger.debug("parsing:" + expression + ", as " + this);

			exp.parse(expression);
			return exp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * parameters are group of expression separated by comma(','). the parameter
	 * is one of: field-access, invocation(with return value),constant,variable.
	 * 
	 * @param s
	 */
	public static void parseParameters(String s) {
		String pFieldAccess = "";
		String pInvocation = "";
		String pConstant = "";
		String pVariable = "";

	}

	public static ExpressionType getType(String s) {
		s = s.trim();
		for (ExpressionType it : ExpressionType.values()) {

			if (s.matches(it.getLinePattern()))
				return it;

		}
		throw new RuntimeException("unknown expression type:" + s);
	}

	public static Expression createExpression(String s) {
		ExpressionType et = ExpressionType.getType(s);
		Expression e = et.parse(s);

		return e;
	}

	public static void main(String[] args) {

		// String s1 = "Foo foo = a.b.c.bar.x(a,b.foo(x,y.getz()).getBar(),c);";
		// ExpressionType et = ExpressionType.getType(s1);
		// Expression e = et.createExpression(s1);

		String[] ss = new String[] { "String s;", "Pattern p=b;",
				"foo = new Foo(a,b,c);", "Integer i;", "//abc def",
				"Foo foo = a.b.c.bar.x(a,b.foo(x,y.getz()).getBar(),c);",
				"DebugConsole$.println(s2);", "Object$.assign(1)" };
		for (String s : ss) {
			ExpressionType tt = ExpressionType.getType(s);
			System.out.println(s + "  --  " + tt);

			// Instruction ins = tt.parse(s);
			// System.out.println(s + "  --  " + tt + " -- " + ins);
		}

	}
}
