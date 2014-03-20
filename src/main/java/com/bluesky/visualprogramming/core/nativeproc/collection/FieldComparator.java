package com.bluesky.visualprogramming.core.nativeproc.collection;

import java.util.Comparator;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core._Object;

public class FieldComparator implements Comparator<Field> {
	private String fieldName;
	private SortOrder order;
	public FieldComparator(String fieldName, SortOrder order) {
		this.fieldName = fieldName;
		this.order= order;		
	}
	
	public int compare(Field f1, Field f2) {
		_Object o1 = f1.getTarget();
		_Object o2 = f2.getTarget();
		
		_Object value1=null;
		_Object value2=null;
		
		if(o1!=null)
			value1 = o1.getChild(fieldName);
		
		if(o2!=null)
			value2 = o2.getChild(fieldName);

		int result;

		if (value1 == null && value2 == null)
			result = 0;
		else if (value1 != null && value2 == null)
			result = 1;
		else if (value1 == null && value2 != null)
			result = -1;
		else {

			if (value1.getType() != value2.getType())
				throw new RuntimeException(
						"cannot compare different type of objects.");
			else
				result = value1.getType().getComparator().compare(o1, o2);
		}

		if (order == SortOrder.Asc)
			result = -result;

		return result;

	}


}
