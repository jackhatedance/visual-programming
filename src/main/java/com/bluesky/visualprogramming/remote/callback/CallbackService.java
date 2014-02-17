package com.bluesky.visualprogramming.remote.callback;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * used for call cooby method from outside. and give a chance to process after
 * the call is completed.
 * 
 * @author jack
 * 
 */
public class CallbackService implements ProtocolService {
	private ProtocolType[] supportedTypes = new ProtocolType[] { ProtocolType.CALLBACK };

	private ObjectRepository repo;

	Map<String, Callback> callbacks = new HashMap<String, Callback>();

	ObjectRepository getRepo() {
		if (repo == null)
			repo = VirtualMachine.getInstance().getObjectRepository();

		return repo;
	}

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {
		throw new RuntimeException("not supported");
	}

	@Override
	public _Object getLocalObject(String address) {
		// no local object
		return null;
	}

	public String getAddress(_Object obj) {
		throw new RuntimeException("not supported");
	}

	@Override
	public void send(String receiverAddress, Message message) {
		Callback cb = callbacks.get(receiverAddress);
		if (cb != null) {
			cb.onComplete(message.body);

			// use once.
			callbacks.remove(receiverAddress);
		}

	}

	@Override
	public ProtocolType[] getSupportedTypes() {

		return supportedTypes;
	}

	public void setCallback(String address, Callback callback) {
		callbacks.put(address, callback);
	}

	@Override
	public Config getConfig(String address) {

		return null;
	}
}
