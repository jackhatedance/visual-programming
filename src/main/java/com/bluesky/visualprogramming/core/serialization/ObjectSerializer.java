package com.bluesky.visualprogramming.core.serialization;

import java.io.Reader;
import java.io.Writer;

import com.bluesky.visualprogramming.core._Object;

public interface ObjectSerializer {

	void serialize(_Object obj, Writer writer);

	_Object deserialize(Reader reader);
}
