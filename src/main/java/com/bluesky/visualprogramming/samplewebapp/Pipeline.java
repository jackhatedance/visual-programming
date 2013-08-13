package com.bluesky.visualprogramming.samplewebapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * sequential processor
 * @author jackding
 *
 */
public abstract class Pipeline implements Processor {

	protected List<Processor> processors = new ArrayList<Processor>();

	@Override
	public void process(Map context) {

		for (Processor p : processors)
			p.process(context);

	}
}
