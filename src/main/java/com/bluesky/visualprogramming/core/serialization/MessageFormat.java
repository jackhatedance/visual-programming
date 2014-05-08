package com.bluesky.visualprogramming.core.serialization;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.vm.VirtualMachine;



public enum MessageFormat {
	Xstream {
		@Override
		public ConfigurableObjectSerializer getSerializer() {

			return new XstreamSerializer();
		}
	},
	/**
	 * used by DDwrt, similar to JSON.
	 */
	Ddwrt {
		@Override
		public ConfigurableObjectSerializer getSerializer() {
			 
			DdwrtSerializer s =  new DdwrtSerializer();
			
			ObjectRepository repo= VirtualMachine.getInstance().getObjectRepository();
			s.setRepo(repo);
			
			return s;
		}
	},
	XML{
		@Override
		public ConfigurableObjectSerializer getSerializer() {
			
			 
			XmlSerialzer s =  new XmlSerialzer();
			
			ObjectRepository repo= VirtualMachine.getInstance().getObjectRepository();
			s.setRepo(repo);
			
			return s;
		}
	},
	HTML {
		@Override
		public ConfigurableObjectSerializer getSerializer() {

			 
			HtmlSerialzer s =  new HtmlSerialzer();
			
			ObjectRepository repo= VirtualMachine.getInstance().getObjectRepository();
			s.setRepo(repo);
			
			return s;
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
