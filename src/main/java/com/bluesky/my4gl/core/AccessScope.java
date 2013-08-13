package com.bluesky.my4gl.core;

import java.util.HashSet;
import java.util.Set;

public enum AccessScope {
	Private, Protected, Public;
	public static Set<AccessScope> toSet() {
		Set<AccessScope> set = new HashSet<AccessScope>();
		set.add(Public);
		set.add(Protected);
		set.add(Private);
		return set;
	}

	public static AccessScope getScope(String s) {
		for (AccessScope scope : values()) {
			if (scope.toString().equalsIgnoreCase(s))
				return scope;
		}
		throw new RuntimeException("unknown access scope:" + s);
	}

	public static boolean isVisible(Class resourceClass,
			AccessScope resourceScope, Class visitorClass) {

		if (resourceScope == AccessScope.Public)
			return true;

		if (resourceClass == visitorClass)
			return true;

		if (resourceScope == AccessScope.Private) {
			if (resourceClass == visitorClass)
				return true;
		}

		if (resourceScope == AccessScope.Protected) {
			// in same package
			if (resourceClass.getDomain().equals(visitorClass.getDomain()))
				return true;
			// subclass
			if (visitorClass.isAncestorClass(resourceClass))
				return true;
		}

		return false;
	}
}
