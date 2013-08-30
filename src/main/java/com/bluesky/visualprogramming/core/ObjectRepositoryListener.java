package com.bluesky.visualprogramming.core;

public interface ObjectRepositoryListener {

	void afterCreate(_Object obj);

	void beforeDestroy(_Object obj);

	void afterLoad(_Object obj);
}
