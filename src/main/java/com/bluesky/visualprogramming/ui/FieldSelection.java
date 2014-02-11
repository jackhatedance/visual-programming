package com.bluesky.visualprogramming.ui;

import com.bluesky.visualprogramming.core._Object;

public class FieldSelection {
	public _Object object;
	public String fieldName;

	public FieldSelection(_Object object, String fieldName) {
		this.object = object;
		this.fieldName = fieldName;
	}
}
