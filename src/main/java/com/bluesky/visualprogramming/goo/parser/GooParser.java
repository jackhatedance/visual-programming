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
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, PROCEDURE=7, IF=8, ELSE=9, 
		WHILE=10, FOR=11, RETURN=12, BOOLEAN=13, BREAK=14, CONTINUE=15, DOT=16, 
		LINE_COMMENT=17, BLOCK_COMMENT=18, REF_ASSIGN=19, OWN_ASSIGN=20, AUTO_ASSIGN=21, 
		ID=22, NUMBER=23, STRING=24, WS=25;
	public static final String[] tokenNames = {
		"<INVALID>", "'{'", "')'", "','", "'('", "'}'", "';'", "'procedure'", 
		"'if'", "'else'", "'while'", "'for'", "'return'", "BOOLEAN", "'break'", 
		"'continue'", "'.'", "LINE_COMMENT", "BLOCK_COMMENT", "'->'", "'=>'", 
		"'='", "ID", "NUMBER", "STRING", "WS"
	};
	public static final int
		RULE_procedure = 0, RULE_header = 1, RULE_paramDeclareList = 2, RULE_block = 3, 
		RULE_statement = 4, RULE_assignment = 5, RULE_assign_op = 6, RULE_assignee = 7, 
		RULE_expr = 8, RULE_constant = 9, RULE_variable = 10, RULE_field = 11, 
		RULE_messgeName = 12, RULE_paramList = 13, RULE_ifStatement = 14, RULE_whileStatement = 15, 
		RULE_forStatement = 16, RULE_returnStatement = 17, RULE_comment = 18;
	public static final String[] ruleNames = {
		"procedure", "header", "paramDeclareList", "block", "statement", "assignment", 
		"assign_op", "assignee", "expr", "constant", "variable", "field", "messgeName", 
		"paramList", "ifStatement", "whileStatement", "forStatement", "returnStatement", 
		"comment"
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
			setState(38); header();
			setState(39); block();
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
			setState(41); match(PROCEDURE);
			setState(42); match(ID);
			setState(43); match(4);
			setState(45);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(44); paramDeclareList();
				}
			}

			setState(47); match(2);
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
			setState(49); match(ID);
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==3) {
				{
				{
				setState(50); match(3);
				setState(51); match(ID);
				}
				}
				setState(56);
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
			setState(57); match(1);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << RETURN) | (1L << BOOLEAN) | (1L << BREAK) | (1L << CONTINUE) | (1L << LINE_COMMENT) | (1L << BLOCK_COMMENT) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0)) {
				{
				{
				setState(58); statement();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64); match(5);
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
			setState(84);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66); assignment();
				setState(67); match(6);
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69); expr(0);
				setState(70); match(6);
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(72); ifStatement();
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(73); whileStatement();
				}
				break;

			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(74); forStatement();
				}
				break;

			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(75); returnStatement();
				setState(76); match(6);
				}
				break;

			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(78); match(BREAK);
				setState(79); match(6);
				}
				break;

			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(80); match(CONTINUE);
				setState(81); match(6);
				}
				break;

			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(82); comment();
				}
				break;

			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(83); match(6);
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
		public Assign_opContext assign_op() {
			return getRuleContext(Assign_opContext.class,0);
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
			setState(86); assignee();
			setState(87); assign_op();
			setState(88); expr(0);
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

	public static class Assign_opContext extends ParserRuleContext {
		public TerminalNode REF_ASSIGN() { return getToken(GooParser.REF_ASSIGN, 0); }
		public TerminalNode OWN_ASSIGN() { return getToken(GooParser.OWN_ASSIGN, 0); }
		public TerminalNode AUTO_ASSIGN() { return getToken(GooParser.AUTO_ASSIGN, 0); }
		public Assign_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterAssign_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitAssign_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitAssign_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_opContext assign_op() throws RecognitionException {
		Assign_opContext _localctx = new Assign_opContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assign_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << REF_ASSIGN) | (1L << OWN_ASSIGN) | (1L << AUTO_ASSIGN))) != 0)) ) {
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
			setState(97);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new AssigneeVariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(92); match(ID);
				}
				break;

			case 2:
				_localctx = new AssigneeFieldContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(93); expr(0);
				setState(94); match(DOT);
				setState(95); field();
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
			setState(102);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case NUMBER:
			case STRING:
				{
				_localctx = new ConstantExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(100); constant();
				}
				break;
			case ID:
				{
				_localctx = new VariableExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(101); variable();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(118);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(116);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new AccessFieldContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(104);
						if (!(2 >= _localctx._p)) throw new FailedPredicateException(this, "2 >= $_p");
						setState(105); match(DOT);
						setState(106); field();
						}
						break;

					case 2:
						{
						_localctx = new SendMessageContext(new ExprContext(_parentctx, _parentState, _p));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(107);
						if (!(1 >= _localctx._p)) throw new FailedPredicateException(this, "1 >= $_p");
						setState(108); match(DOT);
						setState(109); messgeName();
						setState(110); match(4);
						setState(112);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << ID) | (1L << NUMBER) | (1L << STRING))) != 0)) {
							{
							setState(111); paramList();
							}
						}

						setState(114); match(2);
						}
						break;
					}
					} 
				}
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
			setState(124);
			switch (_input.LA(1)) {
			case NUMBER:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(121); match(NUMBER);
				}
				break;
			case STRING:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(122); match(STRING);
				}
				break;
			case BOOLEAN:
				_localctx = new BooleanContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(123); match(BOOLEAN);
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
			setState(126); match(ID);
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
			setState(128); match(ID);
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
			setState(130); match(ID);
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
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GooListener ) ((GooListener)listener).exitParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GooVisitor ) return ((GooVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132); expr(0);
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==3) {
				{
				{
				setState(133); match(3);
				setState(134); expr(0);
				}
				}
				setState(139);
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
		enterRule(_localctx, 28, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140); match(IF);
			setState(141); match(4);
			setState(142); expr(0);
			setState(143); match(2);
			setState(144); block();
			setState(147);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(145); match(ELSE);
				setState(146); block();
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
		enterRule(_localctx, 30, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149); match(WHILE);
			setState(150); match(4);
			setState(151); expr(0);
			setState(152); match(2);
			setState(153); block();
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
		enterRule(_localctx, 32, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155); match(FOR);
			setState(156); match(4);
			setState(159);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(157); assignment();
				}
				break;

			case 2:
				{
				setState(158); expr(0);
				}
				break;
			}
			setState(161); match(6);
			setState(162); expr(0);
			setState(163); match(6);
			setState(164); expr(0);
			setState(165); match(2);
			setState(166); block();
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
		enterRule(_localctx, 34, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); match(RETURN);
			setState(169); expr(0);
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
		enterRule(_localctx, 36, RULE_comment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
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
		"\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\33\u00b0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3\60\n\3\3\3\3\3\3"+
		"\4\3\4\3\4\7\4\67\n\4\f\4\16\4:\13\4\3\5\3\5\7\5>\n\5\f\5\16\5A\13\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6W\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\td\n\t"+
		"\3\n\3\n\3\n\5\ni\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\ns\n\n\3\n\3\n"+
		"\7\nw\n\n\f\n\16\nz\13\n\3\13\3\13\3\13\5\13\177\n\13\3\f\3\f\3\r\3\r"+
		"\3\16\3\16\3\17\3\17\3\17\7\17\u008a\n\17\f\17\16\17\u008d\13\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0096\n\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\5\22\u00a2\n\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\24\3\24\3\24\2\25\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&\2\4\3\2\25\27\3\2\23\24\u00b2\2(\3\2\2\2\4+\3\2\2\2\6\63"+
		"\3\2\2\2\b;\3\2\2\2\nV\3\2\2\2\fX\3\2\2\2\16\\\3\2\2\2\20c\3\2\2\2\22"+
		"h\3\2\2\2\24~\3\2\2\2\26\u0080\3\2\2\2\30\u0082\3\2\2\2\32\u0084\3\2\2"+
		"\2\34\u0086\3\2\2\2\36\u008e\3\2\2\2 \u0097\3\2\2\2\"\u009d\3\2\2\2$\u00aa"+
		"\3\2\2\2&\u00ad\3\2\2\2()\5\4\3\2)*\5\b\5\2*\3\3\2\2\2+,\7\t\2\2,-\7\30"+
		"\2\2-/\7\6\2\2.\60\5\6\4\2/.\3\2\2\2/\60\3\2\2\2\60\61\3\2\2\2\61\62\7"+
		"\4\2\2\62\5\3\2\2\2\638\7\30\2\2\64\65\7\5\2\2\65\67\7\30\2\2\66\64\3"+
		"\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29\7\3\2\2\2:8\3\2\2\2;?\7\3\2"+
		"\2<>\5\n\6\2=<\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3\2\2\2A?\3\2\2"+
		"\2BC\7\7\2\2C\t\3\2\2\2DE\5\f\7\2EF\7\b\2\2FW\3\2\2\2GH\5\22\n\2HI\7\b"+
		"\2\2IW\3\2\2\2JW\5\36\20\2KW\5 \21\2LW\5\"\22\2MN\5$\23\2NO\7\b\2\2OW"+
		"\3\2\2\2PQ\7\20\2\2QW\7\b\2\2RS\7\21\2\2SW\7\b\2\2TW\5&\24\2UW\7\b\2\2"+
		"VD\3\2\2\2VG\3\2\2\2VJ\3\2\2\2VK\3\2\2\2VL\3\2\2\2VM\3\2\2\2VP\3\2\2\2"+
		"VR\3\2\2\2VT\3\2\2\2VU\3\2\2\2W\13\3\2\2\2XY\5\20\t\2YZ\5\16\b\2Z[\5\22"+
		"\n\2[\r\3\2\2\2\\]\t\2\2\2]\17\3\2\2\2^d\7\30\2\2_`\5\22\n\2`a\7\22\2"+
		"\2ab\5\30\r\2bd\3\2\2\2c^\3\2\2\2c_\3\2\2\2d\21\3\2\2\2ef\b\n\1\2fi\5"+
		"\24\13\2gi\5\26\f\2he\3\2\2\2hg\3\2\2\2ix\3\2\2\2jk\6\n\2\3kl\7\22\2\2"+
		"lw\5\30\r\2mn\6\n\3\3no\7\22\2\2op\5\32\16\2pr\7\6\2\2qs\5\34\17\2rq\3"+
		"\2\2\2rs\3\2\2\2st\3\2\2\2tu\7\4\2\2uw\3\2\2\2vj\3\2\2\2vm\3\2\2\2wz\3"+
		"\2\2\2xv\3\2\2\2xy\3\2\2\2y\23\3\2\2\2zx\3\2\2\2{\177\7\31\2\2|\177\7"+
		"\32\2\2}\177\7\17\2\2~{\3\2\2\2~|\3\2\2\2~}\3\2\2\2\177\25\3\2\2\2\u0080"+
		"\u0081\7\30\2\2\u0081\27\3\2\2\2\u0082\u0083\7\30\2\2\u0083\31\3\2\2\2"+
		"\u0084\u0085\7\30\2\2\u0085\33\3\2\2\2\u0086\u008b\5\22\n\2\u0087\u0088"+
		"\7\5\2\2\u0088\u008a\5\22\n\2\u0089\u0087\3\2\2\2\u008a\u008d\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\35\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008e\u008f\7\n\2\2\u008f\u0090\7\6\2\2\u0090\u0091\5\22\n\2"+
		"\u0091\u0092\7\4\2\2\u0092\u0095\5\b\5\2\u0093\u0094\7\13\2\2\u0094\u0096"+
		"\5\b\5\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\37\3\2\2\2\u0097"+
		"\u0098\7\f\2\2\u0098\u0099\7\6\2\2\u0099\u009a\5\22\n\2\u009a\u009b\7"+
		"\4\2\2\u009b\u009c\5\b\5\2\u009c!\3\2\2\2\u009d\u009e\7\r\2\2\u009e\u00a1"+
		"\7\6\2\2\u009f\u00a2\5\f\7\2\u00a0\u00a2\5\22\n\2\u00a1\u009f\3\2\2\2"+
		"\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\7\b\2\2\u00a4\u00a5"+
		"\5\22\n\2\u00a5\u00a6\7\b\2\2\u00a6\u00a7\5\22\n\2\u00a7\u00a8\7\4\2\2"+
		"\u00a8\u00a9\5\b\5\2\u00a9#\3\2\2\2\u00aa\u00ab\7\16\2\2\u00ab\u00ac\5"+
		"\22\n\2\u00ac%\3\2\2\2\u00ad\u00ae\t\3\2\2\u00ae\'\3\2\2\2\17/8?Vchrv"+
		"x~\u008b\u0095\u00a1";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}