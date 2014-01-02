package com.bluesky.visualprogramming.core;

public enum ObjectLayout {
	XY, List {
		public void preprocess(_Object owner) {
			// re-arrange positions
			int unitHeight = 500;
			int unitWidth = 800;
			int maxRows = 7;
			int x = 0, y = 0;
			int i = 0;
			for (Field f : owner.getFields()) {
				x = i / maxRows;
				y = i % maxRows;

				f.getArea().x = x * unitWidth;
				f.getArea().y = y * unitHeight;

				i++;
			}
		}
	};

	public void preprocess(_Object owner) {

	}
}
