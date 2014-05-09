package com.bluesky.visualprogramming.core;

import java.util.HashMap;
import java.util.Map;

import com.bluesky.visualprogramming.vm.CompiledProcedure;

public class Procedure extends _Object {

	public Procedure(long id) {
		super(id);
		type = ObjectType.PROCEDURE;
	}
	
	@Override
	public void copy(_Object src, boolean deep, BasicObjectFactory factory) {
	
		super.copy(src, deep, factory);
		
		setValue(src.getValue());		
	}

	public String name;

	boolean _native = false;
	public String nativeProcedureClassName = "";
	public String nativeProcedureParameters = "";

	public String language;
	String code;

	/**
	 * lazy compile
	 */
	public CompiledProcedure compiled;

	@Override
	public String getValue() {

		String attributes = "";
		if (this._native)
			attributes = String
					.format("[native=%b, native-class=%s, native-procedure-parameters=%s]",
							_native, nativeProcedureClassName,
							nativeProcedureParameters);
		else
			attributes = "[native=false, language=goo]\n";

		return attributes + code;
	}

	private Map<String, String> parseAttributes(String str) {

		Map<String, String> map = new HashMap<String, String>();
		String[] attrArray = str.split(",");
		for (String s : attrArray) {
			String[] kv = s.split("=");

			String value = "";
			if (kv.length == 2) {
				value = kv[1];
				if (value != null)
					value = value.trim();
			}
			map.put(kv[0].trim(), value);
		}
		return map;

	}

	@Override
	public void setValue(String value) {
		int index1 = value.indexOf('[');
		String code;
		if (index1 >= 0) {// has attributes
			int index2 = value.indexOf(']');
			String attrStr = value.substring(index1 + 1, index2);
			Map<String, String> map = parseAttributes(attrStr);

			if (map.containsKey("native"))
				_native = Boolean.valueOf(map.get("native"));
			else
				_native = false;

			if (map.containsKey("native-class"))
				nativeProcedureClassName = map.get("native-class");

			if (map.containsKey("native-procedure-parameters"))
				nativeProcedureParameters = map
						.get("native-procedure-parameters");

			if (map.containsKey("language"))
				language = map.get("language");

			int endOflineIndex = value.indexOf('\n', index2);
			if (endOflineIndex >= 0)
				code = value.substring(endOflineIndex + 1);
			else
				code = "";

		} else
			code = value;

		this.code = code.trim();

		// clean the compiled procedure. any change to code need re-compile.
		this.compiled = null;
	}

	public boolean isNative() {
		return _native;
	}

	public static void main(String[] args) {
		Procedure p = new Procedure(1);
		p.setValue("[native=true, native-class=com.Foo]");

	}

	public String[] getNativeProcedureParameterNames() {
		if (nativeProcedureParameters != null
				&& !nativeProcedureParameters.trim().isEmpty())
			return nativeProcedureParameters.split("\\|");
		else
			return new String[0];
	}

	@Override
	public String getHumanReadableText() {

		return "";
	}
	
	public String getAroundSourceCode(int lineNumber){
		String[] lines = getValue().split("\\n");
		
		StringBuilder sb = new StringBuilder();
		int maxLine = lines.length;
		int line;
		
		String CR = "\r\n";
		
		line=lineNumber-1;
		if(line>=0 && line<maxLine)
			sb.append(lines[line]+CR);
		
		line=lineNumber;
		if(line>=0 && line<maxLine)
			sb.append(lines[line]+CR);
		
		line=lineNumber+1;
		if(line>=0 && line<maxLine)
			sb.append(lines[line]+CR);
		
		
		return  sb.toString();
	}
}
