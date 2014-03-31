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
		public void attachTarget(Field field, _Object target) {
			if (target == null)
				throw new RuntimeException("cannot attach to a null target");

			field.setStrongTarget(target);

			// update backward pointer
			target.setOwnerField(field);
		}

		@Override
		public void detachTarget(Field field) {
			_Object target = field.getStrongTarget();

			if (target == null)
				throw new RuntimeException("cannot detach a null target");

			target.removeOwnerField();

			field.setStrongTarget(null);
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
		public void attachTarget(Field field, _Object target) {
			field.setWeakTarget(target);

			if (target != null && target.getOwnerField() == field)
				throw new RuntimeException("weak field cannot own a target");
		}

		@Override
		public void detachTarget(Field field) {
			field.setWeakTarget(null);
		}
	};

	public abstract _Object getTarget(Field f);

	public abstract void attachTarget(Field field, _Object target);

	public abstract void detachTarget(Field field);

	public static FieldType getType(boolean owns) {
		return owns ? STRONG : WEAK;
	}


}
