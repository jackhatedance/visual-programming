package com.bluesky.my4gl.core.parser.java;

/**
 * it is used by parser.
 * 
 * @author hz00260
 * 
 */
public enum ClassTokenType {

	Package {
		@Override
		public String getPattern() {

			return "package (([\\w\\d]+)\\.)*[\\w\\d]+;";
		}
	},
	Import {
		@Override
		public String getPattern() {

			return "import[\\t\\s]+((([\\w\\d]+)\\.)+([A-Z]{1}[\\w\\d]*));";
		}
	},
	Class {
		@Override
		public String getPattern() {

			return "((public|protected|private)[\\t\\s]+)?class[\\t\\s]+([A-Z]{1}[\\w\\d]*)[\\t\\s]+(extends[\\t\\s]+([\\w\\d]+)[\\t\\s]+)?\\{";
		}
	},
	Field {
		@Override
		public String getPattern() {

			return "[\\t\\s]*((public|protected|private)[\\t\\s]+)?((static|dynamic)[\\t\\s]+)?([A-Z]{1}[\\w\\d]*)[\\t\\s]+([\\w\\d]+);";
		}
	},
	Method {
		@Override
		public String getPattern() {

			return "[\\t\\s]*((public|protected|private)[\\t\\s]+)?((static|dynamic)[\\t\\s]+)?(([\\w\\d]+)[\\t\\s]+)?([\\w\\d]+)\\((.*)\\)[\\s\\t]*[\\{]?";
		}
	};

	abstract public String getPattern();

	// abstract boolean isMatch(String s);

	public static ClassTokenType getType(String s) {
		for (ClassTokenType tt : ClassTokenType.values()) {
			if (s.matches(tt.getPattern()))
				return tt;

		}
		throw new RuntimeException("unknown token type:" + s);
	}

	public static void main(String[] args) {

		String[] ss = new String[] {
				"package com.bluesky.my4gl.example;",
				"import java.util.regex.Pattern;",
				"public class Foo extends Object {",
				"protected AccessScope accessScope;",
				"public Object createPrimitiveObject(Class clazz, String expression) {",
				"Bar bar;", "FlowChart fc = makeFibFlowChart(method);" };
		for (String s : ss) {
			ClassTokenType tt = ClassTokenType.getType(s);
			System.out.println(s + "  --  " + tt);
		}

	}
}
