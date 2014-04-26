package com.bluesky.visualprogramming.core.nativeImpl.prototype;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeClassSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class MapProto extends NativeClassSupport{
	static String MapItemPrefix = "KEY_";

	 

	
	@ParameterList({"self","key","value"})
	public static void put(_Object self, StringValue key, _Object value) {
		
		String strkey = key.getValue();
		// take owner ship
		if (value.hasOwner())
			value.downgradeLinkFromOwner();

		self.setField(getItemName(strkey), value, true);

	}
	
	@ParameterList({"self","key"})
	public static _Object get(_Object self, StringValue key) {
		String strKey = key.getValue();
		return self.getChild(getItemName(strKey));

	}
	
	@ParameterList({"self","key"})
	public static  boolean contains(_Object self, StringValue key) {
		String strKey = key.getValue();
		_Object value = self.getChild(getItemName(strKey));
		return value != null;

	}

	protected static String getItemName(String key) {
		return MapItemPrefix + key;
	}

	@ParameterList({"self","key"})
	public static void remove(_Object self, StringValue key) {
		String strKey = key.getValue();
		self.removeField(getItemName(strKey));
	}
	
	@ParameterList({"self"})
	public static void clear(_Object self) {

		List<Field> listElements = new ArrayList<Field>();

		for (Field f : self.getFields()) {
			if (f.getName().startsWith(MapItemPrefix))
				listElements.add(f);
		}

		for (Field f : listElements)
			self.removeField(f.name);

	}
	
	@ParameterList({"self"})
	public static _Object size(_Object self) {

		int size = self.getUserFieldsCount();
 
		IntegerValue result = getObjectFactory().createInteger(size);
		 
		return result;
	}

	private static String getKey(String itemName) {
		return itemName.substring(MapItemPrefix.length());
	}
	@ParameterList({"self"})
	public static List<String> getKeyList(_Object self) {
		List<String> keys = new ArrayList<String>();
		for (Field f : self.getFields()) {
			if (f.getName().startsWith(MapItemPrefix))
				keys.add(getKey(f.name));
		}
		return keys;
	}
}
