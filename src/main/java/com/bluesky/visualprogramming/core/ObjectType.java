package com.bluesky.visualprogramming.core;

import java.util.Comparator;

import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public enum ObjectType {
	
	
	NORMAL {
		@Override
		public _Object create(long id) {
			_Object obj = new _Object(id);
			return obj;
		}

		
	},
	LINK {
		@Override
		public _Object create(long id) {
			return new Link(id);
		}

		@Override
		public String getSvgResource(){
			return "svg/link.svg";
		}
	},
	INTEGER {
		@Override
		public _Object create(long id) {
			IntegerValue intValue = new IntegerValue(id);
			// set prototype
			return intValue;
		}

		@Override
		public String getPrototypeEL() {

			return ObjectRepository.PROTOTYPE_PATH+".value.integer";
		}

		
		@Override
		public boolean isValueObject() {

			return true;
		}
		
		@Override
		public String getSvgResource(){
			return "svg/integer.svg";
		}

		@Override
		public Comparator<_Object> getComparator() {
			return new Comparator<_Object>() {
				public int compare(_Object o1, _Object o2) {
					IntegerValue iv1 = (IntegerValue) o1;
					IntegerValue iv2 = (IntegerValue) o2;
					return (int) (iv1.getIntValue() - iv2.getIntValue());
				};
			};

		}
	},
	FLOAT {
		@Override
		public _Object create(long id) {
			FloatValue floatValue = new FloatValue(id);
			// set prototype
			return floatValue;
		}

		@Override
		public String getPrototypeEL() {

			return ObjectRepository.PROTOTYPE_PATH+".value._float";
		}

		
		@Override
		public boolean isValueObject() {

			return true;
		}
	},
	TIME {
		@Override
		public _Object create(long id) {
			TimeValue timeValue = new TimeValue(id);
			// set prototype
			return timeValue;
		}

		@Override
		public String getPrototypeEL() {

			return ObjectRepository.PROTOTYPE_PATH+".value.time";
		}

		@Override
		public boolean isValueObject() {

			return true;
		}
		
		@Override
		public String getSvgResource(){
			return "svg/time.svg";
		}
	},
	BOOLEAN {
		@Override
		public _Object create(long id) {
			return new BooleanValue(id);
		}

		@Override
		public String getPrototypeEL() {

			return ObjectRepository.PROTOTYPE_PATH+".value.boolean";
		}

		@Override
		public boolean isValueObject() {

			return true;
		}
		
		@Override
		public String getSvgResource(){
			return "svg/boolean.svg";
		}
	},
	STRING {
		@Override
		public _Object create(long id) {
			return new StringValue(id);
		}

		@Override
		public String getPrototypeEL() {

			return ObjectRepository.PROTOTYPE_PATH+".value.string";
		}

		@Override
		public boolean isValueObject() {

			return true;
		}
		
		@Override
		public String getSvgResource(){
			return "svg/string.svg";
		}

		@Override
		public Comparator<_Object> getComparator() {
			return new Comparator<_Object>() {
				public int compare(_Object o1, _Object o2) {
					StringValue iv1 = (StringValue) o1;
					StringValue iv2 = (StringValue) o2;
					return iv1.getValue().compareTo(iv2.getValue());
				};
			};

		}
	},
	PROCEDURE {
		@Override
		public _Object create(long id) {
			return new Procedure(id);
		}

		@Override
		public String getSvgResource(){
			return "svg/procedure.svg";
		}
	},
	EXCEPTION {

		@Override
		public _Object create(long id) {

			return new VException(id);
		}

	};

	abstract public _Object create(long id);

	

	public boolean isValueObject() {
		return false;
	}

	public String getPrototypeEL() {
		return null;
	}
	

	public String getSvgResource(){
		return "svg/object.svg";
	}

	public Comparator<_Object> getComparator() {
		return new Comparator<_Object>() {
			@Override
			public int compare(_Object o1, _Object o2) {
				// default , nonsense
				if (o1 == null && o2 == null)
					return 0;
				else if (o1 != null && o2 == null)
					return 1;
				else if (o1 == null && o2 != null)
					return -1;
				else
					return o1.hashCode() - o2.hashCode();
			}
		};
	}
}
