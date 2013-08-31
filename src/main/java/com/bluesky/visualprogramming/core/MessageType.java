package com.bluesky.visualprogramming.core;

public enum MessageType {
	Normal {
		@Override
		public boolean isRequest() {

			return true;
		}
	},
	SyncReply {
		@Override
		public boolean isReply() {

			return true;
		}
	},
	AsyncReply {
		@Override
		public boolean isReply() {

			return true;
		}
	},
	Recursive {
		@Override
		public boolean isRequest() {

			return true;
		}
	};

	public boolean isReply() {
		return false;
	}

	public boolean isRequest() {
		return false;
	}
}
