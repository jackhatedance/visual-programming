package com.bluesky.visualprogramming.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
/**
 * create this class to encapsulate fields and indexes. so that they changes  are synchronization.
 *  
 * @author jackding
 *
 */
public class ObjectFields {
	public List<Field> fieldList = new ArrayList<Field>();
	public int systemFieldsCount = 0;
	// index names to accelerate access speed
	public Map<String, Integer> fieldNameMap;
	public ObjectFields(List<Field> fieldList,int systemFieldsCount,Map<String, Integer> fieldNameMap) {
		this.fieldList=fieldList;
		this.systemFieldsCount=systemFieldsCount;
		this.fieldNameMap = fieldNameMap;
		
		if(fieldNameMap==null)
			rebuildFieldIndexes();
	}
	
	public ObjectFields(List<Field> fieldList,int systemFieldsCount) {
		this.fieldList=fieldList;
		this.systemFieldsCount=systemFieldsCount;
		this.fieldNameMap = null;
	}
	/**
	 * used when need to change fields. it will copy a field list by default, but index is null.
	 * @return
	 */
	public ObjectFields makeACopy(){
		//clone list
		List<Field> newFieldList = new ArrayList<Field>();
		newFieldList.addAll(fieldList);
		
		
		//clone index
		Map<String,Integer> newFieldNameMap=null;
		if(fieldNameMap!=null){
			newFieldNameMap = new HashMap<String, Integer>();
			newFieldNameMap.putAll(fieldNameMap);
		}
		
		ObjectFields fields = new ObjectFields(newFieldList, systemFieldsCount, newFieldNameMap);
		
		return fields;
	}
	
	protected void rebuildFieldIndexes() {
		
		fieldNameMap = new HashMap<String, Integer>();

		for (int i = 0; i < fieldList.size(); i++) {
			Field f = fieldList.get(i);
			fieldNameMap.put(f.name, i);
		}		 
	}
	
	public Field removeField(String name) {
		Integer childIndex = fieldNameMap.get(name);
		if (childIndex == null)
			throw new RuntimeException("child not found:" + name);

		return removeField(childIndex);
	}
	
	protected Field removeField(Integer index) {	
//		
//		 
//		if (index == null)
//			return null;

		int idx = index.intValue();
				
		Field field = fieldList.get(idx);
		fieldList.remove(idx);
		
		rebuildFieldIndexes();

		
		return field;
	}	

	public boolean isEmpty() {		
		return fieldList.isEmpty();
	}
	
	public  Field getField(String name) {
		Integer index = fieldNameMap.get(name);
		if (index != null)
			return fieldList.get(index);
		else
			return null;
	}
	
	public Field getField(int index) {		
		return fieldList.get(index);
	}
	
	public String[] getFieldNames() {

		List<String> names = new ArrayList<String>();
		for (Field field : fieldList) {
			if (!field.isSystemField())
				names.add(field.name);
		}

		return names.toArray(new String[0]);

	}
	
	public void sortFields() {
		List<Field> systemFields = new ArrayList<Field>();

		List<Field> userFields = new ArrayList<Field>();

		for (Field f : fieldList) {
			if (f.isSystemField())
				systemFields.add(f);
			else
				userFields.add(f);
		}

		fieldList.clear();

		fieldList.addAll(systemFields);
		fieldList.addAll(userFields);

		systemFieldsCount = systemFields.size();

		rebuildFieldIndexes();
	}
	
	public  void sortFields(Comparator<Field> comparator) {

		List<Field> systemFields = new ArrayList<Field>();

		TreeSet<Field> userFields = new TreeSet<Field>(comparator);

		for (Field f : fieldList) {
			if (f.isSystemField())
				systemFields.add(f);
			else
				userFields.add(f);
		}

		fieldList.clear();

		fieldList.addAll(systemFields);
		fieldList.addAll(userFields);

		systemFieldsCount = systemFields.size();

		rebuildFieldIndexes();

	}
	
	public    int getUserFieldsCount() {
	
		return fieldList.size() - systemFieldsCount;

	}
	
	public Map<String, Integer> getFieldNameMap() {
		return fieldNameMap;
	}

	public void addField(Field field, _Object target) {
			
		fieldList.add(field);
	//	field.owner = this;
		field.setTarget(target);
		sortFields();

	}
	
}
