//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "Compiladores.y"
	import java.io.*;
	import java.util.*;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short TK_CLASSE=257;
public final static short TK_IDENTIFICADOR=258;
public final static short TK_ABRECHAVES=259;
public final static short TK_FECHACHAVES=260;
public final static short TK_UNDERLINE=261;
public final static short TK_VALOR_INTEIRO=262;
public final static short TK_COMENTARIO=263;
public final static short TK_VIRGULA=264;
public final static short TK_INTEIRO=265;
public final static short TK_FLOAT=266;
public final static short TK_STRING=267;
public final static short TK_FUNCAO_PRINCIPAL=268;
public final static short TK_ABRECOLCHETE=269;
public final static short TK_FECHACOLCHETE=270;
public final static short TK_VETOR=271;
public final static short TK_ABREPARENTESES=272;
public final static short TK_FECHAPARENTESES=273;
public final static short objeto=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    2,    2,    3,    3,    3,    3,    4,
    4,    4,    4,    4,    4,    4,    6,    6,    5,    5,
    5,    8,    8,    8,    8,    7,
};
final static short yylen[] = {                            2,
    2,    0,    1,    5,    1,    2,    1,    0,    1,    2,
    1,    1,    2,    2,    1,    0,    1,    3,    1,    1,
    1,    1,    4,    7,    0,    6,
};
final static short yydefred[] = {                         0,
    0,    5,    0,    0,    3,    0,    1,    0,    0,    0,
   19,   21,   20,    0,    0,    0,    0,    0,   12,   11,
   13,   14,    0,    0,    4,    6,    0,   10,    0,    0,
    0,    0,   23,    0,    0,   18,    0,    0,    0,   26,
    0,   24,
};
final static short yydgoto[] = {                          3,
    4,    5,   16,   17,   18,   28,   19,   20,
};
final static short yysindex[] = {                      -246,
 -257,    0,    0, -246,    0, -239,    0, -224, -222,    0,
    0,    0,    0, -228, -251, -215, -224, -212,    0,    0,
    0,    0, -224, -210,    0,    0, -260,    0, -211, -219,
 -207, -221,    0, -209, -208,    0, -205, -213, -224,    0,
 -202,    0,
};
final static short yyrindex[] = {                        55,
    0,    0,    0,   55,    0,    0,    0, -201,    0, -258,
    0,    0,    0, -242,    0,    0, -200,    0,    0,    0,
    0,    0, -201,    0,    0,    0, -233,    0,    0,    0,
    0,    0,    0,    0, -233,    0,    0,    0, -201,    0,
    0,    0,
};
final static short yygindex[] = {                        57,
    0,    0,  -17,    0,   47,   32,    0,    0,
};
final static int YYTABLESIZE=63;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
    6,    9,   15,   31,   15,   29,   15,   15,   15,   15,
    1,   32,   15,   11,   12,   13,    2,   22,   22,    8,
   22,   41,   22,   22,   22,   22,   17,   17,   22,   17,
   23,   17,   17,   17,   17,   21,    9,   17,   10,   22,
   11,   12,   13,   14,   25,   27,   15,   30,   33,   34,
   35,   37,   38,   39,    2,   31,   40,   42,    8,    7,
    7,   24,   36,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         17,
  258,  260,  261,  264,  263,   23,  265,  266,  267,  268,
  257,  272,  271,  265,  266,  267,  263,  260,  261,  259,
  263,   39,  265,  266,  267,  268,  260,  261,  271,  263,
  259,  265,  266,  267,  268,  258,  261,  271,  263,  262,
  265,  266,  267,  268,  260,  258,  271,  258,  260,  269,
  258,  273,  262,  259,    0,  264,  270,  260,  260,  260,
    4,   15,   31,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"TK_CLASSE","TK_IDENTIFICADOR","TK_ABRECHAVES","TK_FECHACHAVES",
"TK_UNDERLINE","TK_VALOR_INTEIRO","TK_COMENTARIO","TK_VIRGULA","TK_INTEIRO",
"TK_FLOAT","TK_STRING","TK_FUNCAO_PRINCIPAL","TK_ABRECOLCHETE",
"TK_FECHACOLCHETE","TK_VETOR","TK_ABREPARENTESES","TK_FECHAPARENTESES","objeto",
};
final static String yyrule[] = {
"$accept : programa",
"programa : classe programa",
"programa :",
"classe : tipoclasse",
"tipoclasse : TK_CLASSE TK_IDENTIFICADOR TK_ABRECHAVES comandos TK_FECHACHAVES",
"tipoclasse : TK_COMENTARIO",
"comandos : declaracoes comandos",
"comandos : declaracoes",
"comandos :",
"comandos : TK_COMENTARIO",
"declaracoes : tipo lista_variaveis",
"declaracoes : funcao",
"declaracoes : vetor",
"declaracoes : TK_UNDERLINE TK_IDENTIFICADOR",
"declaracoes : TK_UNDERLINE TK_VALOR_INTEIRO",
"declaracoes : TK_COMENTARIO",
"declaracoes :",
"lista_variaveis : TK_IDENTIFICADOR",
"lista_variaveis : TK_IDENTIFICADOR TK_VIRGULA lista_variaveis",
"tipo : TK_INTEIRO",
"tipo : TK_STRING",
"tipo : TK_FLOAT",
"funcao : TK_FUNCAO_PRINCIPAL",
"funcao : TK_FUNCAO_PRINCIPAL TK_ABRECHAVES comandos TK_FECHACHAVES",
"funcao : tipo TK_IDENTIFICADOR TK_ABREPARENTESES TK_FECHAPARENTESES TK_ABRECHAVES comandos TK_FECHACHAVES",
"funcao :",
"vetor : TK_VETOR tipo TK_IDENTIFICADOR TK_ABRECOLCHETE TK_VALOR_INTEIRO TK_FECHACOLCHETE",
};

//#line 85 "Compiladores.y"

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
//#line 261 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 43 "Compiladores.y"
{ System.out.println(val_peek(0).sval); }
break;
case 4:
//#line 45 "Compiladores.y"
{ yyval.sval = "\nclass " + val_peek(3).sval + " { \n" + val_peek(1).sval + "}\n "; }
break;
case 5:
//#line 46 "Compiladores.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 6:
//#line 49 "Compiladores.y"
{ yyval.sval = val_peek(1).sval + val_peek(0).sval; }
break;
case 7:
//#line 50 "Compiladores.y"
{yyval.sval = val_peek(0).sval; }
break;
case 8:
//#line 51 "Compiladores.y"
{ yyval.sval = ""; }
break;
case 9:
//#line 52 "Compiladores.y"
{ yyval.sval = "\t" + val_peek(0).sval + "\n"; }
break;
case 10:
//#line 55 "Compiladores.y"
{ yyval.sval = "\t" + val_peek(1).sval +  val_peek(0).sval + "\n\t"; }
break;
case 11:
//#line 56 "Compiladores.y"
{yyval.sval = val_peek(0).sval;}
break;
case 12:
//#line 57 "Compiladores.y"
{yyval.sval = val_peek(0).sval;}
break;
case 13:
//#line 58 "Compiladores.y"
{ yyval.sval = "return "+ val_peek(0).sval + ";" + "\n\t"; }
break;
case 14:
//#line 59 "Compiladores.y"
{ yyval.sval = "return " + val_peek(0).sval + ";" + "\n\t"; }
break;
case 15:
//#line 60 "Compiladores.y"
{ yyval.sval = "\t" + val_peek(0).sval + "\n"; }
break;
case 16:
//#line 61 "Compiladores.y"
{ yyval.sval = ""; }
break;
case 17:
//#line 64 "Compiladores.y"
{ yyval.sval = val_peek(0).sval + ";"; }
break;
case 18:
//#line 65 "Compiladores.y"
{ yyval.sval = val_peek(2).sval + ", " + val_peek(0).sval; }
break;
case 19:
//#line 68 "Compiladores.y"
{ yyval.sval = "int "; }
break;
case 20:
//#line 69 "Compiladores.y"
{ yyval.sval = "string "; }
break;
case 21:
//#line 70 "Compiladores.y"
{ yyval.sval = "float "; }
break;
case 22:
//#line 73 "Compiladores.y"
{ yyval.sval = "\n\tpublic static void main (String[] args) \n\n";}
break;
case 23:
//#line 74 "Compiladores.y"
{ yyval.sval = "\n\tpublic static void main (String[] args)" + "{ \n\t" + val_peek(1).sval + "}\n\n" ;}
break;
case 24:
//#line 75 "Compiladores.y"
{yyval.sval = "\t" + val_peek(6).sval + val_peek(5).sval + "(" + ")" + "{ \n\t" + val_peek(1).sval + "}\n" ;}
break;
case 25:
//#line 76 "Compiladores.y"
{ yyval.sval = ""; }
break;
case 26:
//#line 79 "Compiladores.y"
{ yyval.sval = val_peek(4).sval + val_peek(3).sval + " = new " + val_peek(4).sval + "[" + val_peek(1).sval + "];\n" ;}
break;
//#line 506 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
