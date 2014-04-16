package com.bluesky.visualprogramming.timer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.bluesky.visualprogramming.core.AbstractObjectRepositoryListener;
import com.bluesky.visualprogramming.core.Message;
import com.bluesky.visualprogramming.core.MessageType;
import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ParameterStyle;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.Service;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class TimerService implements Service {
	static Logger logger = Logger.getLogger(TimerService.class);

	Timer timer;

	long counter = 0;

	/**
	 * key is interval, value is subscribers.
	 */
	Map<Integer, Set<_Object>> subscribers;

	ObjectRepository repo;

	private volatile boolean running;

	public TimerService(ObjectRepository repo) {
		this.repo = repo;

		repo.addListener(new AbstractObjectRepositoryListener() {

			@Override
			public void afterLoadFromFile(_Object obj) {
				StringValue type = (StringValue) obj.getSystemChild("type");
				if (type != null && type.getValue().equals("timer")) {

					BooleanValue enabledObj = (BooleanValue) obj
							.getChild("enabled");
					IntegerValue intervalObj = (IntegerValue) obj
							.getChild("interval");

					if (intervalObj != null && enabledObj != null) {
						boolean enabled = enabledObj.getBooleanValue();
						int interval = (int) intervalObj.getIntValue();

						if (enabled && interval > 0) {
							subscribe(obj);

							logger.info("create timer: " + obj.getName());
						}
					}

				}

			}

		});
	}

	@Override
	public void init() {
		timer = new Timer();
		subscribers = new HashMap<Integer, Set<_Object>>();

	}

	@Override
	public void start() {
		running = true;

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {

				if (running) {
					for (Integer interval : subscribers.keySet()) {

						if (counter % interval == 0) {
							Set<_Object> set = subscribers.get(interval);
							notify1(set);

						}

					}
					counter++;
				}
			}
		}, 1000, 1000);

		logger.info("timer service started");
	}

	@Override
	public void pause() {
		running = false;
	}

	@Override
	public void resume() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;

		timer.cancel();

	}

	@Override
	public void destroy() {
		subscribers = null;

	}

	public synchronized void subscribe(_Object client) {
		IntegerValue intervalObj = (IntegerValue) client.getChild("interval");

		int interval = (int) intervalObj.getIntValue();
		if (interval == 0)
			return;

		Set<_Object> set = subscribers.get(interval);
		if (set == null) {
			set = new HashSet<_Object>();
			subscribers.put(interval, set);
		}

		set.add(client);

	}

	public synchronized void unsubscribe(_Object client) {

		for (Integer interval : subscribers.keySet()) {
			Set<_Object> set = subscribers.get(interval);

			if (set.contains(client)) {
				set.remove(client);

				if (set.isEmpty())
					subscribers.remove(interval);
			}
		}
	}

	private void notify1(Set<_Object> objects) {
		for (_Object timer : objects) {
			Message msg = new Message(null, timer, "action", null,
					ParameterStyle.ByName, null, MessageType.SyncRequest);

			VirtualMachine.getInstance().getPostService().sendMessage(msg);
		}
	}

}
