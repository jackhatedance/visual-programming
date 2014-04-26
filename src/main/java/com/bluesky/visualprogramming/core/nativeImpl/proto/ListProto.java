package com.bluesky.visualprogramming.core.nativeImpl.proto;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeClassSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.IntegerValue;

public class ListProto extends NativeClassSupport {
	static String ListItemPrefix= "LI"; 

	@ParameterList({ "self", "element" })
	public static void add(_Object self, _Object element) {

		int size = self.getUserFieldsCount();
		String fieldName = String.format("%s%d",ListItemPrefix,size);
		self.setField(fieldName, element, true);

	}

	@ParameterList({ "self" })
	public static void clear(_Object self) {

		List<Field> listElements = new ArrayList<Field>();

		for (Field f : self.getFields()) {
			if(f.getName().startsWith(ListItemPrefix))
				listElements.add(f);
		}
		
		for(Field f : listElements)
			self.removeField(f.name);

	}

	@ParameterList({ "self", "index" })
	public static _Object get(_Object self, IntegerValue index) {

		String fieldName = String.format("%s%d", ListItemPrefix,
				index.getIntValue());

		_Object element =  self.getChild(fieldName);

		return element;
	}

	

	@ParameterList({ "self", "index", "element" })
	public static void insert(_Object self, IntegerValue index, _Object element) {

		int intIndex = (int) index.getIntValue();
		
		int size = self.getUserFieldsCount();
		
		if (intIndex > size + 1)
			throw new RuntimeException("index not exsit");
		
		//shift items to tail
		for (int i = size - 1; i >= intIndex; i--)
		{	 
			self.renameField(getItemName(i), getItemName(i + 1));
		}
		
		
		self.setField(getItemName(intIndex), element, true);
	}

	@ParameterList({ "self", "index", "element" })
	public static void replace(_Object self, IntegerValue index, _Object newItem) {
		int intIndex = (int) index.getIntValue();
		self.setField(getItemName(intIndex), newItem, true);
	}

	@ParameterList({ "self", "index" })
	public static _Object remove(_Object self, IntegerValue index) {
		int intIndex = (int) index.getIntValue();
		
		int size = (int) self.getUserFieldsCount();
		
		if (intIndex > size - 1)
			throw new RuntimeException("index not exsit");

		self.removeField(getItemName(intIndex));
		
		//shift items 
		for (int i = intIndex + 1; i < size; i++)
		{	 
			self.renameField(getItemName(i), getItemName(i - 1));
		}

		return null;
	}

	@ParameterList({ "self" })
	public static IntegerValue size(_Object self) {

		int size = self.getUserFieldsCount();

		IntegerValue result = getObjectFactory().createInteger(size);

		return result;

	}

	public static int indexOf(_Object item) {
		int listIndex = parseItemName(item.getOwnerField().name);
		return listIndex;
		
	}

	private static String getItemName(int index) {
		return String.format("%s%d", ListItemPrefix, index);
	}

	private static int parseItemName(String name) {
		int len = ListItemPrefix.length();
		String number = name.substring(len);

		return Integer.valueOf(number);
	}
}
