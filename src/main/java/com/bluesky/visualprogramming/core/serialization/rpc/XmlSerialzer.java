package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import com.bluesky.visualprogramming.core._Object;

public class XmlSerialzer implements ConfigurableObjectSerializer {

	@Override
	public void serialize(_Object obj, Writer writer, Properties config) {
		// TODO Auto-generated method stub

	}

	@Override
	public _Object deserialize(Reader reader, Properties config) {
		
		String method = (String)config.get("method");
				
		return null;
	}

	
}
