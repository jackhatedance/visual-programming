package com.bluesky.visualprogramming.core;

import com.bluesky.visualprogramming.goo.GooCompiler;
import com.bluesky.visualprogramming.vm.Compiler;

/**
 * 
 * 
 * @author jackding
 * 
 */
public enum LanguageType {

	GOO {
		@Override
		public Compiler getCompiler() {

			return new GooCompiler();
		}
	},
	BASIC {
		@Override
		public Compiler getCompiler() {

			return null;
		}
	};

	public abstract Compiler getCompiler();

}
