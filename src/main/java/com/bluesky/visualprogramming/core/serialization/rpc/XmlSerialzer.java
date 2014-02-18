package com.bluesky.visualprogramming.core.serialization.rpc;

import java.io.Reader;
import java.io.Writer;
import java.util.Stack;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;
import org.dom4j.Visitor;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.utils.Config;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class XmlSerialzer implements ConfigurableObjectSerializer {



	private boolean dedicatedAttributeField;
	private boolean dedicatedChildNodeField;

	@Override
	public void serialize(_Object obj, Writer writer, Config config) {
		// TODO Auto-generated method stub

	}

	@Override
	public _Object deserialize(Reader reader, Config config) {

		dedicatedAttributeField = config.getBoolean("dedicatedAttributeField",
				true);
		dedicatedChildNodeField = config.getBoolean("dedicatedChildNodeField",
				false);

		SAXReader saxReader = new SAXReader();

		try {
			Document document = saxReader.read(reader);
			XmlDomVisitor visitor = new XmlDomVisitor(dedicatedAttributeField,
					dedicatedChildNodeField);
			
			//document.accept(visitor);
			visitor.visit(document);
			

			return visitor.getObject();
		} catch (Exception e) {

		}
		return null;
	}

}
