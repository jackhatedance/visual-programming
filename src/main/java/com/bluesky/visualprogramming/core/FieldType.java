package com.bluesky.visualprogramming.core;

public enum FieldType {
	/**
	 * strong field, a.k.a. strong reference. the target is owned by the owner
	 * of the field.
	 */
	STRONG {
		@Override
		public _Object getTarget(Field f) {
			return f.getStrongTarget();
		}
		
		@Override
		public void setTarget(Field field, _Object target) {
			field.setStrongTarget(target);			
		}
	},
	/**
	 * weak field. the target don't owned by the owner.
	 */
	WEAK {
		@Override
		public _Object getTarget(Field f) {
			if(f.getWeakTarget()!=null)
				return f.getWeakTarget().get();
			
			return null;
		}
		
		@Override
		public void setTarget(Field field, _Object target) {
			field.setWeakTarget(target);			
		}
	};

	public abstract _Object getTarget(Field f);

	public abstract void setTarget(Field field, _Object target);
}
