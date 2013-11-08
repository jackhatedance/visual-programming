package com.bluesky.visualprogramming.ui.svg;

public enum SvgElementType {
	Object,
	/**
	 * used for transform box, must be rectangle
	 */
	Border,
	/**
	 * any part of the graphic, when you need a place to show team color. reserved.
	 */
	Color,
	/**
	 * usually under the graphic
	 */
	Name,
	/**
	 * usually is value , but in the future it was planned to be field desc.
	 */
	Description;
}
