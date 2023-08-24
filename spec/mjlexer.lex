package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"       { return new_symbol(sym.PROG, yytext()); }
"break"         { return new_symbol(sym.BREAK, yytext()); }
"class"         { return new_symbol(sym.CLASS, yytext()); }
"else"          { return new_symbol(sym.ELSE, yytext()); }
"const"         { return new_symbol(sym.CONST, yytext()); }
"if"            { return new_symbol(sym.IF, yytext()); }
"while"         { return new_symbol(sym.WHILE, yytext()); }
"new"           { return new_symbol(sym.NEW, yytext()); }
"print"         { return new_symbol(sym.PRINT, yytext()); }
"read"          { return new_symbol(sym.READ, yytext()); }
"return"        { return new_symbol(sym.RETURN, yytext()); }
"void"          { return new_symbol(sym.VOID, yytext()); }
"extends"       { return new_symbol(sym.EXTENDS, yytext()); }
"continue"      { return new_symbol(sym.CONTINUE, yytext()); }
"foreach"       { return new_symbol(sym.FOREACH, yytext()); }
"findAny"       { return new_symbol(sym.FINDANY, yytext()); }
"findAndReplace" { return new_symbol(sym.FINDANDREPLACE, yytext()); }
"max"		    { return new_symbol(sym.MAX, yytext()); }
"final"			{ return new_symbol(sym.FINAL, yytext()); }
"skip"          { return new_symbol(sym.SKIP, yytext()); }
"#"				{ return new_symbol(sym.HASH, yytext()); }
 
"+"             { return new_symbol(sym.PLUS, yytext()); }
"-"             { return new_symbol(sym.MINUS, yytext()); }
"*"             { return new_symbol(sym.MULT, yytext()); }
"/"             { return new_symbol(sym.DIV, yytext()); }
"%"             { return new_symbol(sym.MOD, yytext()); }
"=="            { return new_symbol(sym.EQEQ, yytext()); }
"!="            { return new_symbol(sym.NOTEQ, yytext()); }
">"             { return new_symbol(sym.GT, yytext()); }
">="            { return new_symbol(sym.GTEQ, yytext()); }
"<"             { return new_symbol(sym.LT, yytext()); }
"<="            { return new_symbol(sym.LTEQ, yytext()); }
"&&"            { return new_symbol(sym.AND, yytext()); }
"||"            { return new_symbol(sym.OR, yytext()); }
"="             { return new_symbol(sym.EQUAL, yytext()); }
"++"            { return new_symbol(sym.INCREMENT, yytext()); }
"--"            { return new_symbol(sym.DECREMENT, yytext()); }
";"             { return new_symbol(sym.SEMI, yytext()); }
":"             { return new_symbol(sym.COLON, yytext()); }
","             { return new_symbol(sym.COMMA, yytext()); }
"."             { return new_symbol(sym.DOT, yytext()); }
"("             { return new_symbol(sym.LPAREN, yytext()); }
")"             { return new_symbol(sym.RPAREN, yytext()); }
"["             { return new_symbol(sym.LBRACKET, yytext()); }
"]"             { return new_symbol(sym.RBRACKET, yytext()); }
"{"             { return new_symbol(sym.LBRACE, yytext()); }
"}"             { return new_symbol(sym.RBRACE, yytext()); }
"=>"            { return new_symbol(sym.ARROW, yytext()); }

<YYINITIAL> "//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
"'" [^\n\r]'    { return new_symbol(sym.CHARCONST, yytext()); }
"true"          { return new_symbol(sym.BOOLCONST, true); }
"false"         { return new_symbol(sym.BOOLCONST, false); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }






