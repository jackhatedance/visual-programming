package com.bluesky.visualprogramming.remote;


public enum ProtocolType {
	PATH, XMPP, SSH, EMAIL, WECHAT, TWITTER, WEIBO, HTTP {
		@Override
		public boolean needSenderAddress() {
			return false;
		}
	},
	HTTPS {

		@Override
		public boolean needSenderAddress() {
			return false;
		}
	};

	public boolean needSenderAddress() {
		return true;
	}
	
	public int getDefaultPort(){
		return -1;
	}
	
	public static ProtocolType getType(String name){
		
		for (ProtocolType pt : values()){
			if(pt.name().equalsIgnoreCase(name))
				return pt;
		}
		
		throw new RuntimeException("not supported :"+name);
		
	
	}

}
