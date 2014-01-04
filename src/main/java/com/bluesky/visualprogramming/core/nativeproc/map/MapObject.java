package com.bluesky.visualprogramming.core.nativeproc.map;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class MapObject {
	static String MapItemPrefix = "KEY_";

	private _Object obj;

	public MapObject(_Object obj) {
		this.obj = obj;
	}

	public void put(String key, _Object value) {

		obj.setField(getItemName(key), value, true);

	}

	public _Object get(String key) {

		return obj.getChild(getItemName(key));

	}

	public boolean contains(String key) {

		_Object value = obj.getChild(getItemName(key));
		return value != null;

	}

	private String getItemName(String key) {
		return MapItemPrefix + key;
	}

	public void remove(String key) {

		obj.removeField(getItemName(key));
	}

	public void clear() {

		List<Field> listElements = new ArrayList<Field>();

		for (Field f : obj.getFields()) {
			if (f.getName().startsWith(MapItemPrefix))
				listElements.add(f);
		}

		for (Field f : listElements)
			obj.removeField(f.name);

	}

	public _Object size() {

		int size = obj.getUserFieldsCount();

		VirtualMachine vm = VirtualMachine.getInstance();

		IntegerValue result = (IntegerValue) vm.getObjectRepository()
				.createObject(ObjectType.INTEGER, ObjectScope.ExecutionContext);
		result.setIntValue(size);

		return result;
	}

	private String getKey(String itemName) {
		return itemName.substring(MapItemPrefix.length());
	}

	public List<String> getKeyList() {
		List<String> keys = new ArrayList<String>();
		for (Field f : obj.getFields()) {
			if (f.getName().startsWith(MapItemPrefix))
				keys.add(getKey(f.name));
		}
		return keys;
	}
}
