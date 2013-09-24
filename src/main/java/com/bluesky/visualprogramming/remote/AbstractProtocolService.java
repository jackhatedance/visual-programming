package com.bluesky.visualprogramming.remote;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import com.bluesky.visualprogramming.core._Object;

public class AbstractProtocolService {
	// key is address of local object, value is local object
	protected BidiMap addressObjectMap = new DualHashBidiMap();

	public AbstractProtocolService() {
		super();
	}

	public _Object getLocalObject(String address) {

		return (_Object) addressObjectMap.get(address);
	}

	public String getAddress(_Object obj) {

		return (String) addressObjectMap.getKey(obj);
	}

}