package com.bluesky.visualprogramming.core.serialization.rpc;

import java.util.Stack;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;
import org.dom4j.VisitorSupport;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

/**
 * build _Object by visiting DOM of XML.
 * 
 * it is not classic visitor.
 * 
 * @author jack
 * 
 */
public class XmlDomVisitor extends VisitorSupport {
	public static String ATTRIBUTES_FIELD = "_attributes";
	public static String CHILD_NODES_FIELD = "_childNodes";

	private boolean dedicatedAttributeField;
	private boolean dedicatedChildNodeField;
	/**
	 * the object that generate by de-serialization
	 */
	_Object object;
	/**
	 * used to save return value of each accept().
	 */
	Stack<_Object> stack = new Stack<_Object>();

	public XmlDomVisitor(boolean dedicatedAttributeField,
			boolean dedicatedChildNodeField) {
		this.dedicatedAttributeField = dedicatedAttributeField;
		this.dedicatedChildNodeField = dedicatedChildNodeField;
	}

	@Override
	public void visit(Document document) {

		visit(document.getRootElement());

		object = pop();
	}

	public void visit(Node node) {
		int type = node.getNodeType();
		switch (type) {
		case Node.ELEMENT_NODE:
			visit((Element) node);
			break;
		case Node.TEXT_NODE:
			visit((Text) node);
			break;

		case Node.CDATA_SECTION_NODE:
			visit((CDATA) node);
			break;

		default:
			System.out.println("not supported " + node.getText());
			push(null);
		}
	}

	void push(_Object obj) {
		stack.push(obj);
	}

	_Object pop() {

		_Object obj = stack.pop();

		return obj;
	}

	@Override
	public void visit(Element node) {
		Element element = node;

		_Object obj = getObjectRepo().createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);
		String name = element.getName();
		obj.setName(name);

		if (element.attributeCount() > 0) {
			_Object attrOwner = obj;
			if (dedicatedAttributeField) {
				_Object attributes = getObjectRepo().createObject(
						ObjectType.NORMAL, ObjectScope.ExecutionContext);

				attributes.setName(ATTRIBUTES_FIELD);
				obj.setField(ATTRIBUTES_FIELD, attributes, true);

				attrOwner = attributes;
			}


			for (int i = 0; i < element.attributeCount(); i++) {
				Attribute attr = element.attribute(i);
				visit(attr);

				_Object attrObj = pop();
				attrOwner.setField(attr.getName(), attrObj, true);
			}

		}

		// process child nodes
		if (element.nodeCount() > 0) {
			_Object childOwner = obj;
			if (dedicatedChildNodeField) {
				_Object childNodes = getObjectRepo().createObject(
						ObjectType.NORMAL, ObjectScope.ExecutionContext);

				childNodes.setName(CHILD_NODES_FIELD);
				obj.setField(CHILD_NODES_FIELD, childNodes, true);
				childOwner = childNodes;
			}

			for (int i = 0, size = element.nodeCount(); i < size; i++) {
				Node cnode = element.node(i);
				visit(cnode);
				_Object childObj = pop();
				if (childObj != null)
					childOwner.setField(childObj.getName(), childObj, true);
			}

		}

		// element only contains one cdata or text node, replace child with
		// current one

		if (obj.getChildCount() == 1) {
			_Object childObj = obj.getChild(0);
			if (childObj instanceof StringValue) {

				obj = obj.getChild(0);
				obj.setName(name);
			}
		}

		// return value
		push(obj);
	}

	@Override
	public void visit(CDATA node) {
		StringValue obj = (StringValue) getObjectRepo().createObject(
				ObjectType.STRING, ObjectScope.ExecutionContext);
		String name = node.getNodeTypeName();
		obj.setName(name);
		obj.setValue(node.getText());

		push(obj);
	}

	@Override
	public void visit(Text node) {
		Text text = node;

		StringValue obj = (StringValue) getObjectRepo().createObject(
				ObjectType.STRING, ObjectScope.ExecutionContext);
		String name = text.getNodeTypeName();
		obj.setName(name);
		obj.setValue(text.getText());

		push(obj);
	}

	@Override
	public void visit(Attribute node) {
		/*
		 * _Object attrParent = obj; serialize
		 * 
		 * for (int i = 0; i < element.attributeCount(); i++) { Attribute attr =
		 * element.attribute(i);
		 * 
		 * _Object attribute = getObjectRepo().createObject( ObjectType.STRING,
		 * ObjectScope.ExecutionContext); attribute.setValue(attr.getValue());
		 * 
		 * attrParent.setField(attr.getName(), attribute, true); }
		 */
	}

	public _Object getObject() {
		return object;
	}

	private ObjectRepository getObjectRepo() {
		ObjectRepository repo = VirtualMachine.getInstance()
				.getObjectRepository();
		return repo;
	}
}
