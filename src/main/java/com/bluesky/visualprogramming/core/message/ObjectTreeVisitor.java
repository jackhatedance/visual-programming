package com.bluesky.visualprogramming.core.message;

import com.bluesky.visualprogramming.core.Procedure;
import com.bluesky.visualprogramming.core._Object;
import com.bluesky.visualprogramming.core.link.SoftLink;
import com.bluesky.visualprogramming.core.value.BooleanValue;
import com.bluesky.visualprogramming.core.value.FloatValue;
import com.bluesky.visualprogramming.core.value.IntegerValue;
import com.bluesky.visualprogramming.core.value.StringValue;
import com.bluesky.visualprogramming.core.value.TimeValue;

/**
 * first purpose of this is to serialize object as XML or other text form for chat box. but
 * it can be used for other purposes.
 * 
 * @author jack
 * 
 */
public interface ObjectTreeVisitor {
	void visitProcedure(Procedure object);

	void visitObject(_Object object);

	void visitInteger(IntegerValue object);

	void visitBoolean(BooleanValue object);

	void visitString(StringValue object);

	void visitFloat(FloatValue object);

	void visitLink(SoftLink object);

	void visitTime(TimeValue object);

}
