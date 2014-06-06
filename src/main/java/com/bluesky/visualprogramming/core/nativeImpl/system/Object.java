package com.bluesky.visualprogramming.core.nativeImpl.system;

import com.bluesky.visualprogramming.core.ExtendedObjectFactory;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core.Prototype;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeImpl.proto.ListProto;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;

public class Object extends NativeMethodSupport {

	@ParameterList({ "obj", "path" })
	public static _Object getByPath(_Object obj, StringValue path) {

		_Object value = obj.getObjectByPath(path.getValue());

		return value;
	}

	@ParameterList({ "obj", "name" })
	public static _Object getChild(_Object obj, StringValue name) {

		if (obj == null)
			throw new RuntimeException("'obj' is null");
		if (name == null)
			throw new RuntimeException("'name' is null");

		_Object value = obj.getChild(name.getValue());

		return value;
	}

	@ParameterList({ "obj" })
	public static _Object getId(_Object obj) {

		IntegerValue iv = getObjectFactory().createInteger(obj.getId());

		return iv;
	}

	@ParameterList({ "obj" })
	public static _Object getPath(_Object obj) {

		StringValue path = (StringValue) getRepo().createObject(
				ObjectType.STRING, ObjectScope.ExecutionContext);

		if (obj != null)
			path.setValue(obj.getPath());
		else
			path.setValue("null");

		return path;
	}

	@ParameterList({ "obj" })
	public static _Object getType(_Object obj) {

		StringValue sv = getObjectFactory().createString(obj.getType().name());

		return sv;
	}

	@ParameterList({ "obj" })
	public static _Object listField(_Object obj) {

		String[] fieldNames = obj.getFieldNames();

		_Object list = ExtendedObjectFactory.create(Prototype.List);

		for (String field : fieldNames) {
			StringValue fieldNameSV = getObjectFactory().createString(field);

			ListProto.add(list, fieldNameSV);
		}

		return list;
	}

	@ParameterList({ "srcObj", "srcFieldName", "dstObj", "dstFieldName" })
	public static void move(_Object srcObj, StringValue srcFieldName,
			_Object dstObj, StringValue dstFieldName) {

		if (dstFieldName == null)
			dstFieldName = srcFieldName;// same name

		_Object childObj = srcObj.getChild(srcFieldName.getValue());
		srcObj.removeField(srcFieldName.getValue());

		dstObj.setField(dstFieldName.getValue(), childObj, true);

	}

	@ParameterList({ "address" })
	public static _Object newLink(StringValue address) {

		Link link = getObjectFactory().createLink(address.getValue());
		if (link.isValid())
			return link;
		else
			return null;
	}

	@ParameterList({})
	public static _Object newList() {

		_Object list = ExtendedObjectFactory.create(Prototype.List);

		return list;
	}

	@ParameterList({})
	public static _Object newMap() {

		_Object map = ExtendedObjectFactory.create(Prototype.Map);

		return map;
	}

	@ParameterList({ "obj", "fieldName" })
	public static void ownChild(_Object obj, StringValue fieldName) {

		if (obj != null) {
			_Object child = obj.getChild(fieldName.getValue());
			if (child != null) {
				child.attachToOwner(obj, fieldName.getValue());
			}
		}

	}

}
