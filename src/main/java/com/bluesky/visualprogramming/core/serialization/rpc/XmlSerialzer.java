package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;

public class XmlSerialzer implements ConfigurableObjectSerializer {

	private boolean dedicatedAttributeField;
	private boolean dedicatedChildNodeField;
	private boolean skipEmptyTextNode;

	@Override
	public void serialize(_Object obj, Writer writer, Config config) {
		if (obj == null)
			return;

		XmlSerialzationVisitor visitor = new XmlSerialzationVisitor();
		visitor.visit(obj, obj.getName());

		try {
			visitor.wrtieXml(writer);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@Override
	public _Object deserialize(Reader reader, Config config) {

		dedicatedAttributeField = config.getBoolean("dedicatedAttributeField",
				true);
		dedicatedChildNodeField = config.getBoolean("dedicatedChildNodeField",
				false);

		String childNodeConvensionTypeStr = config
				.getString("childNodeConvensionType",
						ChildNodeConvensionType.Field.name());
		ChildNodeConvensionType childNodeConvensionType = ChildNodeConvensionType
				.valueOf(childNodeConvensionTypeStr);
		skipEmptyTextNode = config.getBoolean("skipEmptyTextNode", true);

		SAXReader saxReader = new SAXReader();

		try {
			Document document = saxReader.read(reader);
			XmlDomVisitor visitor = new XmlDomVisitor(dedicatedAttributeField,
					dedicatedChildNodeField, childNodeConvensionType,
					skipEmptyTextNode);

			// document.accept(visitor);
			visitor.visit(document);

			return visitor.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
