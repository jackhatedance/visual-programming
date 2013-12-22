package com.bluesky.visualprogramming.core.serialization.rpc;

import com.bluesky.visualprogramming.core.serialization.dump.ObjectSerializer;

public enum MessageFormatType {
	/**
	 * used by DDwrt, similar to JSON.
	 */
	Ddwrt {
		@Override
		public ObjectSerializer getSerializer() {
			 
			return new DdwrtSerializer();
		}
	};
	
	
	public abstract ObjectSerializer getSerializer();
	
	public static MessageFormatType getType(String format){
			for(MessageFormatType mft : values()){
				if(mft.name().equalsIgnoreCase(format))
					return mft;
			}
			return null;		
	}
}
