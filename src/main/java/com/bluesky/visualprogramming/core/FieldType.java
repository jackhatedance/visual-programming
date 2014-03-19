package com.bluesky.visualprogramming.core;

public enum FieldType {
	/**
	 * strong field, a.k.a. strong reference. the target is owned by the owner
	 * of the field.
	 */
	STRONG,
	/**
	 * weak field. the target don't owned by the owner.
	 */
	WEAK;
}
