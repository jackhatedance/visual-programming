package com.bluesky.visualprogramming.core.serialization;

import java.util.Stack;

import org.apache.log4j.Logger;
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
import com.bluesky.visualprogramming.core.Prototypes;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.list.ListObject;
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
	static Logger logger = Logger.getLogger(XmlDomVisitor.class);

	public static String ATTRIBUTES_FIELD = "attributes";
	public static String CHILDREN_FIELD = "children";

	private boolean dedicatedAttributeField;
	private boolean dedicatedChildrenField;
	private ChildNodeConvensionType childNodeConvensionType;
	private boolean skipEmptyTextNode;
	/**
	 * the object that generate by de-serialization
	 */
	_Object object;
	/**
	 * used to save return value of each accept().
	 */
	Stack<_Object> stack = new Stack<_Object>();

	public XmlDomVisitor(boolean dedicatedAttributeField,
			boolean dedicatedChildNodeField,
			ChildNodeConvensionType childNodeConvensionType,
			boolean skipEmptyTextNode) {
		this.dedicatedAttributeField = dedicatedAttributeField;
		this.dedicatedChildrenField = dedicatedChildNodeField;
		this.childNodeConvensionType = childNodeConvensionType;
		this.skipEmptyTextNode = skipEmptyTextNode;
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

	private boolean isCompositeTextElement(Element element) {
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node cnode = element.node(i);
			if (cnode.getNodeType() != Node.TEXT_NODE
					&& cnode.getNodeType() != Node.CDATA_SECTION_NODE)
				return false;
		}
		return true;
	}

	@Override
	public void visit(Element node) {
		Element element = node;

		_Object result = null;

		/**
		 * if all child node is not element but Text or CData, get text
		 * directly.
		 */
		boolean isCompositeTextElement = isCompositeTextElement(element);
		if (isCompositeTextElement) {

			StringValue strObj = (StringValue) getObjectRepo().createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);
			String name = element.getName();
			strObj.setName(name);
			strObj.setValue(element.getStringValue());

			result = strObj;
		} else {

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

				_Object childNodes = null;
				if (childNodeConvensionType == ChildNodeConvensionType.Field) {
					if (dedicatedChildrenField) {
						childNodes = getObjectRepo()
								.createObject(ObjectType.NORMAL,
										ObjectScope.ExecutionContext);

						childNodes.setName(CHILDREN_FIELD);
						obj.setField(CHILDREN_FIELD, childNodes, true);
						childOwner = childNodes;
					}

					// calculate duplicated tags
					CountMap tagCount = new CountMap();
					for (int i = 0, size = element.nodeCount(); i < size; i++) {
						Node cnode = element.node(i);
						tagCount.inc(cnode.getName());
					}

					for (int i = 0, size = element.nodeCount(); i < size; i++) {
						Node cnode = element.node(i);
						visit(cnode);
						_Object childObj = pop();

						if (childObj != null) {
							if (tagCount.get(childObj.getName()) > 1) {
								/*
								 * create list object if have not
								 */
								String listObjectName = childObj.getName()
										+ "s";
								_Object list = childOwner
										.getChild(listObjectName);
								if (list == null) {
									list = Prototypes.List.createInstance();
									childOwner.setField(listObjectName, list,
											true);
								}
								// add the element to list
								ListObject listObj = new ListObject(list);
								listObj.add(childObj);
							} else {
								childOwner.setField(childObj.getName(),
										childObj, true);
							}
						}
					}

				} else {
					/*
					 * list in the case, a dedicated field is must.
					 */
					childNodes = Prototypes.List.createInstance();
					ListObject childNodeList = new ListObject(childNodes);

					childNodes.setName(CHILDREN_FIELD);
					obj.setField(CHILDREN_FIELD, childNodes, true);
					childOwner = childNodes;

					for (int i = 0, size = element.nodeCount(); i < size; i++) {
						Node cnode = element.node(i);
						visit(cnode);
						_Object childObj = pop();
						if (childObj != null)
							childNodeList.add(childObj);
					}
				}

			}

			result = obj;
		}

		// return value
		push(result);
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

		if (skipEmptyTextNode && text.getText().trim().isEmpty())
			push(null);
		else {

			StringValue obj = (StringValue) getObjectRepo().createObject(
					ObjectType.STRING, ObjectScope.ExecutionContext);
			String name = text.getNodeTypeName();
			obj.setName(name);
			obj.setValue(text.getText());

			push(obj);
		}
	}

	@Override
	public void visit(Attribute node) {

		_Object attribute = getObjectRepo().createObject(ObjectType.STRING,
				ObjectScope.ExecutionContext);
		attribute.setValue(node.getValue());

		attribute.setName(node.getName());

		push(attribute);
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
