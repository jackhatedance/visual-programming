package com.bluesky.visualprogramming.core;


public enum MessageType {
	SyncRequest(true, false) {
		@Override
		public MessageType getReplyType() {

			return SyncReply;
		}
	},
	AsyncRequest(false, false) {
		@Override
		public MessageType getReplyType() {

			return AsyncReply;
		}
	},
	SyncReply(true, true), AsyncReply(
false, true);

	private MessageType(boolean sync, boolean reply) {
		this.sync = sync;
		this.reply = reply;
	}

	private boolean sync;
	private boolean reply;

	public boolean isSync() {
		return sync;
	}

	public boolean isReply() {
		return reply;
	}

	public MessageType getReplyType() {
		return null;
	}

}
