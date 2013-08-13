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

			return new _Object(id);
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
			return new IntegerValue(id);
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

}
