package com.bluesky.visualprogramming.ui.selection;

public enum Hotspot {

	North(ResizeType.Y), South(ResizeType.Y), East(ResizeType.X), West(
			ResizeType.X), NorthWest(ResizeType.XY), NorthEast(ResizeType.XY), SouthEast(
			ResizeType.XY), SouthWest(ResizeType.XY);

	private ResizeType resizeType;

	private Hotspot(ResizeType resizeType) {
		this.resizeType = resizeType;
	}

}
