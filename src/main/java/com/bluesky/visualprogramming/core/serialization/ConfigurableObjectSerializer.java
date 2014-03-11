package com.bluesky.visualprogramming.core.serialization;

import java.io.Reader;
import java.io.Writer;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;

public interface ConfigurableObjectSerializer {

	void serialize(_Object obj, Writer writer, Config config);

	_Object deserialize(Reader reader, Config config);
}
