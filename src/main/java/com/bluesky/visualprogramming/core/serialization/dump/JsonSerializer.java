package com.bluesky.visualprogramming.core.serialization.dump;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class JsonSerializer extends XmlSerializer {

	public JsonSerializer(boolean gui) {
		super(gui);

	}

	@Override
	protected void createXstream() {

		xstream = new XStream(new JettisonMappedXmlDriver());

	}
}
