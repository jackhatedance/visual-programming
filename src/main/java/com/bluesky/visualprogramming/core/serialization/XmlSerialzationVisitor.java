package com.bluesky.visualprogramming.core.serialization;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.bluesky.visualprogramming.core.Field;
import com.bluesky.visualprogramming.core.ObjectVisitor;
import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.Link;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

public class XmlSerialzationVisitor implements ObjectVisitor {

	Stack<VisitorContext> stack = new Stack<VisitorContext>();

	boolean skipSystemField = true;

	// StringWriter writer = new StringWriter();
	Document document;

	public XmlSerialzationVisitor(boolean skipSystemField) {
		document = DocumentHelper.createDocument();

		this.skipSystemField = skipSystemField;
	}

	public void wrtieXml(Writer writer) throws IOException {
		document.write(writer);
	}

	protected void push(VisitorContext ctx) {
		stack.push(ctx);
	}

	public VisitorContext peek() {

		VisitorContext ctx = stack.peek();

		return ctx;
	}

	public VisitorContext pop() {

		VisitorContext ctx = stack.pop();
		return ctx;
	}

	/**
	 * entry method
	 * 
	 * @param obj
	 * @param name
	 */
	public void visit(_Object obj, String name) {
		Field field = new Field(name, false);
		field.setTarget(obj);

		Branch branch = document;

		VisitorContext childCtx = new VisitorContext();
		childCtx.setField(field);
		childCtx.setBranch(branch);

		push(childCtx);

		obj.accept(this);
	}

	@Override
	public Object enter(Field field) {
		VisitorContext ctx = peek();

		VisitorContext childCtx = new VisitorContext();
		childCtx.setField(field);
		childCtx.setBranch(ctx.getBranch());

		push(childCtx);

		// skip system field
		if (skipSystemField && field.isSystemField()) {
			return false;
		}

		return true;
	}

	@Override
	public Object leave(Field field) {
		pop();
		return null;
	}

	@Override
	public Object enter(_Object object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();



		Element newElelment = ctx.getBranch().addElement(field.name);

		VisitorContext childCtx = new VisitorContext();
		childCtx.setField(field);
		childCtx.setBranch(newElelment);
		push(childCtx);

		return null;
	}

	@Override
	public Object leave(_Object object) {
		pop();
		return null;
	}

	@Override
	public Object enter(StringValue object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());

		return null;
	}

	@Override
	public Object leave(StringValue object) {

		return null;
	}

	@Override
	public Object enter(IntegerValue object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());
		return null;
	}

	@Override
	public Object leave(IntegerValue object) {

		return null;
	}

	@Override
	public Object enter(BooleanValue object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());
		return null;
	}

	@Override
	public Object leave(BooleanValue object) {

		return null;
	}

	@Override
	public Object enter(FloatValue object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());
		return null;
	}

	@Override
	public Object leave(FloatValue object) {

		return null;
	}

	@Override
	public Object enter(TimeValue object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());
		return null;
	}

	@Override
	public Object leave(TimeValue object) {

		return null;
	}

	@Override
	public Object enter(Link object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());
		return null;
	}

	@Override
	public Object leave(Link object) {

		return null;
	}

	@Override
	public Object enter(Procedure object) {
		VisitorContext ctx = peek();
		Field field = ctx.getField();

		ctx.getBranch().addElement(field.name).addText(object.getValue());
		return null;
	}

	@Override
	public Object leave(Procedure object) {

		return null;
	}

}
