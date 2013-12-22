package com.bluesky.visualprogramming.core.nativeproc.list;

import java.util.ArrayList;
import java.util.List;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectScope;
import com.bluesky.visualprogramming.core.ObjectType;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.vm.VirtualMachine;

public class ListObject {
	static String ListItemPrefix= "LI"; 

	private _Object obj;

	public ListObject(_Object obj) {
		this.obj = obj;
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
		int size = obj.getUserFieldsCount();
		
		String fieldName = String.format("%s%d",ListItemPrefix,index);

		return obj.getChild(fieldName);

	}

	private String getItemName(int index){
		return String.format("%s%d",ListItemPrefix,index);
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

	public _Object size() {

		int size = obj.getUserFieldsCount();

		VirtualMachine vm = VirtualMachine.getInstance();

		IntegerValue result = (IntegerValue) vm.getObjectRepository()
				.createObject(ObjectType.INTEGER, ObjectScope.ExecutionContext);
		result.setIntValue(size);

		return result;
	}
}
