/*
	generic object-oriented programming language
	
	a sample procedure:
	
	rtn = foo.bar()
	a.b.c().d().e.f()
	a
	a.b
	a.b()
	callback = foo:bar()
*/
grammar Goo;


@lexer::members {
    public static final int WHITESPACE = 1;
    public static final int COMMENTS = 2;
}

//this language don't support class

procedure : header block;	

header : PROCEDURE ID '(' paramDeclareList? ')';	

paramDeclareList : ID (',' ID)*;

block : '{' statement* '}';

statement : 
          assignmentStatement        
	| expressionStatement            
        | ifStatement     
        | whileStatement 
        | forStatement   
        | returnStatement   
        | breakStatement   
        | continueStatement  
        | comment               
        | emptyStatement
	;
	
emptyStatement : ';';
    
//varDeclare : 'var' varList;
//varList : ID (',' ID)*;
expressionStatement : expr ';';                     

assignmentStatement : assignment ';';
assignment : assignee assignOperator expr;

assignOperator : REF_ASSIGN  #RefAssignOperator
          | OWN_ASSIGN      #OwnAssignOperator
          | AUTO_ASSIGN     #AutoAssignOperator
          ;


assignee : ID		#AssigneeVariable
	| expr DOT field	#AssigneeField
	;

expr: constant				#ConstantExpr
    | variable                          #VariableExpr
    | expr DOT field              #AccessField
    | expr DOT messgeSubject '(' paramList? ')' #SendMessage
    ;

constant : 
 
         NUMBER			#Number
	| STRING			#String
	| BOOLEAN                      #Boolean 
	| NULL				#NullValue
        | link            #ObjectLink
        | procedure         #ProcedureConst
         ;

link : LINK_PROTOCOL  LINK_ADDRESS;

variable : ID;
field   :   ID          #IdField
            | STRING    #StringField
            ;
messgeSubject : ID         #IdMessageSubject
           |    STRING  #StringMessageSubject
           ;

paramList : expr (',' expr)*            #orderedParamList  
          | nameValue (',' nameValue)*  #namedParamList
          ;

nameValue : ID ':' expr;


ifStatement : IF '(' expr ')' trueBranch (ELSE falseBranch)?;
trueBranch : blockOrStatment;
falseBranch: blockOrStatment;

whileStatement : WHILE '(' expr ')' blockOrStatment;
forStatement : FOR '(' forInit? ';' forCondition ';' forAfterthought? ')' blockOrStatment;


forInit: assignment|expr;
forCondition : expr;
forAfterthought : expr;

blockOrStatment : block | statement;

returnStatement : RETURN expr ';';

breakStatement : BREAK ';';
continueStatement : CONTINUE ';';
                   
comment : LINE_COMMENT
        | BLOCK_COMMENT;




PROCEDURE : 'procedure';
    
IF : 'if';
ELSE:'else';
WHILE : 'while';
FOR : 'for';
RETURN : 'return';

BOOLEAN : 'true' | 'false';
BREAK: 'break';
CONTINUE : 'continue';



DOT : '.';
          
LINE_COMMENT : '//' .*? '\r'? '\n';
BLOCK_COMMENT : '/*' .*? '*/';


REF_ASSIGN : '->';
OWN_ASSIGN : '=>';
AUTO_ASSIGN: '='| '~>';
    

//LINK : [a-zA-Z0-9]+ '://' [a-zA-Z0-9.]+ '@' [a-zA-Z0-9]+ ('.' [a-zA-Z0-9]+)*;   
LINK_PROTOCOL : [a-zA-Z0-9]+ '://';
LINK_ADDRESS : [a-zA-Z0-9.]+ '@' [a-zA-Z0-9]+ ('.' [a-zA-Z0-9]+)*;
//LINK_ID : [a-zA-Z0-9.]+;
//LINK_DOMAIN : [a-zA-Z0-9]+ ('.' [a-zA-Z0-9]+)*;



NUMBER : [0-9]+
	| [0-9]+ '.' [0-9]+;
	
STRING : '"' ('\\"'|~["])* '"';	

NULL : 'null';

ID : [_a-zA-Z][a-zA-Z0-9]*;

//WS : [ \t\n\r]+ -> skip ;
 WS : [ \t\n\r]+ -> channel(WHITESPACE);
	
	