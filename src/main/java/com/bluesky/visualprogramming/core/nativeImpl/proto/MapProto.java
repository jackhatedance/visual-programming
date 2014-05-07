package com.bluesky.visualprogramming.core.nativeImpl.proto;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.visualprogramming.core.ExtendedObjectFactory;
import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.Prototype;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;

public class MapProto extends NativeMethodSupport {
	static String MapItemPrefix = "KEY_";

	@ParameterList({ "self", "key", "value" })
	public static void put(_Object self, StringValue key, _Object value) {

		String strkey = key.getValue();
		// take owner ship
		if (value.hasOwner())
			value.downgradeLinkFromOwner();

		self.setField(getItemName(strkey), value, true);

	}

	@ParameterList({ "self", "key" })
	public static _Object get(_Object self, StringValue key) {
		String strKey = key.getValue();
		return self.getChild(getItemName(strKey));

	}

	@ParameterList({ "self", "key" })
	public static BooleanValue contains(_Object self, StringValue key) {
		String strKey = key.getValue();
		_Object value = self.getChild(getItemName(strKey));

		BooleanValue result = getObjectFactory().createBoolean(value != null);
		return result;

	}

	protected static String getItemName(String key) {
		return MapItemPrefix + key;
	}

	@ParameterList({ "self", "key" })
	public static void remove(_Object self, StringValue key) {
		String strKey = key.getValue();
		self.removeField(getItemName(strKey));
	}

	@ParameterList({ "self" })
	public static void clear(_Object self) {

		List<Field> listElements = new ArrayList<Field>();

		for (Field f : self.getFields()) {
			if (f.getName().startsWith(MapItemPrefix))
				listElements.add(f);
		}

		for (Field f : listElements)
			self.removeField(f.name);

	}

	@ParameterList({ "self" })
	public static _Object size(_Object self) {

		int size = self.getUserFieldsCount();

		IntegerValue result = getObjectFactory().createInteger(size);

		return result;
	}

	private static String getKey(String itemName) {
		return itemName.substring(MapItemPrefix.length());
	}

	@ParameterList({ "self" })
	public static _Object getKeyList(_Object self) {

		_Object listObject = ExtendedObjectFactory.create(Prototype.List);

		for (Field f : self.getFields()) {
			if (f.getName().startsWith(MapItemPrefix)) {
				String key = getKey(f.name);

				StringValue keySV = getObjectFactory().createString(key);
				ListProto.add(listObject, keySV);
			}
		}

		return listObject;
	}
}
