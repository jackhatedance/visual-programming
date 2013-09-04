package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.core.message.ObjectTreeVisitor;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public enum ObjectType {
	NORMAL {
		@Override
		public _Object create(long id) {
			_Object obj = new _Object(id);
			return obj;
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {

			visitor.visitObject(object);
		}
	},
	LINK {
		@Override
		public _Object create(long id) {
			return new Link(id);
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitLink((Link) object);
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

			return "root.prototype.value.integer";
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitInteger((IntegerValue) object);
		}

		@Override
		public boolean isValueObject() {

			return true;
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

			return "root.prototype.value._float";
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitFloat((FloatValue) object);

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

			return "root.prototype.value.time";
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitTime((TimeValue) object);

		}

		@Override
		public boolean isValueObject() {

			return true;
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

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitBoolean((BooleanValue) object);

		}

		@Override
		public boolean isValueObject() {

			return true;
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
			String str = literal.substring(1, literal.length() - 1);
			return str;
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitString((StringValue) object);

		}

		@Override
		public boolean isValueObject() {

			return true;
		}
	},
	PROCEDURE {
		@Override
		public _Object create(long id) {
			return new Procedure(id);
		}

		@Override
		public void visit(ObjectTreeVisitor visitor, _Object object) {
			visitor.visitProcedure((Procedure) object);

		}
	};

	abstract public _Object create(long id);

	abstract public void visit(ObjectTreeVisitor visitor, _Object object);

	public boolean isValueObject() {
		return false;
	}

	public String getPrototypeEL() {
		return null;
	}

	public String extractValue(String literal) {
		return literal;
	}

}
