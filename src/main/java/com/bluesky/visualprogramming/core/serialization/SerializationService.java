package com.bluesky.visualprogramming.core.serialization;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core._Object;

public class SerializationService {

	Map<SerializerType, ObjectSerializer> fullSerializerMap = new HashMap<SerializerType, ObjectSerializer>();
	Map<SerializerType, ObjectSerializer> noGuiserializerMap = new HashMap<SerializerType, ObjectSerializer>();

	public SerializationService() {

		fullSerializerMap.put(SerializerType.Xml, new XmlSerializer(true));
		fullSerializerMap.put(SerializerType.Json, new JsonSerializer(true));

		noGuiserializerMap.put(SerializerType.Xml, new XmlSerializer(false));
		noGuiserializerMap.put(SerializerType.Json, new JsonSerializer(false));
	}

	public void serialize(_Object object, SerializerType format, boolean gui,
			Writer writer) {
		if (gui)
			fullSerializerMap.get(format).serialize(object, writer);
		else
			noGuiserializerMap.get(format).serialize(object, writer);
	}

	public String serialize(_Object object, SerializerType format, boolean gui) {
		StringWriter writer = new StringWriter();
		serialize(object, format, gui, writer);
		return writer.toString();
	}

	public _Object deserialize(Reader reader, SerializerType format) {
		return fullSerializerMap.get(format).deserialize(reader);
	}

	public _Object deserialize(String str, SerializerType format) {
		StringReader reader = new StringReader(str);
		return deserialize(reader, format);
	}

}
