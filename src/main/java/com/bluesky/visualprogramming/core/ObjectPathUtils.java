package com.bluesky.visualprogramming.core;

public class ObjectPathUtils {

	public static String[] parse(String path) {
		String[] ss = path.split("(?<!\\\\)\\.");

		for (int i = 0; i < ss.length; i++) {
			ss[i] = unescape(ss[i]);
		}

		return ss;
	}

	public static String generate(String[] names) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < names.length; i++) {
			String s = escape(names[i]);

			if (i > 0)
				sb.append(".");

			sb.append(s);
		}

		return sb.toString();
	}

	public static String escape(String s) {
		return s.replaceAll("\\.", "\\\\.");
	}

	public static String unescape(String s) {
		return s.replaceAll("\\\\.", "\\.");
	}

	public static void main(String[] args) {

		String p = "a.b.c\\.d.e";
		String[] ss = parse(p);
		for (String s : ss) {
			System.out.println(s);
		}
		
		String path = generate(ss);
		System.out.println(path);

	}
}
