package com.bluesky.visualprogramming.core.nativeImpl.system.service;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;

public class TimerService extends NativeMethodSupport {

	private static com.bluesky.visualprogramming.timer.TimerService getService() {
		return getVM().getTimerService();
	}

	@ParameterList({})
	public static void start() {

		getService().start();

	}

	@ParameterList({})
	public static void stop() {

		getService().stop();

	}

	@ParameterList({ "timer" })
	public static void subscribe(_Object timer) {

		getService().subscribe(timer);

	}

	@ParameterList({ "timer" })
	public static void execute(_Object timer) {

		getService().unsubscribe(timer);

	}
}
