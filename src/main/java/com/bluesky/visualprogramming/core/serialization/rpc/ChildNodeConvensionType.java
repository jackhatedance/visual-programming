package com.bluesky.visualprogramming.core.serialization.rpc;

public enum ChildNodeConvensionType {
	/**
	 * child elements be put into a list. e.g. obj.childNodes.get(0); pros: not
	 * losing any information. cons: not convenient to navigate
	 */
	List,
	/**
	 * child elements as direct child field, can be accessed by tag name. e.g.
	 * obj.childNodes.bar; or obj.bar if dedicatedChildField is enabled.
	 * 
	 * for those child nodes has same tag name, they are put into a list. e.g.
	 * obj.items.get(0);
	 */
	Field;
}
