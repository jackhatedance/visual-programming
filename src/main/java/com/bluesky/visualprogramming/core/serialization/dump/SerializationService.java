package com.bluesky.visualprogramming.core.serialization.dump;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;

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

	public static void main(String[] args) {
		SerializationService svc = new SerializationService();
		
		
		_Object obj = new _Object(0);
		Field nameField = new Field("name");
		
		_Object nameObject = new StringValue(0);
		nameObject.setValue("jack");
				
		nameField.target = nameObject; 
		obj.getFields().add(nameField);
		
		String json = svc.serialize(obj, SerializerType.Json, false);
		
		System.out.println(json);
		
		_Object obj2 = svc.deserialize(json, SerializerType.Json);
		String json2 = svc.serialize(obj2, SerializerType.Json, true);
		
		System.out.println(json2);
		
	}
}
