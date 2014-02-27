package com.bluesky.visualprogramming.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectRepositoryListener;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.callback.CallbackService;
import com.bluesky.visualprogramming.remote.email.EmailService;
import com.bluesky.visualprogramming.remote.http.HttpService;
import com.bluesky.visualprogramming.remote.path.PathService;
import com.bluesky.visualprogramming.remote.ssh.SshService;
import com.bluesky.visualprogramming.remote.xmpp.XmppService;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.AppProperties;

/**
 * all in one service
 * 
 * @author jackding
 * 
 */
public class RemoteCommunicationService {
	static Logger logger = Logger.getLogger(RemoteCommunicationService.class);

	public static final String ALIASES = "aliases";

	private Map<ProtocolType, ProtocolService> services = new HashMap<ProtocolType, ProtocolService>();
	private ObjectRepository objectRepository;

	
	private void initServices(){
		addProtocolService(new CallbackService());
		addProtocolService(new PathService());

		addProtocolService(new XmppService());
		addProtocolService(new SshService());
		addProtocolService(new HttpService());
		addProtocolService(new EmailService());
	}
	public RemoteCommunicationService(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
		
		initServices();
		
		objectRepository.addListener(new ObjectRepositoryListener() {
			@Override
			public void beforeSave(_Object obj) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeDestroy(_Object obj) {

			}

			@Override
			public void afterCreate(_Object obj) {

			}

			@Override
			public void afterLoadFromFile(_Object obj) {
				_Object aliases = obj.getSystemChild(ALIASES);

				_Object owner = obj;

				if (aliases != null) {
					for (int i = 0; i < aliases.getChildCount(); i++) {
						_Object alias = aliases.getChild(i);

						BooleanValue enabled = (BooleanValue) alias
								.getChild("enabled");

						if (enabled != null && !enabled.getBooleanValue())
							continue;

						try {
							ProtocolType pt = ProtocolType
									.valueOf(((StringValue) alias
											.getChild("protocol")).getValue()
											.toUpperCase());
							StringValue address = (StringValue) alias
									.getChild("address");
							StringValue connectionOptionsSV = (StringValue) alias
									.getChild("connectionOptions");
							String connectionOptions = "";
							if (connectionOptionsSV != null)
								connectionOptions = connectionOptionsSV
										.getValue();
							/**
							 * replace security info, such as password place
							 * holders
							 */
							if (connectionOptions.contains("${"))
								connectionOptions = replaceVariables(connectionOptions);
							Config config = new Config(ConnectionOptionUtils
									.parse(connectionOptions));
							register(pt, address.getValue(), owner, config);

							String fullAddress = pt.toString().toLowerCase()
									+ "://" + address.getValue();

							if (logger.isInfoEnabled())
								logger.info("create remote agent for "
										+ owner.getName()
										+ " binding address is " + fullAddress);
						} catch (Exception e) {

							logger.warn("failed to create remote agent for "
									+ owner.getName(), e);
						}
					}

				}

			}

			@Override
			public void afterAllLoaded() {
				// TODO Auto-generated method stub

			}
		});
	}

	public void register(ProtocolType protocol, String address, _Object obj,
			Config config) {

		try {
			services.get(protocol).register(protocol, address, obj, config);
		} catch (Exception e) {
			logger.warn("error when register address:" + address, e);
		}
	}

	public _Object getLocalObject(ProtocolType protocol, String address) {
		ProtocolService svc = services.get(protocol);
		if (svc != null)
			return svc.getLocalObject(address);
		else
			logger.warn("protocol not support:" + protocol);

		return null;
	}

	public Config getConfig(ProtocolType protocol, String address) {
		ProtocolService svc = services.get(protocol);
		if (svc != null)
			return svc.getConfig(address);
		else
			logger.warn("protocol not support:" + protocol);

		return null;
	}

	public void send(ProtocolType protocol, String receiverAddress,
			Message message) {
		if (logger.isDebugEnabled())
			logger.debug(String.format("protocol:%s, to: %s, subject:%s",
					protocol, receiverAddress, message.getSubject()));

		services.get(protocol).send(receiverAddress, message);
	}

	private void addProtocolService(ProtocolService svc) {
		for (ProtocolType pt : svc.getSupportedTypes())
			services.put(pt, svc);

	}

	public ProtocolService getService(ProtocolType pt) {
		return services.get(pt);
	}

	protected String replaceVariables(String str) {
		Properties p = AppProperties.getInstance().getRemoteSecurityConfig();

		for (Object k : p.keySet()) {
			String v = p.getProperty((String) k);
			str = str.replace("${" + k + "}", v);
		}
		return str;
	}
}
