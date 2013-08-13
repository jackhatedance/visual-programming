package com.bluesky.visualprogramming.core;

import java.util.List;
import java.util.Queue;

/**
 * loook after all hunger objects, feed them.
 * 
 * @author jackding
 * 
 */
public class Engine {
	Queue<_Object> hungerObjects;
	List<Worker> works;

	void run() {

		while (true) {
			while (!hungerObjects.isEmpty()) {
				_Object obj = hungerObjects.poll();

				Worker worker = pickFreeWorker();
				worker.feed(obj);
			}
			// sleep , wait for signal that the hunger objects queue is not
			// empty again

		}

	}

	Worker pickFreeWorker() {
		return null;
	}
}
