package com.bluesky.visualprogramming.core.serialization.rpc;


public enum MessageFormat {
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
			
			return new XmlSerialzer();
		}
	},
	HTML {
		@Override
		public ConfigurableObjectSerializer getSerializer() {

			return new HtmlSerialzer();
		}
	};
	
	
	public abstract ConfigurableObjectSerializer getSerializer();
	
	public static MessageFormat getFormat(String format) {
			for(MessageFormat mft : values()){
				if(mft.name().equalsIgnoreCase(format))
					return mft;
			}
			return null;		
	}
}