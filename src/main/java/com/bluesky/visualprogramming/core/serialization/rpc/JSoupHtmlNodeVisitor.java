package com.bluesky.visualprogramming.core.serialization.rpc;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import com.bluesky.visualprogramming.core.ObjectRepository;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.Prototypes;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.list.ListObject;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class JSoupHtmlNodeVisitor implements NodeVisitor {
	static String CHILD_NODES = "childNodes";

	static String[] LEAF_NODE_TYPES = { "#text", "#data" };

	Set<String> leafNodeTypes = new HashSet<String>();

	private _Object root;
	private Stack<_Object> stack = new Stack<_Object>();

	VirtualMachine vm;
	ObjectRepository repo;

	public JSoupHtmlNodeVisitor() {
		vm = VirtualMachine.getInstance();
		repo = vm.getObjectRepository();

		for (String type : LEAF_NODE_TYPES)
			leafNodeTypes.add(type);

	}

	private boolean isLeafNode(String nodeType) {
		return leafNodeTypes.contains(nodeType);
	}

	@Override
	public void head(Node node, int depth) {
		// when entering a node, we create an object
		// System.out.println("<" + node.nodeName() + ">");

		if (node instanceof Document) {
			_Object obj = repo.createObject(ObjectType.NORMAL,
					ObjectScope.ExecutionContext);

			_Object childList = Prototypes.List.createInstance();

			obj.setField(CHILD_NODES, childList, true);
			root = obj;
			stack.push(childList);
		} else {
			_Object parent = stack.peek();
			ListObject parentObj = new ListObject(parent);
			_Object obj = null;
			if (node instanceof Element) {
				// System.out.println("element");
				_Object self = repo.createObject(ObjectType.NORMAL,
						ObjectScope.ExecutionContext);

				StringValue typeObj = (StringValue) repo.createObject(
						ObjectType.STRING, ObjectScope.ExecutionContext);
				typeObj.setValue(node.nodeName());
				self.setField("type", typeObj, true);

				_Object childList = Prototypes.List.createInstance();
				self.setField(CHILD_NODES, childList, true);

				addChildObject(parentObj, self);

				obj = childList;
			} else if (node instanceof Comment) {
				Comment comment = (Comment) node;
				obj = createLeafNodeObject(node.nodeName(), comment.getData());
				addChildObject(parentObj, obj);
			} else if (node instanceof TextNode) {
				TextNode text = (TextNode) node;
				String textValue = text.text();
				if (!textValue.trim().isEmpty()) {
					obj = createLeafNodeObject(node.nodeName(), text.text());
					addChildObject(parentObj, obj);
				} else
					obj = null;

			} else if (node instanceof DataNode) {
				DataNode data = (DataNode) node;
				obj = createLeafNodeObject(node.nodeName(), data.getWholeData());
				parentObj.add(obj);
			} else if (node instanceof DocumentType) {
				obj = createLeafNodeObject(node.nodeName(), "DocumentType");

				addChildObject(parentObj, obj);
			}

			stack.push(obj);
		}
	}

	/**
	 * parent could be null when a node is intend to be skipped.
	 * 
	 * @param parent
	 * @param child
	 */
	private void addChildObject(ListObject parent, _Object child) {
		if (parent != null)
			parent.add(child);
	}

	private _Object createLeafNodeObject(String nodeType, String value) {
		_Object self = repo.createObject(ObjectType.NORMAL,
				ObjectScope.ExecutionContext);

		StringValue typeObj = (StringValue) repo.createObject(
				ObjectType.STRING, ObjectScope.ExecutionContext);
		typeObj.setValue(nodeType);
		self.setField("type", typeObj, true);

		_Object valueObj = repo.createObject(ObjectType.STRING,
				ObjectScope.ExecutionContext);
		valueObj.setValue(value);
		self.setField("value", valueObj, true);

		return self;
	}

	protected void processAttributes() {

	}

	@Override
	public void tail(Node node, int depth) {
		// when leaving a node, we finish create children
		_Object obj = stack.pop();

		if (node instanceof Element) {
			// if a list contains only one leaf node(text, data,etc). then
			// convert it to a leaf
			// node.
			ListObject listObject = new ListObject(obj);
			if (listObject.size() == 1) {
				_Object firstElement = listObject.get(0);
				_Object type = firstElement.getChild("type");
				if (type != null && type instanceof StringValue
						&& isLeafNode(((StringValue) type).getValue())) {

					_Object owner = obj.getOwner();
					owner.removeField(CHILD_NODES);

					owner.setField("value", firstElement.getChild("value"),
							true);
				}
			}

			// eliminate empty list
			if (listObject.size() == 0) {
				obj.getOwner().removeField(CHILD_NODES);
			}

		} else if (node instanceof TextNode) {

		}
	}

	public _Object getObject() {
		return root;
	}

}
