%{
	import java.io.*;
	import java.util.*;
%}

/* BYACC Declarations */
%token TK_CLASSE
%token <sval> TK_IDENTIFICADOR
%token TK_ABRECHAVES
%token TK_FECHACHAVES
%token TK_UNDERLINE
%token <sval> TK_VALOR_INTEIRO
%token <sval> TK_COMENTARIO
%token TK_VIRGULA
%token TK_INTEIRO
%token TK_FLOAT
%token TK_STRING
%token TK_FUNCAO_PRINCIPAL
%token TK_ABRECOLCHETE
%token TK_FECHACOLCHETE
%token TK_VETOR
%token TK_ABREPARENTESES
%token TK_FECHAPARENTESES

%type <sval> classe
%type <sval> tipoclasse
%type <sval> comandos
%type <sval> declaracoes
%type <sval> tipo
%type <sval> lista_variaveis
%type <sval> vetor
%type <sval> funcao
%type <sval> objeto

%%



programa : classe programa
	|
    ;

classe: tipoclasse { System.out.println($1); }

tipoclasse : TK_CLASSE TK_IDENTIFICADOR TK_ABRECHAVES comandos TK_FECHACHAVES { $$ = "\nclass " + $2 + " { \n" + $4 + "}\n "; }
	| TK_COMENTARIO { $$ = $1; }
	;

comandos : 	declaracoes comandos { $$ = $1 + $2; } 
	| 		declaracoes {$$ = $1; }
	|		{ $$ = ""; }
	| 		TK_COMENTARIO { $$ = "\t" + $1 + "\n"; }
	;

declaracoes : 	tipo lista_variaveis { $$ = "\t" + $1 +  $2 + "\n\t"; }
	| 			funcao 	{$$ = $1;}
	|			vetor 	{$$ = $1;}
	| 			TK_UNDERLINE TK_IDENTIFICADOR { $$ = "return "+ $2 + ";" + "\n\t"; }
	| 			TK_UNDERLINE TK_VALOR_INTEIRO { $$ = "return " + $2 + ";" + "\n\t"; }
	|			TK_COMENTARIO	{ $$ = "\t" + $1 + "\n"; }
	| 			{ $$ = ""; }
	;

lista_variaveis : 	TK_IDENTIFICADOR { $$ = $1 + ";"; }
	| 				TK_IDENTIFICADOR TK_VIRGULA lista_variaveis { $$ = $1 + ", " + $3; }
	;

tipo : 	TK_INTEIRO { $$ = "int "; }
	| 	TK_STRING { $$ = "string "; }
	| 	TK_FLOAT { $$ = "float "; }
	;

funcao :	TK_FUNCAO_PRINCIPAL { $$ = "\n\tpublic static void main (String[] args) \n\n";}	
	|		TK_FUNCAO_PRINCIPAL TK_ABRECHAVES comandos TK_FECHACHAVES { $$ = "\n\tpublic static void main (String[] args)" + "{ \n\t" + $3 + "}\n\n" ;}
	|		tipo TK_IDENTIFICADOR TK_ABREPARENTESES TK_FECHAPARENTESES TK_ABRECHAVES comandos TK_FECHACHAVES {$$ = "\t" + $1 + $2 + "(" + ")" + "{ \n\t" + $6 + "}\n" ;} 
	|		{ $$ = ""; }			
	;

vetor : TK_VETOR tipo TK_IDENTIFICADOR TK_ABRECOLCHETE TK_VALOR_INTEIRO TK_FECHACOLCHETE { $$ = $2 + $3 + " = new " + $2 + "[" + $5 + "];\n" ;}
	;



%%

	// Referencia ao JFlex
	private Yylex lexer;

	/* Interface com o JFlex */
	private int yylex(){
		int yyl_return = -1;
		try {
			yyl_return = lexer.yylex();
		} catch (IOException e) {
			System.err.println("Erro de IO: " + e);
		}
		return yyl_return;
	}

	/* Reporte de erro */
	public void yyerror(String error){
		System.err.println("Error: " + error);
	}

	// Interface com o JFlex eh criado no construtor
	public Parser(Reader r){
		lexer = new Yylex(r, this);
	}

	// Main
	public static void main(String[] args){
		try{ 
			Parser yyparser = new Parser(new FileReader(args[0]));
			yyparser.yyparse();
			} catch (IOException ex) {
				System.err.println("Error: " + ex);
			}
	}