package com.bluesky.visualprogramming.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.AbstractObjectRepositoryListener;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.SystemField;
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

	private void initServices() {
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

		objectRepository.addListener(new AbstractObjectRepositoryListener() {

			@Override
			public void onStartService(_Object obj) {
				_Object aliases = obj.getSystemTopChild(SystemField.Aliases);

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
							StringValue addressSV = (StringValue) alias
									.getChild("address");
							String address = addressSV.getValue();

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
							if (address.contains("${"))
								address = replaceVariables(address);

							if (connectionOptions.contains("${"))
								connectionOptions = replaceVariables(connectionOptions);

							Config config = new Config(ConnectionOptionUtils
									.parse(connectionOptions));
							register(pt, address, owner, config);

							String fullAddress = pt.toString().toLowerCase()
									+ "://" + address;

							if (logger.isDebugEnabled())
								logger.debug("create remote agent for "
										+ owner.getName()
										+ " binding address is " + fullAddress);
						} catch (Exception e) {

							logger.warn("failed to create remote agent for "
									+ owner.getName(), e);
						}
					}

				}

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

	/**
	 * evaluate express.
	 * 
	 * @param str
	 * @return
	 */
	protected String replaceVariables(String str) {
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(str);

		String newStr = str;
		while (m.find()) {
			String path = m.group(1);
			String value = replaceVariable(path);
			if (value != null)
				newStr = newStr.replace("${" + path + "}", value);
		}

		return newStr;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	protected String replaceVariable(String path) {

		try {
			_Object obj = objectRepository.getObjectByPath(path);
			if (obj != null && obj instanceof StringValue)
				return ((StringValue) obj).getValue();
		} catch (Exception e) {
			logger.error("path not existing:" + path);
		}

		return null;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(ObjectRepository.ROOT_OBJECT + ".name", "jack");
		map.put(ObjectRepository.ROOT_OBJECT + ".password", "123");
		String str = "name=${" + ObjectRepository.ROOT_OBJECT
				+ ".name};pass=${"
				+ ObjectRepository.ROOT_OBJECT + ".password}";
		Pattern p = Pattern.compile("\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(str);

		String newStr = str;
		while (m.find()) {
			String path = m.group(1);
			String value = map.get(path);
			if (value != null)
				newStr = newStr.replace("${" + path + "}", value);
		}
		System.out.println(newStr);

	}
}
