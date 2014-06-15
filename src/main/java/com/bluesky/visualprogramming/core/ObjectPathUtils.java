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
		if (s.indexOf('\\') >= 0)
			s = s.replaceAll("\\\\", "\\\\b");
		if (s.indexOf('.') >= 0)
			s = s.replaceAll("\\.", "\\\\d");
		if (s.indexOf(' ') >= 0)
			s = s.replaceAll("\\s", "\\\\s");

		return s;
	}

	public static String unescape(String s) {
		if (s.indexOf('\\') < 0)
			return s;

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {

			char c = s.charAt(i);
			if (c == '\\' && i != (s.length() - 1)) {
				char c2 = s.charAt(i + 1);
				switch (c2) {
				case 'd':// dot
					sb.append(".");
					i++;
					break;
				case 's':// space
					sb.append(" ");
					i++;
					break;
				case 'b':// back slash
					sb.append("\\");
					i++;
					break;
				default:
					throw new RuntimeException("failed to unescape string:" + s);
				}

			} else
				sb.append(c);

		}

		return sb.toString();
	}

	public static void main(String[] args) {

		String p = "a.b.c\\d\\sd\\be.f.g";
		String[] ss = parse(p);
		for (String s : ss) {
			System.out.println(s);
		}

		String path = generate(ss);
		System.out.println();
		System.out.println(path);

	}
}
