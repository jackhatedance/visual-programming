package com.bluesky.my4gl.global;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * global settings for the whole virtual machine or interpreter.
 * 
 * @author hz00260
 * 
 */
public class GlobalSettings {

	private static OutputStream standardOutputStream = System.out;

	public static OutputStream getStandardOutputStream() {
		return standardOutputStream;
	}

	public static void setStandardOutputStream(OutputStream standardOutputStream) {
		GlobalSettings.standardOutputStream = standardOutputStream;
	}

	private static String defaultEncoding = "utf-8";

	public static String getDefaultEncoding() {
		return defaultEncoding;
	}

	public static void setDefaultEncoding(String defaultEncoding) {
		GlobalSettings.defaultEncoding = defaultEncoding;
	}

}
