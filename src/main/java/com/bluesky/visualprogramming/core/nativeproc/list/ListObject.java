package com.bluesky.visualprogramming.core.nativeproc.list;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core._Object;

public class ListObject {
	static String ListItemPrefix= "LI"; 

	private _Object obj;

	public ListObject(_Object obj) {
		this.obj = obj;

		obj.sortFields();
	}

	public void add(_Object item) {

		int size = obj.getUserFieldsCount();
		String fieldName = String.format("%s%d",ListItemPrefix,size);
		obj.setField(fieldName, item, true);

	}

	public void clear() {


		List<Field> listElements = new ArrayList<Field>();

		for(Field f :obj.getFields()){
			if(f.getName().startsWith(ListItemPrefix))
				listElements.add(f);
		}
		
		for(Field f : listElements)
			obj.removeField(f.name);

	}

	public _Object get(int index) {
		String fieldName = String.format("%s%d",ListItemPrefix,index);

		return obj.getChild(fieldName);

	}

	private String getItemName(int index){
		return String.format("%s%d",ListItemPrefix,index);
	}

	private int parseItemName(String name) {
		int len = ListItemPrefix.length();
		String number = name.substring(len);

		return Integer.valueOf(number);
	}
	
	public _Object insert(int index, _Object item) {		
		
		int size = obj.getUserFieldsCount();
		
		if(index >size+1)
			throw new RuntimeException("index not exsit");
		
		//shift items to tail
		for(int i=size-1;i>=index;i--)
		{	 
			obj.renameField(getItemName(i), getItemName(i+1));
		}
		
		
		obj.setField(getItemName(index), item, true);

		return null;
	}

	public void replace(int index, _Object newItem) {
		obj.setField(getItemName(index), newItem, true);
	}

	public _Object remove(int index) {
		
		int size = obj.getUserFieldsCount();
		
		if(index >size-1)
			throw new RuntimeException("index not exsit");

		obj.removeField(getItemName(index));
		
		//shift items 
		for(int i=index+1;i<size;i++)
		{	 
			obj.renameField(getItemName(i), getItemName(i-1));
		}

		return null;
	}

	public int size() {

		int size = obj.getUserFieldsCount();

		return size;
	}

	public int indexOf(_Object item){		
		int listIndex = parseItemName(item.getOwnerField().name);
		return listIndex;
		
	}
}
