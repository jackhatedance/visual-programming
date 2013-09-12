package com.bluesky.visualprogramming.remote;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectRepositoryListener;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.StringValue;

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

				if (owner == null)
					return;

				int index = owner.getChildIndex(obj);
				Field field = owner.getField(index);

				if (field.getName().equals("_aliases")) {
					for (int i = 0; i < aliases.getChildCount(); i++) {
						_Object alias = aliases.getChild(i);

						BooleanValue enabled = (BooleanValue) alias
								.getChild("enabled");
						if (!enabled.getBooleanValue())
							continue;

						try {
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

							if (logger.isInfoEnabled())
								logger.info("create remote agent for "
										+ owner.getName()
										+ " binding address is "
										+ address.getValue());
						} catch (Exception e) {

							logger.warn("failed to create remote agent for "
									+ owner.getName(),e);
						}
					}

				}

			}
		});
	}

	public void register(ProtocolType protocol, String address, _Object obj,
			String connectionOptions) {

		try {
			services.get(protocol).register(address, obj, connectionOptions);
		} catch (Exception e) {
			logger.warn("error when register address:" + address, e);
		}
	}

	public _Object getLocalObject(ProtocolType protocol, String address) {
		return services.get(protocol).getLocalObject(address);
	}

	public void send(ProtocolType protocol, String receiverAddress,
			Message message) {
		services.get(protocol).send(receiverAddress, message);
	}

	public void addProtocolService(ProtocolService svc) {
		services.put(svc.getType(), svc);

	}
}
