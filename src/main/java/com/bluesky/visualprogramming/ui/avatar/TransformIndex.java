package com.bluesky.visualprogramming.ui.avatar;

public enum TransformIndex {

	BaseScale {
		@Override
		public int getIndex() {

			return 0;
		}
	},
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
