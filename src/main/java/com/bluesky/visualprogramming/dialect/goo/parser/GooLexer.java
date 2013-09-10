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
		DOT=18, LINE_COMMENT=19, BLOCK_COMMENT=20, REF_ASSIGN=21, OWN_ASSIGN=22, 
		AUTO_ASSIGN=23, LINK_PROTOCOL=24, LINK_ADDRESS=25, NUMBER=26, STRING=27, 
		NULL=28, ID=29, WS=30;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'{'", "')'", "','", "'('", "':'", "'}'", "';'", "'$'", "'procedure'", 
		"'if'", "'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", 
		"'continue'", "'.'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", "'=>'", 
		"AUTO_ASSIGN", "LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", "'null'", 
		"ID", "WS"
	};
	public static final String[] ruleNames = {
		"T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "PROCEDURE", 
		"IF", "ELSE", "WHILE", "FOR", "RETURN", "BOOLEAN", "BREAK", "CONTINUE", 
		"DOT", "LINE_COMMENT", "BLOCK_COMMENT", "REF_ASSIGN", "OWN_ASSIGN", "AUTO_ASSIGN", 
		"LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", "NULL", "ID", "WS"
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
		case 29: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: _channel = WHITESPACE;  break;
		}
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\2 \u0102\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20|\n\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\7\24\u0093\n\24\f\24\16\24\u0096\13\24\3\24\5\24"+
		"\u0099\n\24\3\24\3\24\3\25\3\25\3\25\3\25\7\25\u00a1\n\25\f\25\16\25\u00a4"+
		"\13\25\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30\5\30"+
		"\u00b2\n\30\3\31\6\31\u00b5\n\31\r\31\16\31\u00b6\3\31\3\31\3\31\3\31"+
		"\3\32\6\32\u00be\n\32\r\32\16\32\u00bf\3\32\3\32\6\32\u00c4\n\32\r\32"+
		"\16\32\u00c5\3\32\3\32\6\32\u00ca\n\32\r\32\16\32\u00cb\7\32\u00ce\n\32"+
		"\f\32\16\32\u00d1\13\32\3\33\6\33\u00d4\n\33\r\33\16\33\u00d5\3\33\6\33"+
		"\u00d9\n\33\r\33\16\33\u00da\3\33\3\33\6\33\u00df\n\33\r\33\16\33\u00e0"+
		"\5\33\u00e3\n\33\3\34\3\34\3\34\3\34\7\34\u00e9\n\34\f\34\16\34\u00ec"+
		"\13\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\36\3\36\7\36\u00f7\n\36\f"+
		"\36\16\36\u00fa\13\36\3\37\6\37\u00fd\n\37\r\37\16\37\u00fe\3\37\3\37"+
		"\4\u0094\u00a2 \3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23"+
		"\13\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1"+
		"\'\25\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1"+
		";\37\1= \2\3\2\b\5\2\62;C\\c|\6\2\60\60\62;C\\c|\3\2\62;\3\2$$\5\2C\\"+
		"aac|\5\2\13\f\17\17\"\"\u0113\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\3?\3\2\2\2\5A\3\2\2\2\7"+
		"C\3\2\2\2\tE\3\2\2\2\13G\3\2\2\2\rI\3\2\2\2\17K\3\2\2\2\21M\3\2\2\2\23"+
		"O\3\2\2\2\25Y\3\2\2\2\27\\\3\2\2\2\31a\3\2\2\2\33g\3\2\2\2\35k\3\2\2\2"+
		"\37{\3\2\2\2!}\3\2\2\2#\u0083\3\2\2\2%\u008c\3\2\2\2\'\u008e\3\2\2\2)"+
		"\u009c\3\2\2\2+\u00a8\3\2\2\2-\u00ab\3\2\2\2/\u00b1\3\2\2\2\61\u00b4\3"+
		"\2\2\2\63\u00bd\3\2\2\2\65\u00e2\3\2\2\2\67\u00e4\3\2\2\29\u00ef\3\2\2"+
		"\2;\u00f4\3\2\2\2=\u00fc\3\2\2\2?@\7}\2\2@\4\3\2\2\2AB\7+\2\2B\6\3\2\2"+
		"\2CD\7.\2\2D\b\3\2\2\2EF\7*\2\2F\n\3\2\2\2GH\7<\2\2H\f\3\2\2\2IJ\7\177"+
		"\2\2J\16\3\2\2\2KL\7=\2\2L\20\3\2\2\2MN\7&\2\2N\22\3\2\2\2OP\7r\2\2PQ"+
		"\7t\2\2QR\7q\2\2RS\7e\2\2ST\7g\2\2TU\7f\2\2UV\7w\2\2VW\7t\2\2WX\7g\2\2"+
		"X\24\3\2\2\2YZ\7k\2\2Z[\7h\2\2[\26\3\2\2\2\\]\7g\2\2]^\7n\2\2^_\7u\2\2"+
		"_`\7g\2\2`\30\3\2\2\2ab\7y\2\2bc\7j\2\2cd\7k\2\2de\7n\2\2ef\7g\2\2f\32"+
		"\3\2\2\2gh\7h\2\2hi\7q\2\2ij\7t\2\2j\34\3\2\2\2kl\7t\2\2lm\7g\2\2mn\7"+
		"v\2\2no\7w\2\2op\7t\2\2pq\7p\2\2q\36\3\2\2\2rs\7v\2\2st\7t\2\2tu\7w\2"+
		"\2u|\7g\2\2vw\7h\2\2wx\7c\2\2xy\7n\2\2yz\7u\2\2z|\7g\2\2{r\3\2\2\2{v\3"+
		"\2\2\2| \3\2\2\2}~\7d\2\2~\177\7t\2\2\177\u0080\7g\2\2\u0080\u0081\7c"+
		"\2\2\u0081\u0082\7m\2\2\u0082\"\3\2\2\2\u0083\u0084\7e\2\2\u0084\u0085"+
		"\7q\2\2\u0085\u0086\7p\2\2\u0086\u0087\7v\2\2\u0087\u0088\7k\2\2\u0088"+
		"\u0089\7p\2\2\u0089\u008a\7w\2\2\u008a\u008b\7g\2\2\u008b$\3\2\2\2\u008c"+
		"\u008d\7\60\2\2\u008d&\3\2\2\2\u008e\u008f\7\61\2\2\u008f\u0090\7\61\2"+
		"\2\u0090\u0094\3\2\2\2\u0091\u0093\13\2\2\2\u0092\u0091\3\2\2\2\u0093"+
		"\u0096\3\2\2\2\u0094\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0098\3\2"+
		"\2\2\u0096\u0094\3\2\2\2\u0097\u0099\7\17\2\2\u0098\u0097\3\2\2\2\u0098"+
		"\u0099\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\7\f\2\2\u009b(\3\2\2\2"+
		"\u009c\u009d\7\61\2\2\u009d\u009e\7,\2\2\u009e\u00a2\3\2\2\2\u009f\u00a1"+
		"\13\2\2\2\u00a0\u009f\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a3\3\2\2\2"+
		"\u00a2\u00a0\3\2\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\u00a6"+
		"\7,\2\2\u00a6\u00a7\7\61\2\2\u00a7*\3\2\2\2\u00a8\u00a9\7/\2\2\u00a9\u00aa"+
		"\7@\2\2\u00aa,\3\2\2\2\u00ab\u00ac\7?\2\2\u00ac\u00ad\7@\2\2\u00ad.\3"+
		"\2\2\2\u00ae\u00b2\7?\2\2\u00af\u00b0\7\u0080\2\2\u00b0\u00b2\7@\2\2\u00b1"+
		"\u00ae\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\60\3\2\2\2\u00b3\u00b5\t\2\2"+
		"\2\u00b4\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7"+
		"\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\7<\2\2\u00b9\u00ba\7\61\2\2\u00ba"+
		"\u00bb\7\61\2\2\u00bb\62\3\2\2\2\u00bc\u00be\t\3\2\2\u00bd\u00bc\3\2\2"+
		"\2\u00be\u00bf\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c1"+
		"\3\2\2\2\u00c1\u00c3\7B\2\2\u00c2\u00c4\t\2\2\2\u00c3\u00c2\3\2\2\2\u00c4"+
		"\u00c5\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00cf\3\2"+
		"\2\2\u00c7\u00c9\7\60\2\2\u00c8\u00ca\t\2\2\2\u00c9\u00c8\3\2\2\2\u00ca"+
		"\u00cb\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ce\3\2"+
		"\2\2\u00cd\u00c7\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\64\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d4\t\4\2"+
		"\2\u00d3\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d6"+
		"\3\2\2\2\u00d6\u00e3\3\2\2\2\u00d7\u00d9\t\4\2\2\u00d8\u00d7\3\2\2\2\u00d9"+
		"\u00da\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dc\3\2"+
		"\2\2\u00dc\u00de\7\60\2\2\u00dd\u00df\t\4\2\2\u00de\u00dd\3\2\2\2\u00df"+
		"\u00e0\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\u00e3\3\2"+
		"\2\2\u00e2\u00d3\3\2\2\2\u00e2\u00d8\3\2\2\2\u00e3\66\3\2\2\2\u00e4\u00ea"+
		"\7$\2\2\u00e5\u00e6\7^\2\2\u00e6\u00e9\7$\2\2\u00e7\u00e9\n\5\2\2\u00e8"+
		"\u00e5\3\2\2\2\u00e8\u00e7\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2"+
		"\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ed\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ed"+
		"\u00ee\7$\2\2\u00ee8\3\2\2\2\u00ef\u00f0\7p\2\2\u00f0\u00f1\7w\2\2\u00f1"+
		"\u00f2\7n\2\2\u00f2\u00f3\7n\2\2\u00f3:\3\2\2\2\u00f4\u00f8\t\6\2\2\u00f5"+
		"\u00f7\t\2\2\2\u00f6\u00f5\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2"+
		"\2\2\u00f8\u00f9\3\2\2\2\u00f9<\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fd"+
		"\t\7\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe"+
		"\u00ff\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0101\b\37\2\2\u0101>\3\2\2\2"+
		"\25\2{\u0094\u0098\u00a2\u00b1\u00b6\u00bf\u00c5\u00cb\u00cf\u00d5\u00da"+
		"\u00e0\u00e2\u00e8\u00ea\u00f8\u00fe";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}