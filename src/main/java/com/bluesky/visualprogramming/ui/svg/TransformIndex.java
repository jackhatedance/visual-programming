package com.bluesky.visualprogramming.ui.svg;

public enum TransformIndex {

	
	Offset {
		@Override
		public int getIndex() {

			return 1;
		}
	},
	Scale {
		@Override
		public int getIndex() {

			return 0;
		}
	};

	public abstract int getIndex();
}
