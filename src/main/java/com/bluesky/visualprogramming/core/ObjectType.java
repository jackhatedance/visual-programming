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
		@Override
		public String getPrototypeEL() {
			
			return "root.prototype.value.boolean";
		}
	},
	STRING {
		@Override
		public _Object create(long id) {
			return new StringValue(id);
		}
		@Override
		public String getPrototypeEL() {
			
			return "root.prototype.value.string";
		}
		@Override
		public String extractValue(String literal) {
			String str = literal.substring(1,literal.length()-1);
			return str;
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
	public String extractValue(String literal){
		return literal;
	}

}
