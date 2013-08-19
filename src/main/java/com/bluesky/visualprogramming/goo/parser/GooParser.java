// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.goo.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GooParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__6=1, T__5=2, T__4=3, T__3=4, T__2=5, T__1=6, T__0=7, PROCEDURE=8, IF=9, 
		ELSE=10, WHILE=11, FOR=12, RETURN=13, BOOLEAN=14, BREAK=15, CONTINUE=16, 
		DOT=17, LINE_COMMENT=18, BLOCK_COMMENT=19, REF_ASSIGN=20, OWN_ASSIGN=21, 
		AUTO_ASSIGN=22, ID=23, NUMBER=24, STRING=25, WS=26;
	public static final String[] tokenNames = {
		"<INVALID>", "'{'", "')'", "','", "'('", "':'", "'}'", "';'", "'procedure'", 
		"'if'", "'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", 
		"'continue'", "'.'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", "'=>'", 
		"AUTO_ASSIGN", "ID", "NUMBER", "STRING", "WS"
	};
	public static final int
		RULE_procedure = 0, RULE_header = 1, RULE_paramDeclareList = 2, RULE_block = 3, 
		RULE_statement = 4, RULE_assignment = 5, RULE_assignOperator = 6, RULE_assignee = 7, 
		RULE_expr = 8, RULE_constant = 9, RULE_variable = 10, RULE_field = 11, 
		RULE_messgeName = 12, RULE_paramList = 13, RULE_nameValue = 14, RULE_ifStatement = 15, 
		RULE_whileStatement = 16, RULE_forStatement = 17, RULE_returnStatement = 18, 
		RULE_comment = 19;
	public static final String[] ruleNames = {
		"procedure", "header", "paramDeclareList", "block", "statement", "assignment", 
		"assignOperator", "assignee", "expr", "constant", "variable", "field", 
		"messgeName", "paramList", "nameValue", "ifStatement", "whileStatement", 
		"forStatement", "returnStatement", "comment"
	};

	@Override
	public String getGrammarFileName() { return "Goo.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public GooParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProcedureContext extends ParserRuleContext {
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ProcedureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_procedure; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterProcedure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitProcedure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitProcedure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProcedureContext procedure() throws RecognitionException {
		ProcedureContext _localctx = new ProcedureContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_procedure);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40); header();
			setState(41); block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeaderContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public TerminalNode PROCEDURE() { return getToken(GooParser.PROCEDURE, 0); }
		public ParamDeclareListContext paramDeclareList() {
			return getRuleContext(ParamDeclareListContext.class,0);
		}
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_header);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); match(PROCEDURE);
			setState(44); match(ID);
			setState(45); match(4);
			setState(47);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(46); paramDeclareList();
				}
			}

			setState(49); match(2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamDeclareListContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(GooParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GooParser.ID, i);
		}
		public ParamDeclareListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramDeclareList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterParamDeclareList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitParamDeclareList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitParamDeclareList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamDeclareListContext paramDeclareList() throws RecognitionException {
		ParamDeclareListContext _localctx = new ParamDeclareListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramDeclareList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); match(ID);
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==3) {
				{
				{
				setState(52); match(3);
				setState(53); match(ID);
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59); match(1);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 7) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << RETURN) | (1L << BOOLEAN) | (1L << BREAK) | (1L << CONTINUE) | (1L << LINE_COMMENT) | (1L << BLOCK_COMMENT) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0)) {
				{
				{
				setState(60); statement();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66); match(6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public CommentContext comment() {
			return getRuleContext(CommentContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public TerminalNode BREAK() { return getToken(GooParser.BREAK, 0); }
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public TerminalNode CONTINUE() { return getToken(GooParser.CONTINUE, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement);
		try {
			setState(86);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(68); assignment();
				setState(69); match(7);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(71); expr(0);
				setState(72); match(7);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(74); ifStatement();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(75); whileStatement();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(76); forStatement();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(77); returnStatement();
				setState(78); match(7);
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(80); match(BREAK);
				setState(81); match(7);
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(82); match(CONTINUE);
				setState(83); match(7);
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(84); comment();
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(85); match(7);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssigneeContext assignee() {
			return getRuleContext(AssigneeContext.class,0);
		}
		public AssignOperatorContext assignOperator() {
			return getRuleContext(AssignOperatorContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88); assignee();
			setState(89); assignOperator();
			setState(90); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignOperatorContext extends ParserRuleContext {
		public AssignOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignOperator; }
	 
		public AssignOperatorContext() { }
		public void copyFrom(AssignOperatorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OwnAssignOperatorContext extends AssignOperatorContext {
		public TerminalNode OWN_ASSIGN() { return getToken(GooParser.OWN_ASSIGN, 0); }
		public OwnAssignOperatorContext(AssignOperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterOwnAssignOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitOwnAssignOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitOwnAssignOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RefAssignOperatorContext extends AssignOperatorContext {
		public TerminalNode REF_ASSIGN() { return getToken(GooParser.REF_ASSIGN, 0); }
		public RefAssignOperatorContext(AssignOperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterRefAssignOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitRefAssignOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitRefAssignOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AutoAssignOperatorContext extends AssignOperatorContext {
		public TerminalNode AUTO_ASSIGN() { return getToken(GooParser.AUTO_ASSIGN, 0); }
		public AutoAssignOperatorContext(AssignOperatorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAutoAssignOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAutoAssignOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAutoAssignOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignOperatorContext assignOperator() throws RecognitionException {
		AssignOperatorContext _localctx = new AssignOperatorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assignOperator);
		try {
			setState(95);
			switch (_input.LA(1)) {
			case REF_ASSIGN:
				_localctx = new RefAssignOperatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(92); match(REF_ASSIGN);
				}
				break;
			case OWN_ASSIGN:
				_localctx = new OwnAssignOperatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(93); match(OWN_ASSIGN);
				}
				break;
			case AUTO_ASSIGN:
				_localctx = new AutoAssignOperatorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(94); match(AUTO_ASSIGN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssigneeContext extends ParserRuleContext {
		public AssigneeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignee; }
	 
		public AssigneeContext() { }
		public void copyFrom(AssigneeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssigneeFieldContext extends AssigneeContext {
		public TerminalNode DOT() { return getToken(GooParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public AssigneeFieldContext(AssigneeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAssigneeField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAssigneeField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAssigneeField(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssigneeVariableContext extends AssigneeContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public AssigneeVariableContext(AssigneeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAssigneeVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAssigneeVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAssigneeVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssigneeContext assignee() throws RecognitionException {
		AssigneeContext _localctx = new AssigneeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assignee);
		try {
			setState(102);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new AssigneeVariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(97); match(ID);
				}
				break;

			case 2:
				_localctx = new AssigneeFieldContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(98); expr(0);
				setState(99); match(DOT);
				setState(100); field();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public int _p;
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, int _p) {
			super(parent, invokingState);
			this._p = _p;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
			this._p = ctx._p;
		}
	}
	public static class ConstantExprContext extends ExprContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ConstantExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterConstantExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitConstantExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitConstantExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableExprContext extends ExprContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public VariableExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterVariableExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitVariableExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitVariableExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SendMessageContext extends ExprContext {
		public TerminalNode DOT() { return getToken(GooParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public MessgeNameContext messgeName() {
			return getRuleContext(MessgeNameContext.class,0);
		}
		public SendMessageContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterSendMessage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitSendMessage(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitSendMessage(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AccessFieldContext extends ExprContext {
		public TerminalNode DOT() { return getToken(GooParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public AccessFieldContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAccessField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAccessField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAccessField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState, _p);
		ExprContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, RULE_expr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case NUMBER:
			case STRING:
				{
				_localctx = new ConstantExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(105); constant();
				}
				break;
			case ID:
				{
				_localctx = new VariableExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(106); variable();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(123);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(121);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new AccessFieldContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(109);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(110); match(DOT);
						setState(111); field();
						}
						break;

					case 2:
						{
						_localctx = new SendMessageContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(112);
						if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
						setState(113); match(DOT);
						setState(114); messgeName();
						setState(115); match(4);
						setState(117);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0)) {
							{
							setState(116); paramList();
							}
						}

						setState(119); match(2);
						}
						break;
					}
					} 
				}
				setState(125);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
	 
		public ConstantContext() { }
		public void copyFrom(ConstantContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NumberContext extends ConstantContext {
		public TerminalNode NUMBER() { return getToken(GooParser.NUMBER, 0); }
		public NumberContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringContext extends ConstantContext {
		public TerminalNode STRING() { return getToken(GooParser.STRING, 0); }
		public StringContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanContext extends ConstantContext {
		public TerminalNode BOOLEAN() { return getToken(GooParser.BOOLEAN, 0); }
		public BooleanContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitBoolean(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitBoolean(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_constant);
		try {
			setState(129);
			switch (_input.LA(1)) {
			case NUMBER:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(126); match(NUMBER);
				}
				break;
			case STRING:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(127); match(STRING);
				}
				break;
			case BOOLEAN:
				_localctx = new BooleanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(128); match(BOOLEAN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MessgeNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public MessgeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messgeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterMessgeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitMessgeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitMessgeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessgeNameContext messgeName() throws RecognitionException {
		MessgeNameContext _localctx = new MessgeNameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_messgeName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135); match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
	 
		public ParamListContext() { }
		public void copyFrom(ParamListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NamedParamListContext extends ParamListContext {
		public NameValueContext nameValue(int i) {
			return getRuleContext(NameValueContext.class,i);
		}
		public List<NameValueContext> nameValue() {
			return getRuleContexts(NameValueContext.class);
		}
		public NamedParamListContext(ParamListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterNamedParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitNamedParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitNamedParamList(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrderedParamListContext extends ParamListContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OrderedParamListContext(ParamListContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterOrderedParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitOrderedParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitOrderedParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_paramList);
		int _la;
		try {
			setState(153);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new OrderedParamListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(137); expr(0);
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==3) {
					{
					{
					setState(138); match(3);
					setState(139); expr(0);
					}
					}
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;

			case 2:
				_localctx = new NamedParamListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(145); nameValue();
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==3) {
					{
					{
					setState(146); match(3);
					setState(147); nameValue();
					}
					}
					setState(152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameValueContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public NameValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterNameValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitNameValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitNameValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameValueContext nameValue() throws RecognitionException {
		NameValueContext _localctx = new NameValueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_nameValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); match(ID);
			setState(156); match(5);
			setState(157); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GooParser.IF, 0); }
		public TerminalNode ELSE() { return getToken(GooParser.ELSE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159); match(IF);
			setState(160); match(4);
			setState(161); expr(0);
			setState(162); match(2);
			setState(163); block();
			setState(166);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(164); match(ELSE);
				setState(165); block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GooParser.WHILE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); match(WHILE);
			setState(169); match(4);
			setState(170); expr(0);
			setState(171); match(2);
			setState(172); block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForStatementContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode FOR() { return getToken(GooParser.FOR, 0); }
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174); match(FOR);
			setState(175); match(4);
			setState(178);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(176); assignment();
				}
				break;

			case 2:
				{
				setState(177); expr(0);
				}
				break;
			}
			setState(180); match(7);
			setState(181); expr(0);
			setState(182); match(7);
			setState(183); expr(0);
			setState(184); match(2);
			setState(185); block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(GooParser.RETURN, 0); }
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187); match(RETURN);
			setState(188); expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode LINE_COMMENT() { return getToken(GooParser.LINE_COMMENT, 0); }
		public TerminalNode BLOCK_COMMENT() { return getToken(GooParser.BLOCK_COMMENT, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitComment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitComment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			_la = _input.LA(1);
			if ( !(_la==LINE_COMMENT || _la==BLOCK_COMMENT) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 8: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return 2 >= _localctx._p;

		case 1: return 1 >= _localctx._p;
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\34\u00c3\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3\62\n\3"+
		"\3\3\3\3\3\4\3\4\3\4\7\49\n\4\f\4\16\4<\13\4\3\5\3\5\7\5@\n\5\f\5\16\5"+
		"C\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\5\6Y\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\5\bb\n\b\3\t\3\t"+
		"\3\t\3\t\3\t\5\ti\n\t\3\n\3\n\3\n\5\nn\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\5\nx\n\n\3\n\3\n\7\n|\n\n\f\n\16\n\177\13\n\3\13\3\13\3\13\5\13\u0084"+
		"\n\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\7\17\u008f\n\17\f\17\16"+
		"\17\u0092\13\17\3\17\3\17\3\17\7\17\u0097\n\17\f\17\16\17\u009a\13\17"+
		"\5\17\u009c\n\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\5\21\u00a9\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\5\23"+
		"\u00b5\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\2\26\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2\3\3\2\24\25"+
		"\u00c8\2*\3\2\2\2\4-\3\2\2\2\6\65\3\2\2\2\b=\3\2\2\2\nX\3\2\2\2\fZ\3\2"+
		"\2\2\16a\3\2\2\2\20h\3\2\2\2\22m\3\2\2\2\24\u0083\3\2\2\2\26\u0085\3\2"+
		"\2\2\30\u0087\3\2\2\2\32\u0089\3\2\2\2\34\u009b\3\2\2\2\36\u009d\3\2\2"+
		"\2 \u00a1\3\2\2\2\"\u00aa\3\2\2\2$\u00b0\3\2\2\2&\u00bd\3\2\2\2(\u00c0"+
		"\3\2\2\2*+\5\4\3\2+,\5\b\5\2,\3\3\2\2\2-.\7\n\2\2./\7\31\2\2/\61\7\6\2"+
		"\2\60\62\5\6\4\2\61\60\3\2\2\2\61\62\3\2\2\2\62\63\3\2\2\2\63\64\7\4\2"+
		"\2\64\5\3\2\2\2\65:\7\31\2\2\66\67\7\5\2\2\679\7\31\2\28\66\3\2\2\29<"+
		"\3\2\2\2:8\3\2\2\2:;\3\2\2\2;\7\3\2\2\2<:\3\2\2\2=A\7\3\2\2>@\5\n\6\2"+
		"?>\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\b\2\2"+
		"E\t\3\2\2\2FG\5\f\7\2GH\7\t\2\2HY\3\2\2\2IJ\5\22\n\2JK\7\t\2\2KY\3\2\2"+
		"\2LY\5 \21\2MY\5\"\22\2NY\5$\23\2OP\5&\24\2PQ\7\t\2\2QY\3\2\2\2RS\7\21"+
		"\2\2SY\7\t\2\2TU\7\22\2\2UY\7\t\2\2VY\5(\25\2WY\7\t\2\2XF\3\2\2\2XI\3"+
		"\2\2\2XL\3\2\2\2XM\3\2\2\2XN\3\2\2\2XO\3\2\2\2XR\3\2\2\2XT\3\2\2\2XV\3"+
		"\2\2\2XW\3\2\2\2Y\13\3\2\2\2Z[\5\20\t\2[\\\5\16\b\2\\]\5\22\n\2]\r\3\2"+
		"\2\2^b\7\26\2\2_b\7\27\2\2`b\7\30\2\2a^\3\2\2\2a_\3\2\2\2a`\3\2\2\2b\17"+
		"\3\2\2\2ci\7\31\2\2de\5\22\n\2ef\7\23\2\2fg\5\30\r\2gi\3\2\2\2hc\3\2\2"+
		"\2hd\3\2\2\2i\21\3\2\2\2jk\b\n\1\2kn\5\24\13\2ln\5\26\f\2mj\3\2\2\2ml"+
		"\3\2\2\2n}\3\2\2\2op\6\n\2\3pq\7\23\2\2q|\5\30\r\2rs\6\n\3\3st\7\23\2"+
		"\2tu\5\32\16\2uw\7\6\2\2vx\5\34\17\2wv\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz\7"+
		"\4\2\2z|\3\2\2\2{o\3\2\2\2{r\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2"+
		"~\23\3\2\2\2\177}\3\2\2\2\u0080\u0084\7\32\2\2\u0081\u0084\7\33\2\2\u0082"+
		"\u0084\7\20\2\2\u0083\u0080\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0082\3"+
		"\2\2\2\u0084\25\3\2\2\2\u0085\u0086\7\31\2\2\u0086\27\3\2\2\2\u0087\u0088"+
		"\7\31\2\2\u0088\31\3\2\2\2\u0089\u008a\7\31\2\2\u008a\33\3\2\2\2\u008b"+
		"\u0090\5\22\n\2\u008c\u008d\7\5\2\2\u008d\u008f\5\22\n\2\u008e\u008c\3"+
		"\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091"+
		"\u009c\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0098\5\36\20\2\u0094\u0095\7"+
		"\5\2\2\u0095\u0097\5\36\20\2\u0096\u0094\3\2\2\2\u0097\u009a\3\2\2\2\u0098"+
		"\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2"+
		"\2\2\u009b\u008b\3\2\2\2\u009b\u0093\3\2\2\2\u009c\35\3\2\2\2\u009d\u009e"+
		"\7\31\2\2\u009e\u009f\7\7\2\2\u009f\u00a0\5\22\n\2\u00a0\37\3\2\2\2\u00a1"+
		"\u00a2\7\13\2\2\u00a2\u00a3\7\6\2\2\u00a3\u00a4\5\22\n\2\u00a4\u00a5\7"+
		"\4\2\2\u00a5\u00a8\5\b\5\2\u00a6\u00a7\7\f\2\2\u00a7\u00a9\5\b\5\2\u00a8"+
		"\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9!\3\2\2\2\u00aa\u00ab\7\r\2\2"+
		"\u00ab\u00ac\7\6\2\2\u00ac\u00ad\5\22\n\2\u00ad\u00ae\7\4\2\2\u00ae\u00af"+
		"\5\b\5\2\u00af#\3\2\2\2\u00b0\u00b1\7\16\2\2\u00b1\u00b4\7\6\2\2\u00b2"+
		"\u00b5\5\f\7\2\u00b3\u00b5\5\22\n\2\u00b4\u00b2\3\2\2\2\u00b4\u00b3\3"+
		"\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\7\t\2\2\u00b7\u00b8\5\22\n\2\u00b8"+
		"\u00b9\7\t\2\2\u00b9\u00ba\5\22\n\2\u00ba\u00bb\7\4\2\2\u00bb\u00bc\5"+
		"\b\5\2\u00bc%\3\2\2\2\u00bd\u00be\7\17\2\2\u00be\u00bf\5\22\n\2\u00bf"+
		"\'\3\2\2\2\u00c0\u00c1\t\2\2\2\u00c1)\3\2\2\2\22\61:AXahmw{}\u0083\u0090"+
		"\u0098\u009b\u00a8\u00b4";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}