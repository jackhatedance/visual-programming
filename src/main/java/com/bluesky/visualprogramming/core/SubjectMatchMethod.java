package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.core.procedure.GroovyMatcher;
import com.bluesky.visualprogramming.core.procedure.RegExpMatcher;
import com.bluesky.visualprogramming.core.procedure.SubjectMatcher;

public enum SubjectMatchMethod {
	RegularExpression {
		@Override
		public SubjectMatcher getMatcher() {

			return new RegExpMatcher();
		}
	},
	Groovy {
		@Override
		public SubjectMatcher getMatcher() {

			return new GroovyMatcher();
		}
	};

	public abstract SubjectMatcher getMatcher();
}
