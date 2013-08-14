// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.goo.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GooLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, PROCEDURE=7, IF=8, ELSE=9, 
		WHILE=10, FOR=11, RETURN=12, BOOLEAN=13, BREAK=14, CONTINUE=15, DOT=16, 
		LINE_COMMENT=17, BLOCK_COMMENT=18, REF_ASSIGN=19, OWN_ASSIGN=20, AUTO_ASSIGN=21, 
		ID=22, NUMBER=23, STRING=24, WS=25;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'{'", "')'", "','", "'('", "'}'", "';'", "'procedure'", "'if'", "'else'", 
		"'while'", "'for'", "'return'", "BOOLEAN", "'break'", "'continue'", "'.'", 
		"LINE_COMMENT", "BLOCK_COMMENT", "'->'", "'=>'", "'='", "ID", "NUMBER", 
		"STRING", "WS"
	};
	public static final String[] ruleNames = {
		"T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "PROCEDURE", "IF", "ELSE", 
		"WHILE", "FOR", "RETURN", "BOOLEAN", "BREAK", "CONTINUE", "DOT", "LINE_COMMENT", 
		"BLOCK_COMMENT", "REF_ASSIGN", "OWN_ASSIGN", "AUTO_ASSIGN", "ID", "NUMBER", 
		"STRING", "WS"
	};


	public GooLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Goo.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 24: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\33\u00cd\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16n\n\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\7\22\u0085\n\22\f\22\16\22\u0088\13\22\3\22\5\22"+
		"\u008b\n\22\3\22\3\22\3\23\3\23\3\23\3\23\7\23\u0093\n\23\f\23\16\23\u0096"+
		"\13\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\7\27\u00a5\n\27\f\27\16\27\u00a8\13\27\3\30\6\30\u00ab\n\30\r\30\16\30"+
		"\u00ac\3\30\6\30\u00b0\n\30\r\30\16\30\u00b1\3\30\3\30\6\30\u00b6\n\30"+
		"\r\30\16\30\u00b7\5\30\u00ba\n\30\3\31\3\31\3\31\3\31\7\31\u00c0\n\31"+
		"\f\31\16\31\u00c3\13\31\3\31\3\31\3\32\6\32\u00c8\n\32\r\32\16\32\u00c9"+
		"\3\32\3\32\4\u0086\u0094\33\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t"+
		"\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1"+
		"#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\2\3\2\7\4\2"+
		"C\\c|\5\2\62;C\\c|\3\2\62;\3\2$$\5\2\13\f\17\17\"\"\u00d8\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2"+
		"\2\2\63\3\2\2\2\3\65\3\2\2\2\5\67\3\2\2\2\79\3\2\2\2\t;\3\2\2\2\13=\3"+
		"\2\2\2\r?\3\2\2\2\17A\3\2\2\2\21K\3\2\2\2\23N\3\2\2\2\25S\3\2\2\2\27Y"+
		"\3\2\2\2\31]\3\2\2\2\33m\3\2\2\2\35o\3\2\2\2\37u\3\2\2\2!~\3\2\2\2#\u0080"+
		"\3\2\2\2%\u008e\3\2\2\2\'\u009a\3\2\2\2)\u009d\3\2\2\2+\u00a0\3\2\2\2"+
		"-\u00a2\3\2\2\2/\u00b9\3\2\2\2\61\u00bb\3\2\2\2\63\u00c7\3\2\2\2\65\66"+
		"\7}\2\2\66\4\3\2\2\2\678\7+\2\28\6\3\2\2\29:\7.\2\2:\b\3\2\2\2;<\7*\2"+
		"\2<\n\3\2\2\2=>\7\177\2\2>\f\3\2\2\2?@\7=\2\2@\16\3\2\2\2AB\7r\2\2BC\7"+
		"t\2\2CD\7q\2\2DE\7e\2\2EF\7g\2\2FG\7f\2\2GH\7w\2\2HI\7t\2\2IJ\7g\2\2J"+
		"\20\3\2\2\2KL\7k\2\2LM\7h\2\2M\22\3\2\2\2NO\7g\2\2OP\7n\2\2PQ\7u\2\2Q"+
		"R\7g\2\2R\24\3\2\2\2ST\7y\2\2TU\7j\2\2UV\7k\2\2VW\7n\2\2WX\7g\2\2X\26"+
		"\3\2\2\2YZ\7h\2\2Z[\7q\2\2[\\\7t\2\2\\\30\3\2\2\2]^\7t\2\2^_\7g\2\2_`"+
		"\7v\2\2`a\7w\2\2ab\7t\2\2bc\7p\2\2c\32\3\2\2\2de\7v\2\2ef\7t\2\2fg\7w"+
		"\2\2gn\7g\2\2hi\7h\2\2ij\7c\2\2jk\7n\2\2kl\7u\2\2ln\7g\2\2md\3\2\2\2m"+
		"h\3\2\2\2n\34\3\2\2\2op\7d\2\2pq\7t\2\2qr\7g\2\2rs\7c\2\2st\7m\2\2t\36"+
		"\3\2\2\2uv\7e\2\2vw\7q\2\2wx\7p\2\2xy\7v\2\2yz\7k\2\2z{\7p\2\2{|\7w\2"+
		"\2|}\7g\2\2} \3\2\2\2~\177\7\60\2\2\177\"\3\2\2\2\u0080\u0081\7\61\2\2"+
		"\u0081\u0082\7\61\2\2\u0082\u0086\3\2\2\2\u0083\u0085\13\2\2\2\u0084\u0083"+
		"\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0087\3\2\2\2\u0086\u0084\3\2\2\2\u0087"+
		"\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008b\7\17\2\2\u008a\u0089\3"+
		"\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\7\f\2\2\u008d"+
		"$\3\2\2\2\u008e\u008f\7\61\2\2\u008f\u0090\7,\2\2\u0090\u0094\3\2\2\2"+
		"\u0091\u0093\13\2\2\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0095"+
		"\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0097\3\2\2\2\u0096\u0094\3\2\2\2\u0097"+
		"\u0098\7,\2\2\u0098\u0099\7\61\2\2\u0099&\3\2\2\2\u009a\u009b\7/\2\2\u009b"+
		"\u009c\7@\2\2\u009c(\3\2\2\2\u009d\u009e\7?\2\2\u009e\u009f\7@\2\2\u009f"+
		"*\3\2\2\2\u00a0\u00a1\7?\2\2\u00a1,\3\2\2\2\u00a2\u00a6\t\2\2\2\u00a3"+
		"\u00a5\t\3\2\2\u00a4\u00a3\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2"+
		"\2\2\u00a6\u00a7\3\2\2\2\u00a7.\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ab"+
		"\t\4\2\2\u00aa\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac"+
		"\u00ad\3\2\2\2\u00ad\u00ba\3\2\2\2\u00ae\u00b0\t\4\2\2\u00af\u00ae\3\2"+
		"\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\u00b3\3\2\2\2\u00b3\u00b5\7\60\2\2\u00b4\u00b6\t\4\2\2\u00b5\u00b4\3"+
		"\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"+
		"\u00ba\3\2\2\2\u00b9\u00aa\3\2\2\2\u00b9\u00af\3\2\2\2\u00ba\60\3\2\2"+
		"\2\u00bb\u00c1\7$\2\2\u00bc\u00bd\7^\2\2\u00bd\u00c0\7$\2\2\u00be\u00c0"+
		"\n\5\2\2\u00bf\u00bc\3\2\2\2\u00bf\u00be\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c4\3\2\2\2\u00c3\u00c1\3\2"+
		"\2\2\u00c4\u00c5\7$\2\2\u00c5\62\3\2\2\2\u00c6\u00c8\t\6\2\2\u00c7\u00c6"+
		"\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca"+
		"\u00cb\3\2\2\2\u00cb\u00cc\b\32\2\2\u00cc\64\3\2\2\2\17\2m\u0086\u008a"+
		"\u0094\u00a6\u00ac\u00b1\u00b7\u00b9\u00bf\u00c1\u00c9";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}