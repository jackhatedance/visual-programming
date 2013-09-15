package com.bluesky.visualprogramming.core.procedure;

public enum SubjectMatchType {
	RegularExpression {
		@Override
		public SubjectMatcher getMatcher(String rule) {

			return new RegExpMatcher(rule);
		}
	},
	Groovy {
		@Override
		public SubjectMatcher getMatcher(String rule) {

			return new GroovyMatcher(rule);
		}
	};

	public abstract SubjectMatcher getMatcher(String rule);
}
