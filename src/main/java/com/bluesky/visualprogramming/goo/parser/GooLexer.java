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
		DOT=17, LINE_COMMENT=18, BLOCK_COMMENT=19, REF_ASSIGN=20, OWN_ASSIGN=21, 
		AUTO_ASSIGN=22, LINK_PROTOCOL=23, LINK_ADDRESS=24, NUMBER=25, STRING=26, 
		NULL=27, ID=28, WS=29;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'{'", "')'", "','", "'('", "':'", "'}'", "';'", "'procedure'", "'if'", 
		"'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", "'continue'", 
		"'.'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", "'=>'", "AUTO_ASSIGN", 
		"LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", "'null'", "ID", "WS"
	};
	public static final String[] ruleNames = {
		"T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "PROCEDURE", "IF", 
		"ELSE", "WHILE", "FOR", "RETURN", "BOOLEAN", "BREAK", "CONTINUE", "DOT", 
		"LINE_COMMENT", "BLOCK_COMMENT", "REF_ASSIGN", "OWN_ASSIGN", "AUTO_ASSIGN", 
		"LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", "NULL", "ID", "WS"
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
		case 28: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\37\u00fe\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\5\17x\n\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\7\23\u008f\n\23\f\23\16\23\u0092\13\23\3\23\5\23\u0095\n\23\3\23"+
		"\3\23\3\24\3\24\3\24\3\24\7\24\u009d\n\24\f\24\16\24\u00a0\13\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\5\27\u00ae\n\27"+
		"\3\30\6\30\u00b1\n\30\r\30\16\30\u00b2\3\30\3\30\3\30\3\30\3\31\6\31\u00ba"+
		"\n\31\r\31\16\31\u00bb\3\31\3\31\6\31\u00c0\n\31\r\31\16\31\u00c1\3\31"+
		"\3\31\6\31\u00c6\n\31\r\31\16\31\u00c7\7\31\u00ca\n\31\f\31\16\31\u00cd"+
		"\13\31\3\32\6\32\u00d0\n\32\r\32\16\32\u00d1\3\32\6\32\u00d5\n\32\r\32"+
		"\16\32\u00d6\3\32\3\32\6\32\u00db\n\32\r\32\16\32\u00dc\5\32\u00df\n\32"+
		"\3\33\3\33\3\33\3\33\7\33\u00e5\n\33\f\33\16\33\u00e8\13\33\3\33\3\33"+
		"\3\34\3\34\3\34\3\34\3\34\3\35\3\35\7\35\u00f3\n\35\f\35\16\35\u00f6\13"+
		"\35\3\36\6\36\u00f9\n\36\r\36\16\36\u00fa\3\36\3\36\4\u0090\u009e\37\3"+
		"\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r"+
		"\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27"+
		"\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1;\37\2\3\2\b\5\2\62"+
		";C\\c|\6\2\60\60\62;C\\c|\3\2\62;\3\2$$\5\2C\\aac|\5\2\13\f\17\17\"\""+
		"\u010f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\3=\3\2\2\2\5?\3\2\2\2\7A\3\2\2\2\tC\3\2\2\2\13E\3\2\2\2"+
		"\rG\3\2\2\2\17I\3\2\2\2\21K\3\2\2\2\23U\3\2\2\2\25X\3\2\2\2\27]\3\2\2"+
		"\2\31c\3\2\2\2\33g\3\2\2\2\35w\3\2\2\2\37y\3\2\2\2!\177\3\2\2\2#\u0088"+
		"\3\2\2\2%\u008a\3\2\2\2\'\u0098\3\2\2\2)\u00a4\3\2\2\2+\u00a7\3\2\2\2"+
		"-\u00ad\3\2\2\2/\u00b0\3\2\2\2\61\u00b9\3\2\2\2\63\u00de\3\2\2\2\65\u00e0"+
		"\3\2\2\2\67\u00eb\3\2\2\29\u00f0\3\2\2\2;\u00f8\3\2\2\2=>\7}\2\2>\4\3"+
		"\2\2\2?@\7+\2\2@\6\3\2\2\2AB\7.\2\2B\b\3\2\2\2CD\7*\2\2D\n\3\2\2\2EF\7"+
		"<\2\2F\f\3\2\2\2GH\7\177\2\2H\16\3\2\2\2IJ\7=\2\2J\20\3\2\2\2KL\7r\2\2"+
		"LM\7t\2\2MN\7q\2\2NO\7e\2\2OP\7g\2\2PQ\7f\2\2QR\7w\2\2RS\7t\2\2ST\7g\2"+
		"\2T\22\3\2\2\2UV\7k\2\2VW\7h\2\2W\24\3\2\2\2XY\7g\2\2YZ\7n\2\2Z[\7u\2"+
		"\2[\\\7g\2\2\\\26\3\2\2\2]^\7y\2\2^_\7j\2\2_`\7k\2\2`a\7n\2\2ab\7g\2\2"+
		"b\30\3\2\2\2cd\7h\2\2de\7q\2\2ef\7t\2\2f\32\3\2\2\2gh\7t\2\2hi\7g\2\2"+
		"ij\7v\2\2jk\7w\2\2kl\7t\2\2lm\7p\2\2m\34\3\2\2\2no\7v\2\2op\7t\2\2pq\7"+
		"w\2\2qx\7g\2\2rs\7h\2\2st\7c\2\2tu\7n\2\2uv\7u\2\2vx\7g\2\2wn\3\2\2\2"+
		"wr\3\2\2\2x\36\3\2\2\2yz\7d\2\2z{\7t\2\2{|\7g\2\2|}\7c\2\2}~\7m\2\2~ "+
		"\3\2\2\2\177\u0080\7e\2\2\u0080\u0081\7q\2\2\u0081\u0082\7p\2\2\u0082"+
		"\u0083\7v\2\2\u0083\u0084\7k\2\2\u0084\u0085\7p\2\2\u0085\u0086\7w\2\2"+
		"\u0086\u0087\7g\2\2\u0087\"\3\2\2\2\u0088\u0089\7\60\2\2\u0089$\3\2\2"+
		"\2\u008a\u008b\7\61\2\2\u008b\u008c\7\61\2\2\u008c\u0090\3\2\2\2\u008d"+
		"\u008f\13\2\2\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u0091\3"+
		"\2\2\2\u0090\u008e\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0095\7\17\2\2\u0094\u0093\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096\3"+
		"\2\2\2\u0096\u0097\7\f\2\2\u0097&\3\2\2\2\u0098\u0099\7\61\2\2\u0099\u009a"+
		"\7,\2\2\u009a\u009e\3\2\2\2\u009b\u009d\13\2\2\2\u009c\u009b\3\2\2\2\u009d"+
		"\u00a0\3\2\2\2\u009e\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a1\3\2"+
		"\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a2\7,\2\2\u00a2\u00a3\7\61\2\2\u00a3"+
		"(\3\2\2\2\u00a4\u00a5\7/\2\2\u00a5\u00a6\7@\2\2\u00a6*\3\2\2\2\u00a7\u00a8"+
		"\7?\2\2\u00a8\u00a9\7@\2\2\u00a9,\3\2\2\2\u00aa\u00ae\7?\2\2\u00ab\u00ac"+
		"\7\u0080\2\2\u00ac\u00ae\7@\2\2\u00ad\u00aa\3\2\2\2\u00ad\u00ab\3\2\2"+
		"\2\u00ae.\3\2\2\2\u00af\u00b1\t\2\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b2"+
		"\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\u00b5\7<\2\2\u00b5\u00b6\7\61\2\2\u00b6\u00b7\7\61\2\2\u00b7\60\3\2\2"+
		"\2\u00b8\u00ba\t\3\2\2\u00b9\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00b9"+
		"\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00bf\7B\2\2\u00be"+
		"\u00c0\t\2\2\2\u00bf\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00bf\3\2"+
		"\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00cb\3\2\2\2\u00c3\u00c5\7\60\2\2\u00c4"+
		"\u00c6\t\2\2\2\u00c5\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c5\3\2"+
		"\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00ca\3\2\2\2\u00c9\u00c3\3\2\2\2\u00ca"+
		"\u00cd\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\62\3\2\2"+
		"\2\u00cd\u00cb\3\2\2\2\u00ce\u00d0\t\4\2\2\u00cf\u00ce\3\2\2\2\u00d0\u00d1"+
		"\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00df\3\2\2\2\u00d3"+
		"\u00d5\t\4\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d4\3\2"+
		"\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00da\7\60\2\2\u00d9"+
		"\u00db\t\4\2\2\u00da\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00da\3\2"+
		"\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00df\3\2\2\2\u00de\u00cf\3\2\2\2\u00de"+
		"\u00d4\3\2\2\2\u00df\64\3\2\2\2\u00e0\u00e6\7$\2\2\u00e1\u00e2\7^\2\2"+
		"\u00e2\u00e5\7$\2\2\u00e3\u00e5\n\5\2\2\u00e4\u00e1\3\2\2\2\u00e4\u00e3"+
		"\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7"+
		"\u00e9\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00ea\7$\2\2\u00ea\66\3\2\2\2"+
		"\u00eb\u00ec\7p\2\2\u00ec\u00ed\7w\2\2\u00ed\u00ee\7n\2\2\u00ee\u00ef"+
		"\7n\2\2\u00ef8\3\2\2\2\u00f0\u00f4\t\6\2\2\u00f1\u00f3\t\2\2\2\u00f2\u00f1"+
		"\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5"+
		":\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f7\u00f9\t\7\2\2\u00f8\u00f7\3\2\2\2"+
		"\u00f9\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fc"+
		"\3\2\2\2\u00fc\u00fd\b\36\2\2\u00fd<\3\2\2\2\25\2w\u0090\u0094\u009e\u00ad"+
		"\u00b2\u00bb\u00c1\u00c7\u00cb\u00d1\u00d6\u00dc\u00de\u00e4\u00e6\u00f4"+
		"\u00fa";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}