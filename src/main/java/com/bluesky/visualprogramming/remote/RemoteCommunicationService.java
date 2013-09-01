package com.bluesky.visualprogramming.remote;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectRepositoryListener;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.messageEngine.Worker;

/**
 * all in one service
 * 
 * @author jackding
 * 
 */
public class RemoteCommunicationService {
	static Logger logger = Logger.getLogger(RemoteCommunicationService.class);

	private Map<ProtocolType, ProtocolService> services = new HashMap<ProtocolType, ProtocolService>();
	private ObjectRepository objectRepository;

	public RemoteCommunicationService(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;

		objectRepository.addListener(new ObjectRepositoryListener() {

			@Override
			public void beforeDestroy(_Object obj) {

			}

			@Override
			public void afterCreate(_Object obj) {

			}

			@Override
			public void afterLoad(_Object obj) {
				_Object aliases = obj;
				_Object owner = obj.getOwner();

				if (obj.getName().equals("_aliases")) {
					for (int i = 0; i < aliases.getChildCount(); i++) {
						_Object alias = aliases.getChild(i);

						ProtocolType pt = ProtocolType
								.valueOf(((StringValue) alias
										.getChild("protocol")).getValue()
										.toUpperCase());
						StringValue address = (StringValue) alias
								.getChild("address");
						StringValue connectionOptions = (StringValue) alias
								.getChild("connectionOptions");

						register(pt, address.getValue(), owner,
								connectionOptions.getValue());

						if (logger.isDebugEnabled())
							logger.debug("create remote agent for "
									+ owner.getName() + " binding address is "
									+ address.getValue());
					}

				}

			}
		});
	}

	public void register(ProtocolType protocol, String address, _Object obj,
			String connectionOptions) {
		services.get(protocol).register(address, obj, connectionOptions);
	}

	public _Object getLocalObject(ProtocolType protocol, String address) {
		return services.get(protocol).getLocalObject(address);
	}

	public void send(ProtocolType protocol, String receiverAddress,
			Message message) {
		services.get(protocol).send(receiverAddress, message);
	}

	public void addProtocolService(ProtocolService svc) {
		services.put(ProtocolType.XMPP, svc);

	}
}