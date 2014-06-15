// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.dialect.goo.parser;
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
		T__8=1, T__7=2, T__6=3, T__5=4, T__4=5, T__3=6, T__2=7, T__1=8, T__0=9, 
		PROCEDURE=10, IF=11, ELSE=12, WHILE=13, FOR=14, RETURN=15, BOOLEAN=16, 
		BREAK=17, CONTINUE=18, DOT=19, DOTDOT=20, LINE_COMMENT=21, BLOCK_COMMENT=22, 
		REF_ASSIGN=23, OWN_ASSIGN=24, AUTO_ASSIGN=25, LINK_PROTOCOL=26, LINK_ADDRESS=27, 
		NUMBER=28, STRING=29, NULL=30, ID=31, WS=32;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'{'", "')'", "','", "'('", "':'", "'}'", "'#'", "';'", "'$'", "'procedure'", 
		"'if'", "'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", 
		"'continue'", "'.'", "'..'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", 
		"'=>'", "AUTO_ASSIGN", "LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", 
		"'null'", "ID", "WS"
	};
	public static final String[] ruleNames = {
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"PROCEDURE", "IF", "ELSE", "WHILE", "FOR", "RETURN", "BOOLEAN", "BREAK", 
		"CONTINUE", "DOT", "DOTDOT", "LINE_COMMENT", "BLOCK_COMMENT", "REF_ASSIGN", 
		"OWN_ASSIGN", "AUTO_ASSIGN", "LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", 
		"STRING", "NULL", "ID", "WS"
	};


	    public static final int WHITESPACE = 1;
	    public static final int COMMENTS = 2;


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
		case 31: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: _channel = WHITESPACE;  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2\"\u010d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\5\21\u0082\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\7\26\u009c\n\26\f\26\16\26\u009f\13\26\3\26\5\26\u00a2\n\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\7\27\u00aa\n\27\f\27\16\27\u00ad\13\27\3\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\5\32\u00bb\n\32"+
		"\3\33\6\33\u00be\n\33\r\33\16\33\u00bf\3\33\3\33\3\33\3\33\3\34\6\34\u00c7"+
		"\n\34\r\34\16\34\u00c8\5\34\u00cb\n\34\3\34\3\34\6\34\u00cf\n\34\r\34"+
		"\16\34\u00d0\3\34\3\34\6\34\u00d5\n\34\r\34\16\34\u00d6\7\34\u00d9\n\34"+
		"\f\34\16\34\u00dc\13\34\3\35\6\35\u00df\n\35\r\35\16\35\u00e0\3\35\6\35"+
		"\u00e4\n\35\r\35\16\35\u00e5\3\35\3\35\6\35\u00ea\n\35\r\35\16\35\u00eb"+
		"\5\35\u00ee\n\35\3\36\3\36\3\36\3\36\7\36\u00f4\n\36\f\36\16\36\u00f7"+
		"\13\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3 \3 \7 \u0102\n \f \16 \u0105"+
		"\13 \3!\6!\u0108\n!\r!\16!\u0109\3!\3!\4\u009d\u00ab\"\3\3\1\5\4\1\7\5"+
		"\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16\1\33\17"+
		"\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1/\31\1\61"+
		"\32\1\63\33\1\65\34\1\67\35\19\36\1;\37\1= \1?!\1A\"\2\3\2\n\5\2\62;C"+
		"\\c|\b\2/\60\62;C\\^^aac|\6\2//\62;C\\c|\3\2\62;\3\2$$\5\2C\\aac|\6\2"+
		"\62;C\\aac|\5\2\13\f\17\17\"\"\u011f\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2"+
		"\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3"+
		"\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2"+
		"\2\2\3C\3\2\2\2\5E\3\2\2\2\7G\3\2\2\2\tI\3\2\2\2\13K\3\2\2\2\rM\3\2\2"+
		"\2\17O\3\2\2\2\21Q\3\2\2\2\23S\3\2\2\2\25U\3\2\2\2\27_\3\2\2\2\31b\3\2"+
		"\2\2\33g\3\2\2\2\35m\3\2\2\2\37q\3\2\2\2!\u0081\3\2\2\2#\u0083\3\2\2\2"+
		"%\u0089\3\2\2\2\'\u0092\3\2\2\2)\u0094\3\2\2\2+\u0097\3\2\2\2-\u00a5\3"+
		"\2\2\2/\u00b1\3\2\2\2\61\u00b4\3\2\2\2\63\u00ba\3\2\2\2\65\u00bd\3\2\2"+
		"\2\67\u00ca\3\2\2\29\u00ed\3\2\2\2;\u00ef\3\2\2\2=\u00fa\3\2\2\2?\u00ff"+
		"\3\2\2\2A\u0107\3\2\2\2CD\7}\2\2D\4\3\2\2\2EF\7+\2\2F\6\3\2\2\2GH\7.\2"+
		"\2H\b\3\2\2\2IJ\7*\2\2J\n\3\2\2\2KL\7<\2\2L\f\3\2\2\2MN\7\177\2\2N\16"+
		"\3\2\2\2OP\7%\2\2P\20\3\2\2\2QR\7=\2\2R\22\3\2\2\2ST\7&\2\2T\24\3\2\2"+
		"\2UV\7r\2\2VW\7t\2\2WX\7q\2\2XY\7e\2\2YZ\7g\2\2Z[\7f\2\2[\\\7w\2\2\\]"+
		"\7t\2\2]^\7g\2\2^\26\3\2\2\2_`\7k\2\2`a\7h\2\2a\30\3\2\2\2bc\7g\2\2cd"+
		"\7n\2\2de\7u\2\2ef\7g\2\2f\32\3\2\2\2gh\7y\2\2hi\7j\2\2ij\7k\2\2jk\7n"+
		"\2\2kl\7g\2\2l\34\3\2\2\2mn\7h\2\2no\7q\2\2op\7t\2\2p\36\3\2\2\2qr\7t"+
		"\2\2rs\7g\2\2st\7v\2\2tu\7w\2\2uv\7t\2\2vw\7p\2\2w \3\2\2\2xy\7v\2\2y"+
		"z\7t\2\2z{\7w\2\2{\u0082\7g\2\2|}\7h\2\2}~\7c\2\2~\177\7n\2\2\177\u0080"+
		"\7u\2\2\u0080\u0082\7g\2\2\u0081x\3\2\2\2\u0081|\3\2\2\2\u0082\"\3\2\2"+
		"\2\u0083\u0084\7d\2\2\u0084\u0085\7t\2\2\u0085\u0086\7g\2\2\u0086\u0087"+
		"\7c\2\2\u0087\u0088\7m\2\2\u0088$\3\2\2\2\u0089\u008a\7e\2\2\u008a\u008b"+
		"\7q\2\2\u008b\u008c\7p\2\2\u008c\u008d\7v\2\2\u008d\u008e\7k\2\2\u008e"+
		"\u008f\7p\2\2\u008f\u0090\7w\2\2\u0090\u0091\7g\2\2\u0091&\3\2\2\2\u0092"+
		"\u0093\7\60\2\2\u0093(\3\2\2\2\u0094\u0095\7\60\2\2\u0095\u0096\7\60\2"+
		"\2\u0096*\3\2\2\2\u0097\u0098\7\61\2\2\u0098\u0099\7\61\2\2\u0099\u009d"+
		"\3\2\2\2\u009a\u009c\13\2\2\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2"+
		"\u009d\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d"+
		"\3\2\2\2\u00a0\u00a2\7\17\2\2\u00a1\u00a0\3\2\2\2\u00a1\u00a2\3\2\2\2"+
		"\u00a2\u00a3\3\2\2\2\u00a3\u00a4\7\f\2\2\u00a4,\3\2\2\2\u00a5\u00a6\7"+
		"\61\2\2\u00a6\u00a7\7,\2\2\u00a7\u00ab\3\2\2\2\u00a8\u00aa\13\2\2\2\u00a9"+
		"\u00a8\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ab\u00a9\3\2"+
		"\2\2\u00ac\u00ae\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\7,\2\2\u00af"+
		"\u00b0\7\61\2\2\u00b0.\3\2\2\2\u00b1\u00b2\7/\2\2\u00b2\u00b3\7@\2\2\u00b3"+
		"\60\3\2\2\2\u00b4\u00b5\7?\2\2\u00b5\u00b6\7@\2\2\u00b6\62\3\2\2\2\u00b7"+
		"\u00bb\7?\2\2\u00b8\u00b9\7\u0080\2\2\u00b9\u00bb\7@\2\2\u00ba\u00b7\3"+
		"\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\64\3\2\2\2\u00bc\u00be\t\2\2\2\u00bd"+
		"\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\7<\2\2\u00c2\u00c3\7\61\2\2\u00c3"+
		"\u00c4\7\61\2\2\u00c4\66\3\2\2\2\u00c5\u00c7\t\3\2\2\u00c6\u00c5\3\2\2"+
		"\2\u00c7\u00c8\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00cb"+
		"\3\2\2\2\u00ca\u00c6\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc"+
		"\u00ce\7B\2\2\u00cd\u00cf\t\4\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3\2"+
		"\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00da\3\2\2\2\u00d2"+
		"\u00d4\7\60\2\2\u00d3\u00d5\t\4\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d6\3"+
		"\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8"+
		"\u00d2\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2"+
		"\2\2\u00db8\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00df\t\5\2\2\u00de\u00dd"+
		"\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1"+
		"\u00ee\3\2\2\2\u00e2\u00e4\t\5\2\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7"+
		"\u00e9\7\60\2\2\u00e8\u00ea\t\5\2\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3"+
		"\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ee\3\2\2\2\u00ed"+
		"\u00de\3\2\2\2\u00ed\u00e3\3\2\2\2\u00ee:\3\2\2\2\u00ef\u00f5\7$\2\2\u00f0"+
		"\u00f1\7^\2\2\u00f1\u00f4\7$\2\2\u00f2\u00f4\n\6\2\2\u00f3\u00f0\3\2\2"+
		"\2\u00f3\u00f2\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f6"+
		"\3\2\2\2\u00f6\u00f8\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8\u00f9\7$\2\2\u00f9"+
		"<\3\2\2\2\u00fa\u00fb\7p\2\2\u00fb\u00fc\7w\2\2\u00fc\u00fd\7n\2\2\u00fd"+
		"\u00fe\7n\2\2\u00fe>\3\2\2\2\u00ff\u0103\t\7\2\2\u0100\u0102\t\b\2\2\u0101"+
		"\u0100\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2"+
		"\2\2\u0104@\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0108\t\t\2\2\u0107\u0106"+
		"\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a"+
		"\u010b\3\2\2\2\u010b\u010c\b!\2\2\u010cB\3\2\2\2\26\2\u0081\u009d\u00a1"+
		"\u00ab\u00ba\u00bf\u00c8\u00ca\u00d0\u00d6\u00da\u00e0\u00e5\u00eb\u00ed"+
		"\u00f3\u00f5\u0103\u0109";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}