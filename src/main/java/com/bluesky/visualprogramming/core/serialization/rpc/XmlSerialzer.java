package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.Reader;
import java.io.Writer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class XmlSerialzer implements ConfigurableObjectSerializer {
	public static String ATTRIBUTES_FIELD = "_attributes";
	public static String CHILD_NODES_FIELD = "_childNodes";

	@Override
	public void serialize(_Object obj, Writer writer, Config config) {
		// TODO Auto-generated method stub

	}

	private boolean dedicatedAttributeField;
	private boolean dedicatedChildNodeField;

	@Override
	public _Object deserialize(Reader reader, Config config) {

		dedicatedAttributeField = config.getBoolean("dedicatedAttributeField",
				true);
		dedicatedChildNodeField = config.getBoolean("dedicatedChildNodeField",
				false);

		SAXReader saxReader = new SAXReader();

		try {
			Document document = saxReader.read(reader);
			_Object rootObj = treeWalk(document.getRootElement());

			return rootObj;
		} catch (Exception e) {

		}
		return null;
	}

	public _Object treeWalk(Element element) {
		ObjectRepository repo = VirtualMachine.getInstance()
				.getObjectRepository();

		_Object obj = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);
		String name = element.getName();
		obj.setName(name);

		// process attributes
		if (element.attributeCount() > 0) {

			_Object attrParent = obj;
			if (dedicatedAttributeField) {
				_Object attributes = repo.createObject(ObjectType.NORMAL,
						ObjectScope.ExecutionContext);

				attributes.setName(ATTRIBUTES_FIELD);
				obj.setField(ATTRIBUTES_FIELD, attributes, true);

				attrParent = attributes;
			}

			for (int i = 0; i < element.attributeCount(); i++) {
				Attribute attr = element.attribute(i);

				_Object attribute = repo.createObject(ObjectType.STRING,
						ObjectScope.ExecutionContext);
				attribute.setValue(attr.getValue());

				attrParent.setField(attr.getName(), attribute, true);
			}
		}

		// process child nodes
		// process attributes
		if (element.attributeCount() > 0) {
			_Object childOwner = obj;
			if (dedicatedChildNodeField) {
				_Object childNodes = repo.createObject(ObjectType.NORMAL,
						ObjectScope.ExecutionContext);

				childNodes.setName(CHILD_NODES_FIELD);
				obj.setField(CHILD_NODES_FIELD, childNodes, true);
				childOwner = childNodes;
			}

			for (int i = 0, size = element.nodeCount(); i < size; i++) {
				Node node = element.node(i);
				if (node instanceof Element) {
					_Object childObj = treeWalk((Element) node);

					childOwner.setField(childObj.getName(), childObj, true);
				} else {
					// do something....
				}
			}
		}

		return obj;
	}

}
