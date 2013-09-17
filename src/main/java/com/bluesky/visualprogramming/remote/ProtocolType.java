package com.bluesky.visualprogramming.remote;

public enum ProtocolType {
	XMPP, SSH, EMAIL, WECHAT, TWITTER, WEIBO, HTTP {
		@Override
		public boolean needSenderAddress() {
			return false;
		}
	};

	public boolean needSenderAddress() {
		return true;
	}
}
