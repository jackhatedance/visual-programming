package com.bluesky.visualprogramming.core.procedure;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyMatcher implements SubjectMatcher {

	@Override
	public boolean isMatch(String rule, String subject) {
		Binding binding = new Binding();
		binding.setVariable("subject", subject);
		GroovyShell shell = new GroovyShell(binding);

		Boolean value = (Boolean) shell.evaluate("return " + rule);

		return value;
	}

}
