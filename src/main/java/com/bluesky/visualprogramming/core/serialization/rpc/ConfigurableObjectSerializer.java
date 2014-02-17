package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import com.bluesky.visualprogramming.core._Object;

public interface ConfigurableObjectSerializer {

	void serialize(_Object obj, Writer writer, Properties config);

	_Object deserialize(Reader reader, Properties config);
}
