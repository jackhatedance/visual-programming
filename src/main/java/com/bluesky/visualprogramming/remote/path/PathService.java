package com.bluesky.visualprogramming.remote.path;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.remote.ProtocolService;
import com.bluesky.visualprogramming.remote.ProtocolType;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class PathService implements ProtocolService {
	private ProtocolType[] supportedTypes = new ProtocolType[] { ProtocolType.PATH };

	private ObjectRepository repo;

	ObjectRepository getRepo() {
		if (repo == null)
			repo = VirtualMachine.getInstance().getObjectRepository();

		return repo;
	}

	@Override
	public void register(ProtocolType protocol, String address, _Object obj,
			String connectionOptions) {
		// intend to do nothing
	}

	@Override
	public _Object getLocalObject(String address) {
		return getRepo().getObjectByPath(address);
	}

	public String getAddress(_Object obj) {
		return obj.getPath();
	}

	@Override
	public void send(String receiverAddress, Message message) {
		throw new RuntimeException("not supported");
	}

	@Override
	public ProtocolType[] getSupportedTypes() {

		return supportedTypes;
	}
}
