// Generated from Goo.g4 by ANTLR 4.1
package com.bluesky.visualprogramming.dialect.goo.parser;
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
		T__7=1, T__6=2, T__5=3, T__4=4, T__3=5, T__2=6, T__1=7, T__0=8, PROCEDURE=9, 
		IF=10, ELSE=11, WHILE=12, FOR=13, RETURN=14, BOOLEAN=15, BREAK=16, CONTINUE=17, 
		DOT=18, DOTDOT=19, LINE_COMMENT=20, BLOCK_COMMENT=21, REF_ASSIGN=22, OWN_ASSIGN=23, 
		AUTO_ASSIGN=24, LINK_PROTOCOL=25, LINK_ADDRESS=26, NUMBER=27, STRING=28, 
		NULL=29, ID=30, WS=31;
	public static final String[] tokenNames = {
		"<INVALID>", "'{'", "')'", "','", "'('", "':'", "'}'", "';'", "'$'", "'procedure'", 
		"'if'", "'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", 
		"'continue'", "'.'", "'..'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", 
		"'=>'", "AUTO_ASSIGN", "LINK_PROTOCOL", "LINK_ADDRESS", "NUMBER", "STRING", 
		"'null'", "ID", "WS"
	};
	public static final int
		RULE_procedure = 0, RULE_header = 1, RULE_paramDeclareList = 2, RULE_block = 3, 
		RULE_statement = 4, RULE_emptyStatement = 5, RULE_expressionStatement = 6, 
		RULE_assignmentStatement = 7, RULE_assignment = 8, RULE_assignOperator = 9, 
		RULE_assignee = 10, RULE_expr = 11, RULE_constant = 12, RULE_link = 13, 
		RULE_object = 14, RULE_variable = 15, RULE_field = 16, RULE_messgeSubject = 17, 
		RULE_fieldList = 18, RULE_nameValue = 19, RULE_ifStatement = 20, RULE_trueBranch = 21, 
		RULE_falseBranch = 22, RULE_whileStatement = 23, RULE_forStatement = 24, 
		RULE_forInit = 25, RULE_forCondition = 26, RULE_forAfterthought = 27, 
		RULE_blockOrStatment = 28, RULE_returnStatement = 29, RULE_breakStatement = 30, 
		RULE_continueStatement = 31, RULE_comment = 32;
	public static final String[] ruleNames = {
		"procedure", "header", "paramDeclareList", "block", "statement", "emptyStatement", 
		"expressionStatement", "assignmentStatement", "assignment", "assignOperator", 
		"assignee", "expr", "constant", "link", "object", "variable", "field", 
		"messgeSubject", "fieldList", "nameValue", "ifStatement", "trueBranch", 
		"falseBranch", "whileStatement", "forStatement", "forInit", "forCondition", 
		"forAfterthought", "blockOrStatment", "returnStatement", "breakStatement", 
		"continueStatement", "comment"
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
			setState(66); header();
			setState(67); block();
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
			setState(69); match(PROCEDURE);
			setState(71);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(70); match(ID);
				}
			}

			setState(73); match(4);
			setState(75);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(74); paramDeclareList();
				}
			}

			setState(77); match(2);
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
			setState(79); match(ID);
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==3) {
				{
				{
				setState(80); match(3);
				setState(81); match(ID);
				}
				}
				setState(86);
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
			setState(87); match(1);
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 7) | (1L << PROCEDURE) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << RETURN) | (1L << BOOLEAN) | (1L << BREAK) | (1L << CONTINUE) | (1L << LINE_COMMENT) | (1L << BLOCK_COMMENT) | (1L << LINK_PROTOCOL) | (1L << NUMBER) | (1L << STRING) | (1L << NULL) | (1L << ID))) != 0)) {
				{
				{
				setState(88); statement();
				}
				}
				setState(93);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(94); match(6);
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
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public EmptyStatementContext emptyStatement() {
			return getRuleContext(EmptyStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
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
			setState(106);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(96); assignmentStatement();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(97); expressionStatement();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(98); ifStatement();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(99); whileStatement();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(100); forStatement();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(101); returnStatement();
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(102); breakStatement();
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(103); continueStatement();
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(104); comment();
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(105); emptyStatement();
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

	public static class EmptyStatementContext extends ParserRuleContext {
		public EmptyStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterEmptyStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitEmptyStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitEmptyStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyStatementContext emptyStatement() throws RecognitionException {
		EmptyStatementContext _localctx = new EmptyStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_emptyStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); match(7);
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

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110); expr(0);
			setState(111); match(7);
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

	public static class AssignmentStatementContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAssignmentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAssignmentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_assignmentStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113); assignment();
			setState(114); match(7);
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
		enterRule(_localctx, 16, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116); assignee();
			setState(117); assignOperator();
			setState(118); expr(0);
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
		enterRule(_localctx, 18, RULE_assignOperator);
		try {
			setState(123);
			switch (_input.LA(1)) {
			case REF_ASSIGN:
				_localctx = new RefAssignOperatorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(120); match(REF_ASSIGN);
				}
				break;
			case OWN_ASSIGN:
				_localctx = new OwnAssignOperatorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(121); match(OWN_ASSIGN);
				}
				break;
			case AUTO_ASSIGN:
				_localctx = new AutoAssignOperatorContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(122); match(AUTO_ASSIGN);
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
		enterRule(_localctx, 20, RULE_assignee);
		try {
			setState(130);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new AssigneeVariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(125); match(ID);
				}
				break;

			case 2:
				_localctx = new AssigneeFieldContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(126); expr(0);
				setState(127); match(DOT);
				setState(128); field();
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
		public TerminalNode DOTDOT() { return getToken(GooParser.DOTDOT, 0); }
		public TerminalNode DOT() { return getToken(GooParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MessgeSubjectContext messgeSubject() {
			return getRuleContext(MessgeSubjectContext.class,0);
		}
		public FieldListContext fieldList() {
			return getRuleContext(FieldListContext.class,0);
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
		int _startState = 22;
		enterRecursionRule(_localctx, RULE_expr);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			switch (_input.LA(1)) {
			case 1:
			case PROCEDURE:
			case BOOLEAN:
			case LINK_PROTOCOL:
			case NUMBER:
			case STRING:
			case NULL:
				{
				_localctx = new ConstantExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(133); constant();
				}
				break;
			case ID:
				{
				_localctx = new VariableExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134); variable();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(149);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new AccessFieldContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(137);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(138); match(DOT);
						setState(139); field();
						}
						break;

					case 2:
						{
						_localctx = new SendMessageContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(140);
						if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
						setState(141);
						_la = _input.LA(1);
						if ( !(_la==DOT || _la==DOTDOT) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(142); messgeSubject();
						setState(143); match(4);
						setState(145);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << PROCEDURE) | (1L << BOOLEAN) | (1L << LINK_PROTOCOL) | (1L << NUMBER) | (1L << STRING) | (1L << NULL) | (1L << ID))) != 0)) {
							{
							setState(144); fieldList();
							}
						}

						setState(147); match(2);
						}
						break;
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
	public static class ObjectLinkContext extends ConstantContext {
		public LinkContext link() {
			return getRuleContext(LinkContext.class,0);
		}
		public ObjectLinkContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterObjectLink(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitObjectLink(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitObjectLink(this);
			else return visitor.visitChildren(this);
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
	public static class ObjectConstContext extends ConstantContext {
		public ObjectContext object() {
			return getRuleContext(ObjectContext.class,0);
		}
		public ObjectConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterObjectConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitObjectConst(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitObjectConst(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullValueContext extends ConstantContext {
		public TerminalNode NULL() { return getToken(GooParser.NULL, 0); }
		public NullValueContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterNullValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitNullValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitNullValue(this);
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
	public static class ProcedureConstContext extends ConstantContext {
		public ProcedureContext procedure() {
			return getRuleContext(ProcedureContext.class,0);
		}
		public ProcedureConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterProcedureConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitProcedureConst(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitProcedureConst(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_constant);
		try {
			setState(161);
			switch (_input.LA(1)) {
			case NUMBER:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(154); match(NUMBER);
				}
				break;
			case STRING:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(155); match(STRING);
				}
				break;
			case BOOLEAN:
				_localctx = new BooleanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(156); match(BOOLEAN);
				}
				break;
			case NULL:
				_localctx = new NullValueContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(157); match(NULL);
				}
				break;
			case LINK_PROTOCOL:
				_localctx = new ObjectLinkContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(158); link();
				}
				break;
			case PROCEDURE:
				_localctx = new ProcedureConstContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(159); procedure();
				}
				break;
			case 1:
				_localctx = new ObjectConstContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(160); object();
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

	public static class LinkContext extends ParserRuleContext {
		public TerminalNode LINK_PROTOCOL() { return getToken(GooParser.LINK_PROTOCOL, 0); }
		public TerminalNode LINK_ADDRESS() { return getToken(GooParser.LINK_ADDRESS, 0); }
		public LinkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_link; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterLink(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitLink(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitLink(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinkContext link() throws RecognitionException {
		LinkContext _localctx = new LinkContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_link);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163); match(LINK_PROTOCOL);
			setState(164); match(LINK_ADDRESS);
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

	public static class ObjectContext extends ParserRuleContext {
		public FieldListContext fieldList() {
			return getRuleContext(FieldListContext.class,0);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_object);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166); match(1);
			setState(168);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << PROCEDURE) | (1L << BOOLEAN) | (1L << LINK_PROTOCOL) | (1L << NUMBER) | (1L << STRING) | (1L << NULL) | (1L << ID))) != 0)) {
				{
				setState(167); fieldList();
				}
			}

			setState(170); match(6);
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
		enterRule(_localctx, 30, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172); match(ID);
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
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
	 
		public FieldContext() { }
		public void copyFrom(FieldContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdFieldContext extends FieldContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public IdFieldContext(FieldContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterIdField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitIdField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitIdField(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringFieldContext extends FieldContext {
		public TerminalNode STRING() { return getToken(GooParser.STRING, 0); }
		public StringFieldContext(FieldContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterStringField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitStringField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitStringField(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarFieldContext extends FieldContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public VarFieldContext(FieldContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterVarField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitVarField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitVarField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_field);
		try {
			setState(178);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new IdFieldContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(174); match(ID);
				}
				break;
			case 8:
				_localctx = new VarFieldContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(175); match(8);
				setState(176); match(ID);
				}
				break;
			case STRING:
				_localctx = new StringFieldContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(177); match(STRING);
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

	public static class MessgeSubjectContext extends ParserRuleContext {
		public MessgeSubjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_messgeSubject; }
	 
		public MessgeSubjectContext() { }
		public void copyFrom(MessgeSubjectContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarMessageSubjectContext extends MessgeSubjectContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public VarMessageSubjectContext(MessgeSubjectContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterVarMessageSubject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitVarMessageSubject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitVarMessageSubject(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdMessageSubjectContext extends MessgeSubjectContext {
		public TerminalNode ID() { return getToken(GooParser.ID, 0); }
		public IdMessageSubjectContext(MessgeSubjectContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterIdMessageSubject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitIdMessageSubject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitIdMessageSubject(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringMessageSubjectContext extends MessgeSubjectContext {
		public TerminalNode STRING() { return getToken(GooParser.STRING, 0); }
		public StringMessageSubjectContext(MessgeSubjectContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterStringMessageSubject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitStringMessageSubject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitStringMessageSubject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MessgeSubjectContext messgeSubject() throws RecognitionException {
		MessgeSubjectContext _localctx = new MessgeSubjectContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_messgeSubject);
		try {
			setState(184);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new IdMessageSubjectContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(180); match(ID);
				}
				break;
			case 8:
				_localctx = new VarMessageSubjectContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(181); match(8);
				setState(182); match(ID);
				}
				break;
			case STRING:
				_localctx = new StringMessageSubjectContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(183); match(STRING);
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

	public static class FieldListContext extends ParserRuleContext {
		public FieldListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldList; }
	 
		public FieldListContext() { }
		public void copyFrom(FieldListContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NamedParamListContext extends FieldListContext {
		public NameValueContext nameValue(int i) {
			return getRuleContext(NameValueContext.class,i);
		}
		public List<NameValueContext> nameValue() {
			return getRuleContexts(NameValueContext.class);
		}
		public NamedParamListContext(FieldListContext ctx) { copyFrom(ctx); }
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
	public static class OrderedParamListContext extends FieldListContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OrderedParamListContext(FieldListContext ctx) { copyFrom(ctx); }
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

	public final FieldListContext fieldList() throws RecognitionException {
		FieldListContext _localctx = new FieldListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_fieldList);
		int _la;
		try {
			setState(202);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				_localctx = new OrderedParamListContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(186); expr(0);
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==3) {
					{
					{
					setState(187); match(3);
					setState(188); expr(0);
					}
					}
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;

			case 2:
				_localctx = new NamedParamListContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(194); nameValue();
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==3) {
					{
					{
					setState(195); match(3);
					setState(196); nameValue();
					}
					}
					setState(201);
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
		enterRule(_localctx, 38, RULE_nameValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204); match(ID);
			setState(205); match(5);
			setState(206); expr(0);
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
		public TrueBranchContext trueBranch() {
			return getRuleContext(TrueBranchContext.class,0);
		}
		public FalseBranchContext falseBranch() {
			return getRuleContext(FalseBranchContext.class,0);
		}
		public TerminalNode IF() { return getToken(GooParser.IF, 0); }
		public TerminalNode ELSE() { return getToken(GooParser.ELSE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 40, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208); match(IF);
			setState(209); match(4);
			setState(210); expr(0);
			setState(211); match(2);
			setState(212); trueBranch();
			setState(215);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(213); match(ELSE);
				setState(214); falseBranch();
				}
				break;
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

	public static class TrueBranchContext extends ParserRuleContext {
		public BlockOrStatmentContext blockOrStatment() {
			return getRuleContext(BlockOrStatmentContext.class,0);
		}
		public TrueBranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trueBranch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterTrueBranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitTrueBranch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitTrueBranch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrueBranchContext trueBranch() throws RecognitionException {
		TrueBranchContext _localctx = new TrueBranchContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_trueBranch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217); blockOrStatment();
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

	public static class FalseBranchContext extends ParserRuleContext {
		public BlockOrStatmentContext blockOrStatment() {
			return getRuleContext(BlockOrStatmentContext.class,0);
		}
		public FalseBranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_falseBranch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterFalseBranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitFalseBranch(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitFalseBranch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FalseBranchContext falseBranch() throws RecognitionException {
		FalseBranchContext _localctx = new FalseBranchContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_falseBranch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219); blockOrStatment();
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
		public BlockOrStatmentContext blockOrStatment() {
			return getRuleContext(BlockOrStatmentContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 46, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221); match(WHILE);
			setState(222); match(4);
			setState(223); expr(0);
			setState(224); match(2);
			setState(225); blockOrStatment();
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
		public ForAfterthoughtContext forAfterthought() {
			return getRuleContext(ForAfterthoughtContext.class,0);
		}
		public ForInitContext forInit() {
			return getRuleContext(ForInitContext.class,0);
		}
		public BlockOrStatmentContext blockOrStatment() {
			return getRuleContext(BlockOrStatmentContext.class,0);
		}
		public TerminalNode FOR() { return getToken(GooParser.FOR, 0); }
		public ForConditionContext forCondition() {
			return getRuleContext(ForConditionContext.class,0);
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
		enterRule(_localctx, 48, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227); match(FOR);
			setState(228); match(4);
			setState(230);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << PROCEDURE) | (1L << BOOLEAN) | (1L << LINK_PROTOCOL) | (1L << NUMBER) | (1L << STRING) | (1L << NULL) | (1L << ID))) != 0)) {
				{
				setState(229); forInit();
				}
			}

			setState(232); match(7);
			setState(233); forCondition();
			setState(234); match(7);
			setState(236);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << PROCEDURE) | (1L << BOOLEAN) | (1L << LINK_PROTOCOL) | (1L << NUMBER) | (1L << STRING) | (1L << NULL) | (1L << ID))) != 0)) {
				{
				setState(235); forAfterthought();
				}
			}

			setState(238); match(2);
			setState(239); blockOrStatment();
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

	public static class ForInitContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ForInitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterForInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitForInit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitForInit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForInitContext forInit() throws RecognitionException {
		ForInitContext _localctx = new ForInitContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_forInit);
		try {
			setState(243);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(241); assignment();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(242); expr(0);
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

	public static class ForConditionContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterForCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitForCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitForCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForConditionContext forCondition() throws RecognitionException {
		ForConditionContext _localctx = new ForConditionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_forCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245); expr(0);
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

	public static class ForAfterthoughtContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForAfterthoughtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forAfterthought; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterForAfterthought(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitForAfterthought(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitForAfterthought(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForAfterthoughtContext forAfterthought() throws RecognitionException {
		ForAfterthoughtContext _localctx = new ForAfterthoughtContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_forAfterthought);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247); expr(0);
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

	public static class BlockOrStatmentContext extends ParserRuleContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockOrStatmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockOrStatment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterBlockOrStatment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitBlockOrStatment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitBlockOrStatment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockOrStatmentContext blockOrStatment() throws RecognitionException {
		BlockOrStatmentContext _localctx = new BlockOrStatmentContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_blockOrStatment);
		try {
			setState(251);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(249); block();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(250); statement();
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
		enterRule(_localctx, 58, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253); match(RETURN);
			setState(254); expr(0);
			setState(255); match(7);
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

	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(GooParser.BREAK, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257); match(BREAK);
			setState(258); match(7);
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

	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(GooParser.CONTINUE, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260); match(CONTINUE);
			setState(261); match(7);
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
		enterRule(_localctx, 64, RULE_comment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(263);
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
		case 11: return expr_sempred((ExprContext)_localctx, predIndex);
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3!\u010c\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\3\2\3\2\3\3\3\3\5\3J\n\3\3\3\3\3\5\3N\n\3\3\3\3\3\3\4"+
		"\3\4\3\4\7\4U\n\4\f\4\16\4X\13\4\3\5\3\5\7\5\\\n\5\f\5\16\5_\13\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6m\n\6\3\7\3\7\3\b\3\b"+
		"\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\5\13~\n\13\3\f\3\f\3\f"+
		"\3\f\3\f\5\f\u0085\n\f\3\r\3\r\3\r\5\r\u008a\n\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\5\r\u0094\n\r\3\r\3\r\7\r\u0098\n\r\f\r\16\r\u009b\13\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00a4\n\16\3\17\3\17\3\17\3\20\3\20"+
		"\5\20\u00ab\n\20\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\5\22\u00b5\n"+
		"\22\3\23\3\23\3\23\3\23\5\23\u00bb\n\23\3\24\3\24\3\24\7\24\u00c0\n\24"+
		"\f\24\16\24\u00c3\13\24\3\24\3\24\3\24\7\24\u00c8\n\24\f\24\16\24\u00cb"+
		"\13\24\5\24\u00cd\n\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\3\26\5\26\u00da\n\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\32\3\32\3\32\5\32\u00e9\n\32\3\32\3\32\3\32\3\32\5\32\u00ef\n"+
		"\32\3\32\3\32\3\32\3\33\3\33\5\33\u00f6\n\33\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\5\36\u00fe\n\36\3\37\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3"+
		"\"\2#\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>"+
		"@B\2\4\3\2\24\25\3\2\26\27\u0111\2D\3\2\2\2\4G\3\2\2\2\6Q\3\2\2\2\bY\3"+
		"\2\2\2\nl\3\2\2\2\fn\3\2\2\2\16p\3\2\2\2\20s\3\2\2\2\22v\3\2\2\2\24}\3"+
		"\2\2\2\26\u0084\3\2\2\2\30\u0089\3\2\2\2\32\u00a3\3\2\2\2\34\u00a5\3\2"+
		"\2\2\36\u00a8\3\2\2\2 \u00ae\3\2\2\2\"\u00b4\3\2\2\2$\u00ba\3\2\2\2&\u00cc"+
		"\3\2\2\2(\u00ce\3\2\2\2*\u00d2\3\2\2\2,\u00db\3\2\2\2.\u00dd\3\2\2\2\60"+
		"\u00df\3\2\2\2\62\u00e5\3\2\2\2\64\u00f5\3\2\2\2\66\u00f7\3\2\2\28\u00f9"+
		"\3\2\2\2:\u00fd\3\2\2\2<\u00ff\3\2\2\2>\u0103\3\2\2\2@\u0106\3\2\2\2B"+
		"\u0109\3\2\2\2DE\5\4\3\2EF\5\b\5\2F\3\3\2\2\2GI\7\13\2\2HJ\7 \2\2IH\3"+
		"\2\2\2IJ\3\2\2\2JK\3\2\2\2KM\7\6\2\2LN\5\6\4\2ML\3\2\2\2MN\3\2\2\2NO\3"+
		"\2\2\2OP\7\4\2\2P\5\3\2\2\2QV\7 \2\2RS\7\5\2\2SU\7 \2\2TR\3\2\2\2UX\3"+
		"\2\2\2VT\3\2\2\2VW\3\2\2\2W\7\3\2\2\2XV\3\2\2\2Y]\7\3\2\2Z\\\5\n\6\2["+
		"Z\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2^`\3\2\2\2_]\3\2\2\2`a\7\b\2\2"+
		"a\t\3\2\2\2bm\5\20\t\2cm\5\16\b\2dm\5*\26\2em\5\60\31\2fm\5\62\32\2gm"+
		"\5<\37\2hm\5> \2im\5@!\2jm\5B\"\2km\5\f\7\2lb\3\2\2\2lc\3\2\2\2ld\3\2"+
		"\2\2le\3\2\2\2lf\3\2\2\2lg\3\2\2\2lh\3\2\2\2li\3\2\2\2lj\3\2\2\2lk\3\2"+
		"\2\2m\13\3\2\2\2no\7\t\2\2o\r\3\2\2\2pq\5\30\r\2qr\7\t\2\2r\17\3\2\2\2"+
		"st\5\22\n\2tu\7\t\2\2u\21\3\2\2\2vw\5\26\f\2wx\5\24\13\2xy\5\30\r\2y\23"+
		"\3\2\2\2z~\7\30\2\2{~\7\31\2\2|~\7\32\2\2}z\3\2\2\2}{\3\2\2\2}|\3\2\2"+
		"\2~\25\3\2\2\2\177\u0085\7 \2\2\u0080\u0081\5\30\r\2\u0081\u0082\7\24"+
		"\2\2\u0082\u0083\5\"\22\2\u0083\u0085\3\2\2\2\u0084\177\3\2\2\2\u0084"+
		"\u0080\3\2\2\2\u0085\27\3\2\2\2\u0086\u0087\b\r\1\2\u0087\u008a\5\32\16"+
		"\2\u0088\u008a\5 \21\2\u0089\u0086\3\2\2\2\u0089\u0088\3\2\2\2\u008a\u0099"+
		"\3\2\2\2\u008b\u008c\6\r\2\3\u008c\u008d\7\24\2\2\u008d\u0098\5\"\22\2"+
		"\u008e\u008f\6\r\3\3\u008f\u0090\t\2\2\2\u0090\u0091\5$\23\2\u0091\u0093"+
		"\7\6\2\2\u0092\u0094\5&\24\2\u0093\u0092\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0096\7\4\2\2\u0096\u0098\3\2\2\2\u0097\u008b\3\2"+
		"\2\2\u0097\u008e\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099"+
		"\u009a\3\2\2\2\u009a\31\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u00a4\7\35\2"+
		"\2\u009d\u00a4\7\36\2\2\u009e\u00a4\7\21\2\2\u009f\u00a4\7\37\2\2\u00a0"+
		"\u00a4\5\34\17\2\u00a1\u00a4\5\2\2\2\u00a2\u00a4\5\36\20\2\u00a3\u009c"+
		"\3\2\2\2\u00a3\u009d\3\2\2\2\u00a3\u009e\3\2\2\2\u00a3\u009f\3\2\2\2\u00a3"+
		"\u00a0\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a2\3\2\2\2\u00a4\33\3\2\2"+
		"\2\u00a5\u00a6\7\33\2\2\u00a6\u00a7\7\34\2\2\u00a7\35\3\2\2\2\u00a8\u00aa"+
		"\7\3\2\2\u00a9\u00ab\5&\24\2\u00aa\u00a9\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab"+
		"\u00ac\3\2\2\2\u00ac\u00ad\7\b\2\2\u00ad\37\3\2\2\2\u00ae\u00af\7 \2\2"+
		"\u00af!\3\2\2\2\u00b0\u00b5\7 \2\2\u00b1\u00b2\7\n\2\2\u00b2\u00b5\7 "+
		"\2\2\u00b3\u00b5\7\36\2\2\u00b4\u00b0\3\2\2\2\u00b4\u00b1\3\2\2\2\u00b4"+
		"\u00b3\3\2\2\2\u00b5#\3\2\2\2\u00b6\u00bb\7 \2\2\u00b7\u00b8\7\n\2\2\u00b8"+
		"\u00bb\7 \2\2\u00b9\u00bb\7\36\2\2\u00ba\u00b6\3\2\2\2\u00ba\u00b7\3\2"+
		"\2\2\u00ba\u00b9\3\2\2\2\u00bb%\3\2\2\2\u00bc\u00c1\5\30\r\2\u00bd\u00be"+
		"\7\5\2\2\u00be\u00c0\5\30\r\2\u00bf\u00bd\3\2\2\2\u00c0\u00c3\3\2\2\2"+
		"\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00cd\3\2\2\2\u00c3\u00c1"+
		"\3\2\2\2\u00c4\u00c9\5(\25\2\u00c5\u00c6\7\5\2\2\u00c6\u00c8\5(\25\2\u00c7"+
		"\u00c5\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2"+
		"\2\2\u00ca\u00cd\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cc\u00bc\3\2\2\2\u00cc"+
		"\u00c4\3\2\2\2\u00cd\'\3\2\2\2\u00ce\u00cf\7 \2\2\u00cf\u00d0\7\7\2\2"+
		"\u00d0\u00d1\5\30\r\2\u00d1)\3\2\2\2\u00d2\u00d3\7\f\2\2\u00d3\u00d4\7"+
		"\6\2\2\u00d4\u00d5\5\30\r\2\u00d5\u00d6\7\4\2\2\u00d6\u00d9\5,\27\2\u00d7"+
		"\u00d8\7\r\2\2\u00d8\u00da\5.\30\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2"+
		"\2\2\u00da+\3\2\2\2\u00db\u00dc\5:\36\2\u00dc-\3\2\2\2\u00dd\u00de\5:"+
		"\36\2\u00de/\3\2\2\2\u00df\u00e0\7\16\2\2\u00e0\u00e1\7\6\2\2\u00e1\u00e2"+
		"\5\30\r\2\u00e2\u00e3\7\4\2\2\u00e3\u00e4\5:\36\2\u00e4\61\3\2\2\2\u00e5"+
		"\u00e6\7\17\2\2\u00e6\u00e8\7\6\2\2\u00e7\u00e9\5\64\33\2\u00e8\u00e7"+
		"\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00eb\7\t\2\2\u00eb"+
		"\u00ec\5\66\34\2\u00ec\u00ee\7\t\2\2\u00ed\u00ef\58\35\2\u00ee\u00ed\3"+
		"\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\7\4\2\2\u00f1"+
		"\u00f2\5:\36\2\u00f2\63\3\2\2\2\u00f3\u00f6\5\22\n\2\u00f4\u00f6\5\30"+
		"\r\2\u00f5\u00f3\3\2\2\2\u00f5\u00f4\3\2\2\2\u00f6\65\3\2\2\2\u00f7\u00f8"+
		"\5\30\r\2\u00f8\67\3\2\2\2\u00f9\u00fa\5\30\r\2\u00fa9\3\2\2\2\u00fb\u00fe"+
		"\5\b\5\2\u00fc\u00fe\5\n\6\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe"+
		";\3\2\2\2\u00ff\u0100\7\20\2\2\u0100\u0101\5\30\r\2\u0101\u0102\7\t\2"+
		"\2\u0102=\3\2\2\2\u0103\u0104\7\22\2\2\u0104\u0105\7\t\2\2\u0105?\3\2"+
		"\2\2\u0106\u0107\7\23\2\2\u0107\u0108\7\t\2\2\u0108A\3\2\2\2\u0109\u010a"+
		"\t\3\2\2\u010aC\3\2\2\2\31IMV]l}\u0084\u0089\u0093\u0097\u0099\u00a3\u00aa"+
		"\u00b4\u00ba\u00c1\u00c9\u00cc\u00d9\u00e8\u00ee\u00f5\u00fd";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}