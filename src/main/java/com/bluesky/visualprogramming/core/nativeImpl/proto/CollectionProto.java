package com.bluesky.visualprogramming.core.nativeImpl.proto;

import java.util.Comparator;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.SystemField;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.nativeproc.NativeMethodSupport;
import com.bluesky.visualprogramming.core.nativeproc.ParameterList;
import com.bluesky.visualprogramming.core.value.StringValue;

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
 * TODO
 * 
 * @author jack
 * 
 */
public class CollectionProto extends NativeMethodSupport {
	static String ItemPrefix = "CI";



	protected static void sort(_Object self, String field, SortOrder order) {

		// prepare comparator
		Comparator<Field> comparator = new FieldComparator(field, order);


		self.sortFields(comparator);
	}

	@ParameterList({ "self", })
	public static _Object sort(_Object self) {



		_Object config = self.getSystemTopChild(SystemField.Collection);

		String field = null;
		SortOrder order = SortOrder.Asc;

		// get config
		if (config != null) {
			StringValue fieldObj = (StringValue) config.getChild("field");
			if (fieldObj != null)
				field = fieldObj.getValue();

			StringValue orderObj = (StringValue) config.getChild("order");
			if (orderObj != null)
				order = SortOrder.valueOf(orderObj.getValue());
		}

		sort(self, field, order);

		return null;
	}
}
