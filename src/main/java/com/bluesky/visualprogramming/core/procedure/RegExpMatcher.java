package com.bluesky.visualprogramming.core.procedure;


public class RegExpMatcher implements SubjectMatcher {

	@Override
	public boolean isMatch(String rule, String subject) {
		return subject.matches(rule);
	}

}
