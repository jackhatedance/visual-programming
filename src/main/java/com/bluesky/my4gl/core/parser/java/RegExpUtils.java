package com.bluesky.my4gl.core.parser.java;

import java.util.regex.Matcher;

public class RegExpUtils {

	public static void printGroups(String prefix,Matcher matcher) {
		for (int i = 0; i <= matcher.groupCount(); i++)
			System.out.println(prefix+"\t"+i + ":[" + matcher.group(i) + "]");
	}
}
