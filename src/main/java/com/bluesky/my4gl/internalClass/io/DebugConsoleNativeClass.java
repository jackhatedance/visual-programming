package com.bluesky.my4gl.internalClass.io;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import com.bluesky.my4gl.core.NativeClass;
import com.bluesky.my4gl.core.Object;
import com.bluesky.my4gl.global.GlobalSettings;
import com.bluesky.my4gl.internalClass.lang.NativeObject;

/**
 * the Integer class extends from Object class, with no field, and some methods:
 * add,sub,multiple,divide;equal,greatThan,lessThan,greatThanOrEqual,
 * lessThanOrEqual,
 * 
 * @author xp
 * 
 */
public class DebugConsoleNativeClass implements NativeClass {

	@Override
	public Object invoke(
			com.bluesky.my4gl.core.interpreter.ExceutionContext ctx,
			Object self, String methodName, Object[] parameters) {

		if (methodName.equals("println")) {
			NativeObject<String> p = (NativeObject<String>) parameters[0];
			PrintStream ps;
			try {
				ps = new PrintStream(GlobalSettings.getStandardOutputStream(),
						true, GlobalSettings.getDefaultEncoding());
				ps.println(p.getNativeValue());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;

		}

		return null;
	}

}
