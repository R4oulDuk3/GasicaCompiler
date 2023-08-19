package rs.ac.bg.etf.pp1;

import java.util.logging.Logger;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

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

    

    /* Expressions  */
    public void inheritType(Struct inheritor, Struct value){
        inheritor=value;
    }

    public void visit (Expr_Term expr_Term){
        expr_Term.struct = expr_Term.getTerm().struct;
    }

    public void visit (Expr_MinusTerm expr_MinusTerm){
        if (expr_MinusTerm.getTerm().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", expr_MinusTerm);
        } else {
            expr_MinusTerm.struct = expr_MinusTerm.getTerm().struct;
        }
    }
    public void visit (Expr_AddopTerm expr_AddopTerm){
        if (expr_AddopTerm.getTerm().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", expr_AddopTerm);
        } else if (expr_AddopTerm.getExpr().struct != Tab.intType){
            report_error("Greska: Izraz mora biti tipa int", expr_AddopTerm);
        } else {
            expr_AddopTerm.struct = expr_AddopTerm.getTerm().struct;
        }
    }

    public void visit (Term_Factor term_Factor){
        term_Factor.struct = term_Factor.getFactor().struct;
    }
    /*
        Term = Term Mulop Factor.
        • Term i Factor moraju biti tipa int
     */
    public void visit (Term_MulopFactor mulopFactor){
        if (mulopFactor.getFactor().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", mulopFactor);
        } else if (mulopFactor.getTerm().struct != Tab.intType){
            report_error("Greska: Izraz mora biti tipa int", mulopFactor);
        } else {
            mulopFactor.struct = mulopFactor.getFactor().struct;
        }
    }

    /*
        Factor = "new" Type "[" Expr "]".
        • Tip neterminala Expr mora biti int
    */

    public void visit(Factor_NewArray factor_NewArray){
        if (factor_NewArray.getExpr().struct != Tab.intType) {
            report_error("Greska: Izraz mora biti tipa int", factor_NewArray);
        } else {
            factor_NewArray.struct = new Struct(Struct.Array, factor_NewArray.getType().struct);
        }
    }

    public void visit (Factor_Expr factor_Expr){
        factor_Expr.struct = factor_Expr.getExpr().struct;
    }




    /* Terms, Factors and Designators - Setting types */
    


    public void visit(Factor_CharConst factor_CharConst){
        factor_CharConst.struct = Tab.charType;
    }

    public void visit(Factor_NumConst factor_NumConst){
        factor_NumConst.struct = Tab.intType;
    }

    public void visit(Factor_BoolConst factor_BoolConst){
        factor_BoolConst.struct = boolType.getType();
    }

      
}
