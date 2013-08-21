package com.bluesky.visualprogramming.vm;

import java.io.InputStream;


/**
 * 
 * 
 * @author jackding
 * 
 */
public interface Compiler {

	CompiledProcedure compile(String code);

	CompiledProcedure compile(InputStream is);
}
