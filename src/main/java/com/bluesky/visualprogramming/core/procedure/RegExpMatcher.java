package com.bluesky.visualprogramming.core.procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class RegExpMatcher extends SubjectMatcher {
	static Logger logger = Logger.getLogger(RegExpMatcher.class);

	Pattern pattern;
	Matcher matcher;

	String[] groupNames;

	static final String SEPARATOR = "0x0";

	public RegExpMatcher(String rule) {
		super(rule);

		this.groupNames = extractGroupNames(rule);
	}

	@Override
	public boolean matches(String subject) {

		pattern = Pattern.compile(rule, Pattern.UNICODE_CHARACTER_CLASS);
		matcher = pattern.matcher(subject);

		return matcher.matches();
	}

	private String[] extractGroupNames(String regexp) {
		Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(
				regexp);

		List<String> groups = new ArrayList<String>();

		while (m.find()) {
			groups.add(m.group(1));
		}

		return groups.toArray(new String[0]);
	}

	@Override
	public void postProcess(Message msg) {
		// extract parameter from subject if it has named groups.
		if (msg.body != null)
			return;

		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		if (matcher.groupCount() > 0) {
			_Object body = repo.createObject(ObjectType.NORMAL,
					ObjectScope.ExecutionContext);

			for (int i = 0; i < matcher.groupCount(); i++) {
				String groupName = groupNames[i];

				String paramName;
				String paramType;
				if (groupName.contains(SEPARATOR)) {
					String[] ss = groupName.split(SEPARATOR);
					paramName = ss[0];
					paramType = ss[1];

				} else {
					paramName = groupName;
					paramType = "string";
				}

				String paramValue = matcher.group(i + 1);

				ObjectType objectType = ObjectType.valueOf(paramType
						.toUpperCase());
				_Object child = repo.createObject(objectType,
						ObjectScope.ExecutionContext);
				child.setValue(paramValue);

				if (logger.isDebugEnabled())
					logger.debug("add parameter: " + paramName + ", type "
							+ paramType);

				body.addChild(child, paramName, true);
			}
			msg.body = body;
		}
	}

}
