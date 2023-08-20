package rs.ac.bg.etf.pp1;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

    public static String callMethod(SyntaxNode instance, String methodName) {
        try {
            Method method = instance.getClass().getMethod(methodName);
            if (String.class.equals(method.getReturnType())) {
                return (String) method.invoke(instance);
            } else {
                return "Error: Method does not return a String";
            }
        } catch (NoSuchMethodException e) {
            return "Error: Method not found";
        } catch (Exception e) {
            // Handle other exceptions: e.g., IllegalAccessException, InvocationTargetException
            return "Error: " + e.getMessage();
        }
    }

    private Obj boolType;
    private Obj invalidType;
    public SemanticAnalyzer() {
        Tab.init();
        boolType = Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
        invalidType = Tab.insert(Obj.Type, "invalid", new Struct(Struct.None));
    }

    public void dumpState() {
        Tab.dump();
    }

    Logger log = Logger.getLogger(getClass().getSimpleName());

    public boolean errorDetected = false;

    private Obj currentProgram;

    private Struct currentType;

    public void report_error(String message, Object info) {
    	errorDetected = true;
    	StringBuilder msg = new StringBuilder(message); 
    	if (info instanceof Symbol)
            msg.append (" na liniji ").append(((Symbol)info).left);
        log.severe(msg.toString());
    }
    
    public void report_info(String message, Object info) {
    	StringBuilder msg = new StringBuilder(message); 
    	if (info instanceof Symbol)
            msg.append (" na liniji ").append(((Symbol)info).left);
        log.info(msg.toString());
    }

    @Override
    public void visit(Program program) {
        Tab.chainLocalSymbols(currentProgram);
        Tab.closeScope();
    }

    @Override
    public void visit(ProgramName programName) {
        currentProgram = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
        Tab.openScope();
    }

    /* Const and Var declarations */

    public boolean isAlreadyDeclared(String name) {
        if (currentMethod == null)
            return Tab.find(name) != Tab.noObj;
        
        return Tab.currentScope().findSymbol(name) != null;
    }

    public Obj find(String name) {

        if (!isAlreadyDeclared(name))
            throw new RuntimeException("Greska: Ime " + name + " nije deklarisano");

        if (currentMethod == null)
            return Tab.find(name);
        
        return Tab.currentScope().findSymbol(name);
    }

    @Override
    public void visit(Type type){
        Obj typeNode = Tab.find(type.getI1());
        if(typeNode == Tab.noObj){
            report_error("Nije pronadjen tip " + type.getI1() + " u tabeli simbola", null);
            currentType = Tab.noType;
        }
        if (typeNode.getKind() != Obj.Type) {
            report_error("Greska: Ime " + type.getI1() + " ne predstavlja tip ", type);
            currentType = Tab.noType;
        } else {
            currentType = typeNode.getType();
            report_info("Koriscenje tipa " + type.getI1(), type);
        }
    }

    @Override
    public void visit (NumConst_Single numConst){
        report_info("Deklarisana konstanta numericka", numConst);
        if (isAlreadyDeclared(numConst.getI1())) {
            report_error("Greska: Konstanta " + numConst.getI1() + " je vec deklarisana", numConst);
        } else if (currentType != Tab.intType) {
            report_error("Greska: Konstanta " + numConst.getI1() + " nije tipa int", numConst);
        } else {
            Obj constObj = Tab.insert(Obj.Con, numConst.getI1(), Tab.intType);
            constObj.setAdr(numConst.getN3());
        }
    }

    @Override
    public void visit (CharConst_Single charConst){
        report_info("Deklarisana konstanta char", charConst);
        if (isAlreadyDeclared(charConst.getI1())) {
            report_error("Greska: Konstanta " + charConst.getI1() + " je vec deklarisana", charConst);
        } else if (currentType != Tab.charType) {
            report_error("Greska: Konstanta " + charConst.getI1() + " nije tipa char", charConst);
        } else {
            Obj constObj = Tab.insert(Obj.Con, charConst.getI1(), Tab.charType);
            constObj.setAdr(charConst.getC3().charAt(0));
        }
    }

   @Override
   public void visit (BoolConst_Single boolConst){
       report_info("Deklarisana konstanta bool", boolConst);
       if (isAlreadyDeclared(boolConst.getI1())) {
           report_error("Greska: Konstanta " + boolConst.getI1() + " je vec deklarisana", boolConst);
       } else if (currentType != boolType.getType()) {
           report_error("Greska: Konstanta " + boolConst.getI1() + " nije tipa bool", boolConst);
       } else {
           Obj constObj = Tab.insert(Obj.Con, boolConst.getI1(), boolType.getType());
           constObj.setAdr(boolConst.getB3() ? 1 : 0);
       }
    }



    @Override 
    public void visit (Var_Single varDecl){
        if (isAlreadyDeclared(varDecl.getI1())) {
            report_error("Greska: Promenljiva " + varDecl.getI1() + " je vec deklarisana u ovom scopeu", varDecl);
        } else {
            Tab.insert(Obj.Var, varDecl.getI1(), currentType);
            report_info("Deklarisana promenljiva " + varDecl.getI1(), varDecl);
        }
    }

    @Override 
    public void visit (Var_Array varDecl){
        log.info("Deklarisan niz " + varDecl.getI1() + " tipa " + currentType.getKind());
        if (isAlreadyDeclared(varDecl.getI1())) {
            report_error("Greska: Promenljiva " + varDecl.getI1() + " je vec deklarisana", varDecl);
        } else {
            Tab.insert(Obj.Var, varDecl.getI1(), currentType);
            report_info("Deklarisana promenljiva " + varDecl.getI1(), new Struct(Struct.Array, currentType));
        }
    }

    /* Method declarations */
    private Obj currentMethod = null;
    private Struct currentMethodReturnType = Tab.noType;
    private boolean returnTypeExpected = false;
    @Override
    public void visit (ReturnType_Type returnType){
        Obj typeNode = Tab.find(returnType.getType().getI1());
        if(typeNode == Tab.noObj){
            report_error("Nije pronadjen tip " + returnType.getType().getI1() + " u tabeli simbola", null);
            currentMethodReturnType = Tab.noType;
        }
        if (typeNode.getKind() != Obj.Type) {
            report_error("Greska: Ime " + returnType.getType().getI1() + " ne predstavlja tip ", returnType);
            currentMethodReturnType = Tab.noType;
        } else {
            currentMethodReturnType = typeNode.getType();
            report_info("Koriscenje tipa " + returnType.getType().getI1(), returnType);
            returnTypeExpected = true;
        }
    }

    @Override
    public void visit (MethodName methodName){
        Obj obj = Tab.find(methodName.getI1());
        if (obj != Tab.noObj) {
            report_error("Greska: Metoda " + methodName.getI1() + " je vec deklarisana", methodName);
        } else {
            Tab.insert(Obj.Meth, methodName.getI1(), currentMethodReturnType);
            currentMethod = Tab.find(methodName.getI1());
            Tab.openScope();
            
            report_info("Obradjuje se funkcija " + methodName.getI1(), methodName);
        }
    }

    @Override
    public void visit (MethodDecl methodDecl){
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();
        currentMethod = Tab.noObj;
        returnTypeExpected = false;
        report_info("Zavrseno je obradjivanje funkcije " + methodDecl.getMethodName().getI1(), methodDecl);
    }

    public boolean isInMainMethod(){
        return currentMethod.getName().equals("main");
    }

    public void registerParam(Obj obj){
        obj.setFpPos(1);
        currentMethod.setLevel(currentMethod.getLevel() + 1);
    }

    @Override
    public void visit (Param_Single paramSingle){
        if (isAlreadyDeclared(paramSingle.getI2())) {
            report_error("Greska: Parametar / Varijabla " + paramSingle.getI2() + " je vec deklarisan", paramSingle);
        } else if(isInMainMethod()){
            report_error("Greska: Variabla ne moze biti prosledjena kao parametar main emthoda", paramSingle);
            
        } else {
            Obj obj =Tab.insert(Obj.Var, paramSingle.getI2(), currentType);
            registerParam(obj);
            report_info("Deklarisan parametar " + paramSingle.getI2(), paramSingle);
        }
    }

    @Override
    public void visit (Param_Array paramArray){
        if (isAlreadyDeclared(paramArray.getI2())) {
            report_error("Greska: Parametar / Varijabla " + paramArray.getI2() + " je vec deklarisan", paramArray);
        }else if(isInMainMethod()){
            report_error("Greska: Variabla ne moze biti prosledjena kao parametar main emthoda", paramArray);
        } else {
            Obj obj = Tab.insert(Obj.Var, paramArray.getI2(), new Struct(Struct.Array, currentType));
            registerParam(obj);
            report_info("Deklarisan parametar " + paramArray.getI2(), paramArray);
        }
    }

    /* Statements */
    
    /*
     * Statement = DesignatorStatement ";".
     */
    @Override
    public void visit (DesignatorStatement_ statement_DesignatorStatement){}

    /*
     * DesignatorStatement = Designator Assignop Expr ";".
        • Designator mora označavati promenljivu, element niza ili polje unutar objekta.
        • Tip neterminala Expr mora biti kompatibilan pri dodeli sa tipom neterminala Designator
     */

    @Override
    public void visit (DesignatorStatement_Assign statement_DesignatorStatement_Assign){
        Struct designatorType = getTypeOrNull(statement_DesignatorStatement_Assign.getDesignator().obj);
        Struct exprType = statement_DesignatorStatement_Assign.getExpr().struct;
        Assignop assignop = statement_DesignatorStatement_Assign.getAssignop();

        if (!designatorType.assignableTo(exprType)){
            report_error("Greska: Tipovi u dodeli se ne poklapaju na liniji: " + statement_DesignatorStatement_Assign.getLine(), assignop);
        }
    }
    /*
     * DesignatorStatement = Designator ("++" | "--") ";".
        • Designator mora označavati promenljivu, element niza ili polje objekta unutrašnje klase.
        • Designator mora biti tipa int. 
     */

    @Override
    public void visit (DesignatorStatement_Increment designatorStatement_Increment){
        Struct designatorType = designatorStatement_Increment.getDesignator().obj.getType();
        if (designatorType.getKind() != Struct.Int){
            report_error("Greska: Designator mora biti tipa int na liniji: " + designatorStatement_Increment.getLine(), designatorStatement_Increment);
        }
        int designatorKind = designatorStatement_Increment.getDesignator().obj.getKind();
        if (designatorKind != Obj.Var && designatorKind != Obj.Elem){
            report_error("Greska: Designator mora biti promenljiva, element niza ili polje objekta unutrasnje klase na liniji: " + designatorStatement_Increment.getLine(), designatorStatement_Increment);
        }
    }

    @Override
    public void visit (DesignatorStatement_Decrement designatorStatement_Decrement){
        Struct designatorType = designatorStatement_Decrement.getDesignator().obj.getType();
        if (designatorType.getKind() != Struct.Int){
            report_error("Greska: Designator mora biti tipa int na liniji: " + designatorStatement_Decrement.getLine(), designatorStatement_Decrement);
        }
        int designatorKind = designatorStatement_Decrement.getDesignator().obj.getKind();
        if (designatorKind != Obj.Var && designatorKind != Obj.Elem){
            report_error("Greska: Designator mora biti promenljiva, element niza ili polje objekta unutrasnje klase na liniji: " + designatorStatement_Decrement.getLine(), designatorStatement_Decrement);
        }
    }
    /*
     * DesignatorStatement = Designator "(" [ActPars] ")" ";".
        • Designator mora označavati nestatičku metodu unutrašnje klase ili globalnu funkciju glavnog
        programa. 
     */
    @Override
    public void visit (DesignatorStatement_ActPars designatorStatement_ActPars){
        if (designatorStatement_ActPars.getDesignator().obj != null || designatorStatement_ActPars.getDesignator().obj.getKind() != Obj.Meth){
            report_error("Greska: Designator mora označavati nestatičku metodu unutrašnje klase ili globalnu funkciju glavnog programa na liniji: " + designatorStatement_ActPars.getLine(), designatorStatement_ActPars);
        } 
    }

    /*
     * Statement = "break".
        • Iskaz break se može koristiti samo unutar while ili foreach petlje. Prekida izvršavanje neposredno
        okružujuće petlje
     */

    List<Boolean> loopStack = new ArrayList<Boolean>();

    private boolean isInLoop() {
        return loopStack.size() > 0;
    }

    @Override
    public void visit (BreakStatement breakStatement){
        if (!isInLoop()){
            report_error("Greska: Iskaz break se moze koristiti samo unutar while ili foreach petlje na liniji: " + breakStatement.getLine(), breakStatement);
        }
    }

    /*
     * Statement = "continue".
        • Iskaz continue se može koristiti samo unutar while ili foreach petlje. Nastavlja se sledeća iteracija
        neposredno okružujuće petlje. 
     */

    @Override
    public void visit (ContinueStatement continueStatement){
        if (!isInLoop()){
            report_error("Greska: Iskaz continue se moze koristiti samo unutar while ili foreach petlje na liniji: " + continueStatement.getLine(), continueStatement);
        }
    }

    /*
     * Statement = "read" "(" Designator ")" ";".
        • Designator mora označavati promenljivu, element niza ili polje unutar objekta.
        • Designator mora biti tipa int, char ili bool.
     */

     @Override
     public void visit(ReadStatement readStatement){
        Struct designatorType = readStatement.getDesignator().obj.getType();
        if (designatorType.getKind() != Struct.Int && designatorType.getKind() != Struct.Char && designatorType.getKind() != Struct.Bool){
            report_error("Greska: Designator mora biti tipa int, char ili bool na liniji: " + readStatement.getLine(), readStatement);
        }
        int designatorKind = readStatement.getDesignator().obj.getKind();
        if (designatorKind != Obj.Var && designatorKind != Obj.Elem){
            report_error("Greska: Designator mora biti promenljiva, element niza ili polje objekta unutrasnje klase na liniji: " + readStatement.getLine(), readStatement);
        }
     }
     
     /*
      * Statement = "print" "(" Expr ["," numConst] ")" ";".
        • Expr mora biti tipa int, char ili bool.
      */

    @Override
    public void visit(PrintStatement printStatement){
        Struct exprType = printStatement.getExpr().struct;
        if (exprType.getKind() != Struct.Int && exprType.getKind() != Struct.Char && exprType.getKind() != Struct.Bool){
            report_error("Greska: Expr mora biti tipa int, char ili bool na liniji: " + printStatement.getLine(), printStatement);
        }
    }

    /*
     * Statement = "return" [Expr] .
        • Tip neterminala Expr mora biti ekvivalentan povratnom tipu tekuće metode/ globalne funkcije.
        • Ako neterminal Expr nedostaje, tekuća metoda mora biti deklarisana kao void.
        • Ne sme postojati izvan tela (statičkih) metoda, odnosno globalnih funkcija. 
     */

    @Override
    public void visit(ReturnVoidStatement returnVoidStatement){
        if (returnTypeExpected){
            report_error("Greska: Return nema povratnu vrendost na liniji: " + returnVoidStatement.getLine(), returnVoidStatement);
        }
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        if (!returnTypeExpected){
            report_error("Greska: Return ima povratnu vrendost na liniji: " + returnStatement.getLine(), returnStatement);
        }
        Struct exprType = returnStatement.getExpr().struct;
        if (exprType.getKind() != currentMethodReturnType.getKind()){
            report_error("Greska: Tip neterminala Expr mora biti ekvivalentan povratnom tipu tekuće metode/ globalne funkcije na liniji: " + returnStatement.getLine(), returnStatement);
        }
    }
    

    /* Conditions */





    /*
     * Statement = "if" "(" Condition ")" Statement ["else" Statement].
        • Naredba if – ukoliko je vrednost uslovnog izraza Condition true, izvršavaju se naredbe u if grani, u
        suprotnom izvršavaju se naredbe u else grani, ako je navedena.
        • Tip uslovnog izraza Condition mora biti bool
     */

    @Override
    public void visit(IfStatement ifStatement){
        // Struct conditionType = ifStatement.getCondition().struct;
        // if (conditionType.getKind() != Struct.Bool){
        //     report_error("Greska: Tip uslovnog izraza Condition mora biti bool na liniji: " + ifStatement.getLine(), ifStatement);
        // } 
        // Nije potrebno jer je vec provereno u CondFact
    }

    /* Loops */
    
    /*
     * Statement = "while" "(" Condition ")" Statement .
        • Uslovni izraz Condition mora biti tipa bool.
        • Prilikom ulaska u petlju, kao i po završetku tela petlje (osim ukoliko se ne naiđe na break)
        proverava se zadati uslov. Ukoliko je uslov ispunjen skače se na početak petlje, dok se u
        suprotnom izlazi iz petlje. 
     */

     @Override 
     public void visit (WhileStart whileStart){
        loopStack.add(true);
     }

    @Override
    public void visit(WhileStatement whileStatement){
        // Struct conditionType = whileStatement.getCondition().struct;
        // if (conditionType.getKind() != Struct.Bool){
        //     report_error("Greska: Uslovni izraz Condition mora biti tipa bool na liniji: " + whileStatement.getLine(), whileStatement);
        // }
        // Nije potrebno jer je vec provereno u CondFact
        loopStack.remove(loopStack.size()-1);
    }

    /*
     * Statement = Designator "." "foreach" "(" ident "=>" Statement ")" ";".
        • Designator mora označavati niz proizvoljnog tipa.
        • ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi niza koji opisuje
        Designator.
        • U svakoj iteraciji petlje ident označava tekući element niza, pri čemu iteriranje počinje od prvog
        elementa niza i završava se sa poslednjim.
        • ident nije moguće koristiti za promenu vrednosti elementa niza – dozvoljeno je samo čitanje, ne i
        upis.
     */
    @Override
    public void visit (ForeachStart foreachStart){
        loopStack.add(true);
    }

    Struct currentForeachType = Tab.noType;

    @Override 
    public void visit (ForEachDesignator forEachDesignator){
        if (forEachDesignator.getDesignator().obj.getType().getKind() != Struct.Array){
            report_error("Greska: Designator mora označavati niz proizvoljnog tipa na liniji: " + forEachDesignator.getLine(), forEachDesignator);
        }
        currentForeachType = forEachDesignator.getDesignator().obj.getType().getElemType();
    }

    @Override
    public void visit (ForEachInterator forEachInterator){
        if (!isAlreadyDeclared(forEachInterator.getI1())){
            report_error("Greska: Ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi niza koji opisuje Designator na liniji: " + forEachInterator.getLine(), forEachInterator);
        }
        Obj iteratorObj = find(forEachInterator.getI1());
        if (iteratorObj.getType().getKind() != currentForeachType.getKind()){
            report_error("Greska: Ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi niza koji opisuje Designator na liniji: " + forEachInterator.getLine(), forEachInterator);
        }
    }
    /*
     * ActPars = Expr {"," Expr}.
        • Broj formalnih i stvarnih argumenata metode ili konstruktora mora biti isti.
        • Tip svakog stvarnog argumenta mora biti kompatibilan pri dodeli sa tipom svakog formalnog
        argumenta na odgovarajućoj poziciji. 
     */
    
    // TODO: Dodati ovo


     /*
      * CondTerm = CondFact {"&&" CondFact}.
      */

    

    /*
     * CondFact = Expr Relop Expr.
        • Tipovi oba izraza moraju biti kompatibilni.
        • Uz promenljive tipa klase ili niza, od relacionih operatora, mogu se koristiti samo != i ==. 
     */

    @Override
    public void visit (CondFact_ExprRelopExpr condFact_ExprRelopExpr){
        Struct expr0Type = condFact_ExprRelopExpr.getExpr().struct;
        Struct expr1Type = condFact_ExprRelopExpr.getExpr1().struct;
        Relop relop = condFact_ExprRelopExpr.getRelop();
        report_info("Trenutno u conditionalFactu sa Relop", expr0Type);
        if (expr0Type.getKind() != expr1Type.getKind()){
            report_error("Greska: Tipovi u relacionoj operaciji se ne poklapaju na liniji: " + condFact_ExprRelopExpr.getLine(), relop);
        } else if ( expr0Type.getKind() == Struct.Array){
            if ( condFact_ExprRelopExpr.getRelop() instanceof Equals || condFact_ExprRelopExpr.getRelop() instanceof NotEqual){
                report_info("Koriscenje relacionog operatora " + relop.getClass().getSimpleName() + " na nizovima na liniji: " + condFact_ExprRelopExpr.getLine(), relop);
                condFact_ExprRelopExpr.struct = boolType.getType();
            } else {
                report_error("Greska: Nizovi mogu biti poredjeni samo sa == i != na liniji: " + condFact_ExprRelopExpr.getLine(), relop);
            }
        } else {
            condFact_ExprRelopExpr.struct = boolType.getType();
        }
    }
    /*
     * CondFact = Expr
     */

    @Override
    public void visit (CondFact_Expr condFact_Expr){
        Struct exprType = condFact_Expr.getExpr().struct;
        if (exprType.getKind() != Struct.Bool){
            report_error("Greska: Expr mora biti tipa bool na liniji: " + condFact_Expr.getLine(), condFact_Expr);
        } else {
            condFact_Expr.struct = boolType.getType();
        }
    }

    /* Expressions  */
    public void inheritType(Struct inheritor, Struct value){
        inheritor=value;
    }
    /*
     * Expr = Term
     */
    @Override
    public void visit (Expr_Term expr_Term){
        expr_Term.struct = expr_Term.getTerm().struct;
    }

    /*
     * Expr = "-" Term.
        • Term mora biti tipa int.
     */
    @Override
    public void visit (Expr_MinusTerm expr_MinusTerm){
        if (expr_MinusTerm.getTerm().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int da bi se dodao minus, linija: " + expr_MinusTerm.getLine(), expr_MinusTerm);
        } else {
            expr_MinusTerm.struct = expr_MinusTerm.getTerm().struct;
        }
    }
    /*
     * Expr = Expr Addop Term.
        • Expr i Term moraju biti tipa int. U svakom slučaju, tipovi za Expr i Term moraju biti komatibilni. 
     */

    @Override
    public void visit (Expr_AddopTerm expr_AddopTerm){
        if (expr_AddopTerm.getTerm().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", expr_AddopTerm);
        } else if (expr_AddopTerm.getExpr().struct != Tab.intType){
            report_error("Greska: Izraz mora biti tipa int", expr_AddopTerm);
        } else {
            expr_AddopTerm.struct = expr_AddopTerm.getTerm().struct;
        }
    }
    /*
     * Term = Factor. 
     */
    @Override
    public void visit (Term_Factor term_Factor){
        term_Factor.struct = term_Factor.getFactor().struct;
    }
    /*
     * Term = Term Mulop Factor.
        • Term i Factor moraju biti tipa int
     */
    @Override
    public void visit (Term_MulopFactor term_MulopFactor){
        if (term_MulopFactor.getFactor().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", term_MulopFactor);
        } else if (term_MulopFactor.getTerm().struct != Tab.intType){
            report_error("Greska: Izraz mora biti tipa int", term_MulopFactor);
        } else {
            term_MulopFactor.struct = term_MulopFactor.getFactor().struct;
        }
    }
    /* Terms, Factors and Designators - Setting types */
    /*
     * Factor = Designator | numConst | charConst |boolConst | "(" Expr ")".
     */

     @Override
     public void visit(Factor_CharConst factor_CharConst){
         factor_CharConst.struct = Tab.charType;
     }
 
     @Override
     public void visit(Factor_NumConst factor_NumConst){
         factor_NumConst.struct = Tab.intType;
     }
 
     @Override
     public void visit(Factor_BoolConst factor_BoolConst){
         factor_BoolConst.struct = boolType.getType();
     }

     public Struct getTypeOrNull(Obj obj) {
    	 if (obj == null) {
    		 return Tab.noType;
    	 }
    	 return obj.getType();
     }
     
     @Override 
     public void visit(Factor_Designator factor_Designator){
         factor_Designator.struct = getTypeOrNull(factor_Designator.getDesignator().obj);
     }

     @Override
     public void visit (Factor_Expr factor_Expr){
         factor_Expr.struct = factor_Expr.getExpr().struct;
     }

    /*
     Factor = Designator "(" [ActPars] ")".
        • Designator mora označavati nestatičku metodu unutrašnje klase, konstruktor unutrašnje klase ili
        globalnu funkciju glavnog programa. 
    */


    /*
        Factor = "new" Type "[" Expr "]".
        • Tip neterminala Expr mora biti int
    */
    @Override
    public void visit(Factor_NewArray factor_NewArray){
        if (factor_NewArray.getExpr().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", factor_NewArray);
        } else {
           factor_NewArray.struct = new Struct(Struct.Array, currentType);
        }
    }

    /*
     * Designator ::= (Designator_Single) IDENT
     */

    @Override
    public void visit(Designator_Single designator_Single){
        Obj obj = Tab.find(designator_Single.getI1());
        if (obj == Tab.noObj) {
            report_error("Greska: Varijabla " + designator_Single.getI1() + " nije deklarisana", designator_Single);
            designator_Single.obj = Tab.noObj;
        }else {
            designator_Single.obj = obj;
            int kind = obj.getKind();
            if (kind == Obj.Con) {
                report_info("Pristup konstanti " + designator_Single.getI1(), designator_Single);
            } else if (kind == Obj.Var) {
                report_info("Pristup promenljivoj " + designator_Single.getI1(), designator_Single);
            } else if (kind == Obj.Fld) {
                report_info("Pristup polju " + designator_Single.getI1(), designator_Single);
            } else if (kind == Obj.Meth) {
                report_info("Pristup metodi " + designator_Single.getI1(), designator_Single);
            } else if (kind == Obj.Elem) {
                report_info("Pristup elementu niza " + designator_Single.getI1(), designator_Single);
            }
        }
    }

    /*
     * Designator = Designator "[" Expr "]".
        • Tip neterminala Designator mora biti niz.
        • Tip neterminala Expr mora biti int. 
     */

    @Override
    public void visit(Designator_Array designator_Array){
        if (designator_Array.getExpr().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", designator_Array);
        } else if ( designator_Array.getDesignator().obj.getType().getKind() != Struct.Array ){
            report_error("Greska: Designator nije niz", designator_Array);
        } 
    }




      
}
