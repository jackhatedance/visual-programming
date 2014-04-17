package com.bluesky.visualprogramming.timer;

public enum TimeUnit {

	Second(1), Minute(60), Hour(3600), Day(86400), Week(604800);
	private int seconds;

	private TimeUnit(int seconds) {
		this.seconds = seconds;
	}

	public int getInSeconds() {
		return seconds;
	};
}
