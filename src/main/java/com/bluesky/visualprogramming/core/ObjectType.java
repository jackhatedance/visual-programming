package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.core.link.HardLink;
import com.bluesky.visualprogramming.core.link.SoftLink;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;

public enum ObjectType {
	DEFAULT {
		@Override
		public _Object create(long id) {
			_Object obj = new _Object(id);
			return obj;
		}
	},
	SOFT_LINK {
		@Override
		public _Object create(long id) {
			return new SoftLink(id);
		}
	},
	HARD_LINK {
		@Override
		public _Object create(long id) {
			return new HardLink(id);
		}
	},
	INTEGER {
		@Override
		public _Object create(long id) {
			IntegerValue intValue =new IntegerValue(id);
			//set prototype
			return intValue;
		}
		
		@Override
		public String getPrototypeEL() {
			
			return "root.prototype.value.integer";
		}
		
	},
	BOOLEAN {
		@Override
		public _Object create(long id) {
			return new BooleanValue(id);
		}
	},
	STRING {
		@Override
		public _Object create(long id) {
			return new StringValue(id);
		}
	},
	PROCEDURE {
		@Override
		public _Object create(long id) {
			return new Procedure(id);
		}
	};

	abstract public _Object create(long id);
	public String getPrototypeEL(){
		return null;
	}

}
