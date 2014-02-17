package com.bluesky.visualprogramming.remote;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;

public class AbstractProtocolService {
	protected ProtocolType[] supportedTypes = null;

	// key is address of local object, value is local object
	protected BidiMap addressObjectMap = new DualHashBidiMap();

	protected BidiMap addressConfigMap = new DualHashBidiMap();

	public AbstractProtocolService() {
		super();
	}


	public ProtocolType[] getSupportedTypes() {

		return this.supportedTypes;
	}
	public _Object getLocalObject(String address) {

		return (_Object) addressObjectMap.get(address);
	}

	public String getAddress(_Object obj) {

		return (String) addressObjectMap.getKey(obj);
	}

	public Config getConfig(String address) {

		return (Config) addressConfigMap.get(address);
	}

}