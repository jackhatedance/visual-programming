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
		T__7=1, T__6=2, T__5=3, T__4=4, T__3=5, T__2=6, T__1=7, T__0=8, PROCEDURE=9, 
		IF=10, ELSE=11, WHILE=12, FOR=13, RETURN=14, BOOLEAN=15, BREAK=16, CONTINUE=17, 
		DOT=18, DOTDOT=19, LINE_COMMENT=20, BLOCK_COMMENT=21, REF_ASSIGN=22, OWN_ASSIGN=23, 
		AUTO_ASSIGN=24, LINK_PROTOCOL=25, LINK_ADDRESS=26, NUMBER=27, STRING=28, 
		NULL=29, ID=30, WS=31;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'{'", "')'", "','", "'('", "':'", "'}'", "';'", "'$'", "'procedure'", 
		"'if'", "'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", 
		"'continue'", "'.'", "'..'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", 
		"'=>'", "AUTO_ASSIGN", "LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", 
		"'null'", "ID", "WS"
	};
	public static final String[] ruleNames = {
		"T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "PROCEDURE", 
		"IF", "ELSE", "WHILE", "FOR", "RETURN", "BOOLEAN", "BREAK", "CONTINUE", 
		"DOT", "DOTDOT", "LINE_COMMENT", "BLOCK_COMMENT", "REF_ASSIGN", "OWN_ASSIGN", 
		"AUTO_ASSIGN", "LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", "NULL", 
		"ID", "WS"
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
		case 30: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: _channel = WHITESPACE;  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2!\u0107\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20~\n\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u0098\n\25\f\25\16\25\u009b"+
		"\13\25\3\25\5\25\u009e\n\25\3\25\3\25\3\26\3\26\3\26\3\26\7\26\u00a6\n"+
		"\26\f\26\16\26\u00a9\13\26\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\5\31\u00b7\n\31\3\32\6\32\u00ba\n\32\r\32\16\32\u00bb"+
		"\3\32\3\32\3\32\3\32\3\33\6\33\u00c3\n\33\r\33\16\33\u00c4\3\33\3\33\6"+
		"\33\u00c9\n\33\r\33\16\33\u00ca\3\33\3\33\6\33\u00cf\n\33\r\33\16\33\u00d0"+
		"\7\33\u00d3\n\33\f\33\16\33\u00d6\13\33\3\34\6\34\u00d9\n\34\r\34\16\34"+
		"\u00da\3\34\6\34\u00de\n\34\r\34\16\34\u00df\3\34\3\34\6\34\u00e4\n\34"+
		"\r\34\16\34\u00e5\5\34\u00e8\n\34\3\35\3\35\3\35\3\35\7\35\u00ee\n\35"+
		"\f\35\16\35\u00f1\13\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\37\3\37\7"+
		"\37\u00fc\n\37\f\37\16\37\u00ff\13\37\3 \6 \u0102\n \r \16 \u0103\3 \3"+
		" \4\u0099\u00a7!\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23"+
		"\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1"+
		"\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1"+
		";\37\1= \1?!\2\3\2\t\5\2\62;C\\c|\6\2\60\60\62;C\\c|\3\2\62;\3\2$$\5\2"+
		"C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\u0118\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3"+
		"\2\2\2\3A\3\2\2\2\5C\3\2\2\2\7E\3\2\2\2\tG\3\2\2\2\13I\3\2\2\2\rK\3\2"+
		"\2\2\17M\3\2\2\2\21O\3\2\2\2\23Q\3\2\2\2\25[\3\2\2\2\27^\3\2\2\2\31c\3"+
		"\2\2\2\33i\3\2\2\2\35m\3\2\2\2\37}\3\2\2\2!\177\3\2\2\2#\u0085\3\2\2\2"+
		"%\u008e\3\2\2\2\'\u0090\3\2\2\2)\u0093\3\2\2\2+\u00a1\3\2\2\2-\u00ad\3"+
		"\2\2\2/\u00b0\3\2\2\2\61\u00b6\3\2\2\2\63\u00b9\3\2\2\2\65\u00c2\3\2\2"+
		"\2\67\u00e7\3\2\2\29\u00e9\3\2\2\2;\u00f4\3\2\2\2=\u00f9\3\2\2\2?\u0101"+
		"\3\2\2\2AB\7}\2\2B\4\3\2\2\2CD\7+\2\2D\6\3\2\2\2EF\7.\2\2F\b\3\2\2\2G"+
		"H\7*\2\2H\n\3\2\2\2IJ\7<\2\2J\f\3\2\2\2KL\7\177\2\2L\16\3\2\2\2MN\7=\2"+
		"\2N\20\3\2\2\2OP\7&\2\2P\22\3\2\2\2QR\7r\2\2RS\7t\2\2ST\7q\2\2TU\7e\2"+
		"\2UV\7g\2\2VW\7f\2\2WX\7w\2\2XY\7t\2\2YZ\7g\2\2Z\24\3\2\2\2[\\\7k\2\2"+
		"\\]\7h\2\2]\26\3\2\2\2^_\7g\2\2_`\7n\2\2`a\7u\2\2ab\7g\2\2b\30\3\2\2\2"+
		"cd\7y\2\2de\7j\2\2ef\7k\2\2fg\7n\2\2gh\7g\2\2h\32\3\2\2\2ij\7h\2\2jk\7"+
		"q\2\2kl\7t\2\2l\34\3\2\2\2mn\7t\2\2no\7g\2\2op\7v\2\2pq\7w\2\2qr\7t\2"+
		"\2rs\7p\2\2s\36\3\2\2\2tu\7v\2\2uv\7t\2\2vw\7w\2\2w~\7g\2\2xy\7h\2\2y"+
		"z\7c\2\2z{\7n\2\2{|\7u\2\2|~\7g\2\2}t\3\2\2\2}x\3\2\2\2~ \3\2\2\2\177"+
		"\u0080\7d\2\2\u0080\u0081\7t\2\2\u0081\u0082\7g\2\2\u0082\u0083\7c\2\2"+
		"\u0083\u0084\7m\2\2\u0084\"\3\2\2\2\u0085\u0086\7e\2\2\u0086\u0087\7q"+
		"\2\2\u0087\u0088\7p\2\2\u0088\u0089\7v\2\2\u0089\u008a\7k\2\2\u008a\u008b"+
		"\7p\2\2\u008b\u008c\7w\2\2\u008c\u008d\7g\2\2\u008d$\3\2\2\2\u008e\u008f"+
		"\7\60\2\2\u008f&\3\2\2\2\u0090\u0091\7\60\2\2\u0091\u0092\7\60\2\2\u0092"+
		"(\3\2\2\2\u0093\u0094\7\61\2\2\u0094\u0095\7\61\2\2\u0095\u0099\3\2\2"+
		"\2\u0096\u0098\13\2\2\2\u0097\u0096\3\2\2\2\u0098\u009b\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2"+
		"\2\2\u009c\u009e\7\17\2\2\u009d\u009c\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u009f\3\2\2\2\u009f\u00a0\7\f\2\2\u00a0*\3\2\2\2\u00a1\u00a2\7\61\2\2"+
		"\u00a2\u00a3\7,\2\2\u00a3\u00a7\3\2\2\2\u00a4\u00a6\13\2\2\2\u00a5\u00a4"+
		"\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a8"+
		"\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\7,\2\2\u00ab\u00ac\7\61"+
		"\2\2\u00ac,\3\2\2\2\u00ad\u00ae\7/\2\2\u00ae\u00af\7@\2\2\u00af.\3\2\2"+
		"\2\u00b0\u00b1\7?\2\2\u00b1\u00b2\7@\2\2\u00b2\60\3\2\2\2\u00b3\u00b7"+
		"\7?\2\2\u00b4\u00b5\7\u0080\2\2\u00b5\u00b7\7@\2\2\u00b6\u00b3\3\2\2\2"+
		"\u00b6\u00b4\3\2\2\2\u00b7\62\3\2\2\2\u00b8\u00ba\t\2\2\2\u00b9\u00b8"+
		"\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc"+
		"\u00bd\3\2\2\2\u00bd\u00be\7<\2\2\u00be\u00bf\7\61\2\2\u00bf\u00c0\7\61"+
		"\2\2\u00c0\64\3\2\2\2\u00c1\u00c3\t\3\2\2\u00c2\u00c1\3\2\2\2\u00c3\u00c4"+
		"\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c8\7B\2\2\u00c7\u00c9\t\2\2\2\u00c8\u00c7\3\2\2\2\u00c9\u00ca\3\2"+
		"\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00d4\3\2\2\2\u00cc"+
		"\u00ce\7\60\2\2\u00cd\u00cf\t\2\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d0\3"+
		"\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d3\3\2\2\2\u00d2"+
		"\u00cc\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2"+
		"\2\2\u00d5\66\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00d9\t\4\2\2\u00d8\u00d7"+
		"\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db"+
		"\u00e8\3\2\2\2\u00dc\u00de\t\4\2\2\u00dd\u00dc\3\2\2\2\u00de\u00df\3\2"+
		"\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1"+
		"\u00e3\7\60\2\2\u00e2\u00e4\t\4\2\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3"+
		"\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7"+
		"\u00d8\3\2\2\2\u00e7\u00dd\3\2\2\2\u00e88\3\2\2\2\u00e9\u00ef\7$\2\2\u00ea"+
		"\u00eb\7^\2\2\u00eb\u00ee\7$\2\2\u00ec\u00ee\n\5\2\2\u00ed\u00ea\3\2\2"+
		"\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0"+
		"\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3\7$\2\2\u00f3"+
		":\3\2\2\2\u00f4\u00f5\7p\2\2\u00f5\u00f6\7w\2\2\u00f6\u00f7\7n\2\2\u00f7"+
		"\u00f8\7n\2\2\u00f8<\3\2\2\2\u00f9\u00fd\t\6\2\2\u00fa\u00fc\t\7\2\2\u00fb"+
		"\u00fa\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2"+
		"\2\2\u00fe>\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0102\t\b\2\2\u0101\u0100"+
		"\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104"+
		"\u0105\3\2\2\2\u0105\u0106\b \2\2\u0106@\3\2\2\2\25\2}\u0099\u009d\u00a7"+
		"\u00b6\u00bb\u00c4\u00ca\u00d0\u00d4\u00da\u00df\u00e5\u00e7\u00ed\u00ef"+
		"\u00fd\u0103";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}