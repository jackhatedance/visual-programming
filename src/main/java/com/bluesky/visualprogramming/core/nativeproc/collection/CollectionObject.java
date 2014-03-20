package com.bluesky.visualprogramming.core.nativeproc.collection;

import java.util.Comparator;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core._Object;

/**
 * collection is an object that let user put same kind of objects, names are not
 * matter, it is a container. it is like a table of DB.
 * 
 * the only feature is it support sort.
 * 
 * for example, we put blog posts into a collection object. then we set
 * configure info, let it order by createTime and by ASC.
 * 
 * in the internal, it relies on fieldList of _Object class. system fields in
 * front, then user fields.
 * 
 * the difference between list and collection is that the list object is not
 * flexible as collection. in list, we have use its APIs, but in collection, we
 * can add/remove any object without worrying keep the element names in certain
 * style, such as "LIxxx". because coolection's order is not rely on element
 * name, but internal Field List.
 * 
 * 
 * 
 * @author jack
 * 
 */
public class CollectionObject {
	private _Object obj;

	public CollectionObject(_Object obj) {
		this.obj = obj;
	}

	public void sort(String field, SortOrder order) {

		// prepare comparator
		Comparator<Field> comparator = new FieldComparator(field, order);


		obj.sortFields(comparator);
	}
}
