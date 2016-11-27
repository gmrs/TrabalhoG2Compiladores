import java.io.*;

%%

%byaccj

%{

	// Armazena uma referencia para o parser
	private Parser yyparser;

	// Construtor recebendo o parser como parametro adicional
	public Yylex(Reader r, Parser yyparser){
		this(r);
		this.yyparser = yyparser;
	}	

%}

NL = \n | \r | \r\n

%%

funcao_principal 		{ return Parser.TK_FUNCAO_PRINCIPAL; }
classe					{ return Parser.TK_CLASSE; }
inteiro              	{ return Parser.TK_INTEIRO; }
real                 	{ return Parser.TK_FLOAT; }
vetor					{ return Parser.TK_VETOR;}
"{"						{ return Parser.TK_ABRECHAVES; }
","						{ return Parser.TK_VIRGULA; }
"}" 					{ return Parser.TK_FECHACHAVES; }
"["						{ return Parser.TK_ABRECOLCHETE; }
"]"						{ return Parser.TK_FECHACOLCHETE; }
"("						{ return Parser.TK_ABREPARENTESES; }
")"						{ return Parser.TK_FECHAPARENTESES; }
[a-zA-Z][_a-zA-Z0-9]*	{ 	
							yyparser.yylval = new ParserVal(yytext());
							return Parser.TK_IDENTIFICADOR;
						}
"_"						{ return Parser.TK_UNDERLINE; }
-?[0-9]+             	{ 
							yyparser.yylval = new ParserVal(yytext());
							return Parser.TK_VALOR_INTEIRO;
						}
\/\/.*$              	{ 
							yyparser.yylval = new ParserVal(yytext());
							return Parser.TK_COMENTARIO;
						}
\".*\"              	{ 
							yyparser.yylval = new ParserVal(yytext());
							return Parser.TK_STRING;
						}
{NL}|" "|\t				{  }
