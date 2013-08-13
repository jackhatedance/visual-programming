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
		T__6=1, T__5=2, T__4=3, T__3=4, T__2=5, T__1=6, T__0=7, PROCEDURE=8, IF=9, 
		ELSE=10, WHILE=11, FOR=12, RETURN=13, BOOLEAN=14, BREAK=15, CONTINUE=16, 
		DOT=17, LINE_COMMENT=18, BLOCK_COMMENT=19, ID=20, NUMBER=21, STRING=22, 
		WS=23;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'{'", "')'", "','", "'('", "'='", "'}'", "';'", "'procedure'", "'if'", 
		"'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", "'continue'", 
		"'.'", "LINE_COMMENT", "BLOCK_COMMENT", "ID", "NUMBER", "STRING", "WS"
	};
	public static final String[] ruleNames = {
		"T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "PROCEDURE", "IF", 
		"ELSE", "WHILE", "FOR", "RETURN", "BOOLEAN", "BREAK", "CONTINUE", "DOT", 
		"LINE_COMMENT", "BLOCK_COMMENT", "ID", "NUMBER", "STRING", "WS"
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
		case 22: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\31\u00c3\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17l\n\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\7\23\u0083\n\23\f\23\16\23\u0086\13\23\3\23\5\23\u0089"+
		"\n\23\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u0091\n\24\f\24\16\24\u0094\13"+
		"\24\3\24\3\24\3\24\3\25\3\25\7\25\u009b\n\25\f\25\16\25\u009e\13\25\3"+
		"\26\6\26\u00a1\n\26\r\26\16\26\u00a2\3\26\6\26\u00a6\n\26\r\26\16\26\u00a7"+
		"\3\26\3\26\6\26\u00ac\n\26\r\26\16\26\u00ad\5\26\u00b0\n\26\3\27\3\27"+
		"\3\27\3\27\7\27\u00b6\n\27\f\27\16\27\u00b9\13\27\3\27\3\27\3\30\6\30"+
		"\u00be\n\30\r\30\16\30\u00bf\3\30\3\30\4\u0084\u0092\31\3\3\1\5\4\1\7"+
		"\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33"+
		"\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\2"+
		"\3\2\7\4\2C\\c|\5\2\62;C\\c|\3\2\62;\3\2$$\5\2\13\f\17\17\"\"\u00ce\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\3\61\3\2\2\2\5\63\3\2\2\2\7\65\3\2\2\2\t\67\3\2\2\2\139\3\2\2\2\r;\3"+
		"\2\2\2\17=\3\2\2\2\21?\3\2\2\2\23I\3\2\2\2\25L\3\2\2\2\27Q\3\2\2\2\31"+
		"W\3\2\2\2\33[\3\2\2\2\35k\3\2\2\2\37m\3\2\2\2!s\3\2\2\2#|\3\2\2\2%~\3"+
		"\2\2\2\'\u008c\3\2\2\2)\u0098\3\2\2\2+\u00af\3\2\2\2-\u00b1\3\2\2\2/\u00bd"+
		"\3\2\2\2\61\62\7}\2\2\62\4\3\2\2\2\63\64\7+\2\2\64\6\3\2\2\2\65\66\7."+
		"\2\2\66\b\3\2\2\2\678\7*\2\28\n\3\2\2\29:\7?\2\2:\f\3\2\2\2;<\7\177\2"+
		"\2<\16\3\2\2\2=>\7=\2\2>\20\3\2\2\2?@\7r\2\2@A\7t\2\2AB\7q\2\2BC\7e\2"+
		"\2CD\7g\2\2DE\7f\2\2EF\7w\2\2FG\7t\2\2GH\7g\2\2H\22\3\2\2\2IJ\7k\2\2J"+
		"K\7h\2\2K\24\3\2\2\2LM\7g\2\2MN\7n\2\2NO\7u\2\2OP\7g\2\2P\26\3\2\2\2Q"+
		"R\7y\2\2RS\7j\2\2ST\7k\2\2TU\7n\2\2UV\7g\2\2V\30\3\2\2\2WX\7h\2\2XY\7"+
		"q\2\2YZ\7t\2\2Z\32\3\2\2\2[\\\7t\2\2\\]\7g\2\2]^\7v\2\2^_\7w\2\2_`\7t"+
		"\2\2`a\7p\2\2a\34\3\2\2\2bc\7v\2\2cd\7t\2\2de\7w\2\2el\7g\2\2fg\7h\2\2"+
		"gh\7c\2\2hi\7n\2\2ij\7u\2\2jl\7g\2\2kb\3\2\2\2kf\3\2\2\2l\36\3\2\2\2m"+
		"n\7d\2\2no\7t\2\2op\7g\2\2pq\7c\2\2qr\7m\2\2r \3\2\2\2st\7e\2\2tu\7q\2"+
		"\2uv\7p\2\2vw\7v\2\2wx\7k\2\2xy\7p\2\2yz\7w\2\2z{\7g\2\2{\"\3\2\2\2|}"+
		"\7\60\2\2}$\3\2\2\2~\177\7\61\2\2\177\u0080\7\61\2\2\u0080\u0084\3\2\2"+
		"\2\u0081\u0083\13\2\2\2\u0082\u0081\3\2\2\2\u0083\u0086\3\2\2\2\u0084"+
		"\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2"+
		"\2\2\u0087\u0089\7\17\2\2\u0088\u0087\3\2\2\2\u0088\u0089\3\2\2\2\u0089"+
		"\u008a\3\2\2\2\u008a\u008b\7\f\2\2\u008b&\3\2\2\2\u008c\u008d\7\61\2\2"+
		"\u008d\u008e\7,\2\2\u008e\u0092\3\2\2\2\u008f\u0091\13\2\2\2\u0090\u008f"+
		"\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0093\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0096\7,\2\2\u0096\u0097\7\61"+
		"\2\2\u0097(\3\2\2\2\u0098\u009c\t\2\2\2\u0099\u009b\t\3\2\2\u009a\u0099"+
		"\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d"+
		"*\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a1\t\4\2\2\u00a0\u009f\3\2\2\2"+
		"\u00a1\u00a2\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00b0"+
		"\3\2\2\2\u00a4\u00a6\t\4\2\2\u00a5\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ab\7\60"+
		"\2\2\u00aa\u00ac\t\4\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad"+
		"\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00a0\3\2"+
		"\2\2\u00af\u00a5\3\2\2\2\u00b0,\3\2\2\2\u00b1\u00b7\7$\2\2\u00b2\u00b3"+
		"\7^\2\2\u00b3\u00b6\7$\2\2\u00b4\u00b6\n\5\2\2\u00b5\u00b2\3\2\2\2\u00b5"+
		"\u00b4\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2"+
		"\2\2\u00b8\u00ba\3\2\2\2\u00b9\u00b7\3\2\2\2\u00ba\u00bb\7$\2\2\u00bb"+
		".\3\2\2\2\u00bc\u00be\t\6\2\2\u00bd\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2"+
		"\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2"+
		"\b\30\2\2\u00c2\60\3\2\2\2\17\2k\u0084\u0088\u0092\u009c\u00a2\u00a7\u00ad"+
		"\u00af\u00b5\u00b7\u00bf";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}