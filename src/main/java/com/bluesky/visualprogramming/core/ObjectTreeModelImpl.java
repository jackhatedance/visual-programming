package com.bluesky.visualprogramming.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTreeModelImpl implements ObjectTreeModel {
	private Map<_Object, List<ObjectListener>> listnerMap = new HashMap<_Object, List<ObjectListener>>();

	@Override
	public void setField(_Object obj, String name, _Object child, boolean own) {
		obj.setField(name, child, own);
	}

	@Override
	public Field getField(_Object obj, String name) {
		return obj.getField(name);
	}

	@Override
	public void removeField(_Object obj, String name) {
		List<ObjectListener> listeners= getListeners(obj);
		if(listeners!=null && !listeners.isEmpty()){
			for(ObjectListener ol : listeners)
				ol.beforeRemoveField(obj, name);
		}
		
		obj.removeField(name);

		
			
	}

	@Override
	public void addListener(_Object target, ObjectListener listener) {
		List<ObjectListener> list = listnerMap.get(target);
		if (list == null) {
			list = new ArrayList<ObjectListener>();
			listnerMap.put(target, list);
		}
		list.add(listener);
	}
	
	private List<ObjectListener> getListeners(_Object target){
		return listnerMap.get(target);
	}

	@Override
	public void removeListener(_Object target, ObjectListener listener) {
		List<ObjectListener> list = listnerMap.get(target);
		if (list == null) {
			throw new RuntimeException("listener does not exsit");
		} else {
			list.remove(listener);
		}

	}

}
