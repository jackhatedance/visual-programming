package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.remote.callback.Callback;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class HtmlSerialzer implements ConfigurableObjectSerializer {
	static Logger logger = Logger.getLogger(HtmlSerialzer.class);

	private Semaphore responseReady = new Semaphore(0);
	private _Object html;

	@Override
	public void serialize(_Object obj, Writer writer, Config config)
 {

		// convert to _Object to HTML
		VirtualMachine vm = VirtualMachine.getInstance();
		ObjectRepository repo = vm.getObjectRepository();

		String address = "path://_root.lib.web.render@local";
		Link receiverLink = (Link) repo.createObject(ObjectType.LINK,
				ObjectScope.ExecutionContext);
		receiverLink.setValue(address);

		_Object params = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);
		params.setField("target", obj, false);

		vm.getPostService().sendMessageFromNobody(receiverLink, "do", params,
				new Callback() {

					@Override
					public void onComplete(_Object result) {
						html = result;
						if (logger.isDebugEnabled())
							logger.debug("HTML render complete.");

						responseReady.release();

					}
				});

		try {
			if (logger.isDebugEnabled())
				logger.debug("HTML serialzer wait for web render result.");
			// wait until render finished
			responseReady.acquire();

			if (logger.isDebugEnabled())
				logger.debug("HTML serialzer wakeup.");

			if (html != null) {
				StringValue svHtml = (StringValue) html;
				writer.write(svHtml.getValue());
			}
		} catch (InterruptedException e) {

			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public _Object deserialize(Reader reader, Config config) {

		throw new RuntimeException("not supported");
	}


}
