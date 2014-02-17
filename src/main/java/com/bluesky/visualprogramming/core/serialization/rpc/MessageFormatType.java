package com.bluesky.visualprogramming.core.serialization.rpc;

import com.bluesky.visualprogramming.core.serialization.dump.ObjectSerializer;

public enum MessageFormatType {
	/**
	 * used by DDwrt, similar to JSON.
	 */
	Ddwrt {
		@Override
		public ConfigurableObjectSerializer getSerializer() {
			 
			return new DdwrtSerializer();
		}
	},
	XML{
		@Override
		public ConfigurableObjectSerializer getSerializer() {
			
			return null;
		}
	};
	
	
	public abstract ConfigurableObjectSerializer getSerializer();
	
	public static MessageFormatType getType(String format){
			for(MessageFormatType mft : values()){
				if(mft.name().equalsIgnoreCase(format))
					return mft;
			}
			return null;		
	}
}
