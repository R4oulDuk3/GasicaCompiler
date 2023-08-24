package rs.ac.bg.etf.pp1;

import java.lang.reflect.Method;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {
    private Obj boolType;
    public SemanticAnalyzer() {
        Tab.init();
        boolType = Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
        List<String> methodNames = new ArrayList<>();
        methodNames.add("len");
        methodNames.add("chr");
        methodNames.add("ord");
        methodNames.forEach((methodName) -> {
            Tab.find(methodName).getLocalSymbols().forEach((obj) -> {
                obj.setFpPos(1);
            });
        });
    }

    public void dumpState() {
        Tab.dump();
    }

    Logger log = Logger.getLogger(getClass().getSimpleName());

    public boolean errorDetected = false;

    

    public void report_error(String message, Object info) {
    	errorDetected = true;
    	StringBuilder msg = new StringBuilder(message); 
    	if (info instanceof SyntaxNode)
            msg.append (" na liniji ").append(((SyntaxNode)info).getLine());
        log.severe(msg.toString());
    }

    public boolean print_info = false;
    
    public void report_info(String message, Object info) {
        if (!print_info)
            return;
    	StringBuilder msg = new StringBuilder(message); 
    	if (info instanceof SyntaxNode)
            msg.append (" na liniji ").append(((SyntaxNode)info).getLine());
        log.info(msg.toString());
    }
    /* Program = "program" ident {ConstDecl | VarDecl | ClassDecl | RecordDecl } "{" {MethodDecl} "}". */

    /* U programu mora postojati metoda sa imenom main. Ona mora biti deklarisana kao void metoda
        bez argumenata. 
    */

    static class Pair<T, U> {
        public final T condition;
        public final U errorHandler;
    
        public Pair(T condition, U errorHandler) {
            this.condition = condition;
            this.errorHandler = errorHandler;
        }
    }

    @SafeVarargs
    public final void executeWithFailureConditions(Consumer<Void> action, Pair<Function<Void, Boolean>, Consumer<Void>>... failureConditions) {
        for (Pair<Function<Void, Boolean>, Consumer<Void>> failureCondition : failureConditions) {
            if (failureCondition.condition.apply(null)) {
                failureCondition.errorHandler.accept(null);
                return;
            }
        }
        action.accept(null);
    }

    public static class State {
        public static int varNum = 0;
        public static Struct currentType;
        public static Obj currentProgram;
        public static Obj currentMethod;
        public static Struct currentMethodReturnType = Tab.noType;
        public static boolean returnTypeExpected = false;
        public static boolean returnFound = false;
        public static List<Struct> calledMethodParams = new ArrayList<>();
        public static boolean symbolAlreadyDeclared(String name) {
            // System.out.println("Checking if " + name + " is already declared");
            
            if (currentMethod == null){
                // System.out.println("Current method is null");
                return Tab.find(name) != Tab.noObj;
            }
            return Tab.currentScope().findSymbol(name) != null || Tab.find(name) != Tab.noObj;
        }

        public static boolean symbolAlreadyDeclaredInScope(String name) {
            // System.out.println("Checking if " + name + " is already declared");
            
            if (currentMethod == null){
                // System.out.println("Current method is null");
                return Tab.find(name) != null;
            }
            return Tab.currentScope().findSymbol(name) != null;
        }

        public static Obj find(String name) {

        if (!State.symbolAlreadyDeclared(name))
            throw new RuntimeException("Greska: Ime " + name + " nije deklarisano");

        if (State.currentMethod == null)
            return Tab.find(name);
        Obj obj = Tab.currentScope().findSymbol(name);
        if (obj!=null) return obj;
        return Tab.find(name);
    }
    }

    public int getVarNum() {
        return State.varNum;
    }

    @Override
    public void visit(Program program) {
        Obj main = Tab.find("main");
        executeWithFailureConditions(
            (Void) -> { 
                report_info("Pronadjena main metoda u ispravnom stanju", program);
                State.varNum = Tab.currentScope().getnVars();
            }, 
            new Pair<>((Void) -> main == Tab.noObj, (Void) -> report_error("Greska: Main metoda nije pronadjena", program)),
            new Pair<>((Void) -> main.getKind() != Obj.Meth, (Void) -> report_error("Greska: Main nije deklarisan kao metoda", program)),
            new Pair<>((Void) -> main.getType() != Tab.noType, (Void) -> report_error("Greska: Main ima povratnu vrednost razlicitu od Void", program)),
            new Pair<>((Void) -> main.getLevel() != 0, (Void) -> report_error("Error: Main ima vise parametre", program))
        );
        Tab.chainLocalSymbols(State.currentProgram);
        Tab.closeScope();
    }

    

    @Override
    public void visit(ProgramName programName) {
        State.currentProgram = Tab.insert(Obj.Prog, programName.getI1(), Tab.noType);
        Tab.openScope();
    }

    /* Const and Var declarations */




    /*
     * Tip reference
        Nizovi i klase su tipa reference.
        [Validation required]
     */

    /*
     * Tip konstante
        ‐ Tip celobrojne konstante (npr. 17) je int.
        ‐ Tip znakovne konstante (npr. 'x') je char.
        ‐ Tip logičke konstante (npr. true) je bool. 
     */

    @Override
    public void visit(Type type) {
        Obj typeNode = Tab.find(type.getI1());
    
        executeWithFailureConditions(
            (Void) -> {
                State.currentType = typeNode.getType();
                report_info("Koriscenje tipa " + type.getI1(), type);
            },
            new Pair<>((Void) -> typeNode == Tab.noObj, 
                       (Void) -> {
                           report_error("Nije pronadjen tip " + type.getI1() + " u tabeli simbola", type);
                           State.currentType = Tab.noType;
                       }),
            new Pair<>((Void) -> typeNode.getKind() != Obj.Type,
                       (Void) -> {
                           report_error("Greska: Ime " + type.getI1() + " ne predstavlja tip ", type);
                           State.currentType = Tab.noType;
                       })
        );
    }

    @Override
    public void visit (NumConst_Single numConst){
        executeWithFailureConditions((Void) ->{
            Obj constObj = Tab.insert(Obj.Con, numConst.getI1(), Tab.intType);
            constObj.setAdr(numConst.getN3());
            report_info("Deklarisana konstanta numericka", numConst);
        }, 
            new Pair<>((Void) -> State.symbolAlreadyDeclared(numConst.getI1()), (Void) -> report_error("Greska: Konstanta " + numConst.getI1() + " je vec deklarisana", numConst)),
            new Pair<>((Void) -> State.currentType != Tab.intType , (Void) -> report_error("Greska: Konstanta " + numConst.getI1() + " nije tipa int", numConst))
        );
    }

    @Override
    public void visit (CharConst_Single charConst){
        executeWithFailureConditions((Void) -> {
            report_info("Deklarisana konstanta char", charConst);
            Obj constObj = Tab.insert(Obj.Con, charConst.getI1(), Tab.charType);
            constObj.setAdr(charConst.getC3().charAt(0));
            
        }, new Pair <>((Void) -> State.symbolAlreadyDeclared(charConst.getI1()), (Void) -> report_error("Greska: Konstanta " + charConst.getI1() + " je vec deklarisana", charConst)),
            new Pair<>((Void) -> State.currentType != Tab.charType, (Void) -> report_error("Greska: Konstanta " + charConst.getI1() + " nije tipa char", charConst))
        );
    }

   @Override
   public void visit (BoolConst_Single boolConst){
       executeWithFailureConditions(
           (Void) -> {
                report_info("Deklarisana konstanta bool", boolConst);
                Obj constObj = Tab.insert(Obj.Con, boolConst.getI1(), boolType.getType());
                constObj.setAdr(boolConst.getB3() ? 1 : 0);
           },
           new Pair<>((Void) -> State.symbolAlreadyDeclared(boolConst.getI1()), (Void) -> report_error("Greska: Konstanta " + boolConst.getI1() + " je vec deklarisana", boolConst)),
           new Pair<>((Void) -> State.currentType != boolType.getType(), (Void) -> report_error("Greska: Konstanta " + boolConst.getI1() + " nije tipa bool", boolConst))
       );
    }

    @Override 
    public void visit (Var_Single varDecl){

        executeWithFailureConditions((Void) -> {
                Tab.insert(Obj.Var, varDecl.getI1(), State.currentType);
                report_info("Deklarisana promenljiva " + varDecl.getI1(), varDecl);
            }, 
            new Pair<>((Void) -> State.symbolAlreadyDeclared(varDecl.getI1()), (Void) -> report_error("Greska: Promenljiva " + varDecl.getI1() + " je vec deklarisana", varDecl))
        );
    }

    @Override 
    public void visit (Var_Array varDecl){
        executeWithFailureConditions(
            (Void) -> {
                Tab.insert(Obj.Var, varDecl.getI1(), new Struct(Struct.Array, State.currentType));
                report_info("Deklarisana niz " + varDecl.getI1(), varDecl);
            },
            new Pair<>((Void) -> State.symbolAlreadyDeclared(varDecl.getI1()), (Void) -> report_error("Greska: Promenljiva " + varDecl.getI1() + " je vec deklarisana", varDecl))
        );
    }

    @Override
    public void visit(ReturnType_Void returnType){

        State.currentMethodReturnType = Tab.noType;
        State.returnTypeExpected = false;
        
    }
    /*
     * MethodDecl = (Type | "void") ident "(" [FormPars] ")" {VarDecl} "{" {Statement} "}".
        • Ako metoda nije tipa void, mora imati iskaz return unutar svog tela (uslov treba da se proverava u
        vreme izvršavanja programa). 
     */

    /* Method declarations */

    @Override
    public void visit (ReturnType_Type returnType){
        Obj typeNode = Tab.find(returnType.getType().getI1());
        executeWithFailureConditions(
            (Void) -> {
                report_info("Koriscenje tipa " + returnType.getType().getI1() +" na liniji " , returnType);
                State.currentMethodReturnType = typeNode.getType();
                State.returnTypeExpected = true;
            }, 
            new Pair<>((Void) -> typeNode == Tab.noObj,
                (Void) -> {
                report_error("Nije pronadjen tip " + returnType.getType().getI1() + " u tabeli simbola", null);
                State.currentMethodReturnType = Tab.noType;
            }),
            new Pair<>((Void) -> typeNode.getKind() != Obj.Type, (Void) -> {
                report_error("Greska: Ime " + returnType.getType().getI1() + " ne predstavlja tip ", returnType);
                State.currentMethodReturnType = Tab.noType;
            })
        );   
    }

    @Override
    public void visit (MethodName methodName){
        Obj obj = Tab.find(methodName.getI1());
        if (obj != Tab.noObj) {
            report_error("Greska: Metoda " + methodName.getI1() + " je vec deklarisana", methodName);
        } else {
            obj = Tab.insert(Obj.Meth, methodName.getI1(), State.currentMethodReturnType);
            State.currentMethod = obj;
            methodName.obj = obj;
            Tab.openScope();
            
            report_info("Obradjuje se funkcija " + methodName.getI1() , methodName);
        }
    }

    @Override
    public void visit (MethodDecl methodDecl){
        Tab.chainLocalSymbols(State.currentMethod);
        Tab.closeScope();
        State.currentMethod = Tab.noObj;
        if (State.returnTypeExpected && !State.returnFound) {
            report_error("Greska: Funkcija " + methodDecl.getMethodName().getI1() + " nema return iskaz", methodDecl);
        }
        State.returnTypeExpected = false;
        State.returnFound = false;
        report_info("Zavrseno je obradjivanje funkcije " + methodDecl.getMethodName().getI1(), methodDecl);
    }

    public boolean isInMainMethod(){
        return State.currentMethod.getName().equals("main");
    }

    public void registerParam(Obj obj){
        obj.setFpPos(1);
        State.currentMethod.setLevel(State.currentMethod.getLevel() + 1);
    }

    @Override
    public void visit (Param_Single paramSingle){
        if (State.symbolAlreadyDeclaredInScope(paramSingle.getI2())) {
            report_error("Greska: Parametar / Varijabla " + paramSingle.getI2() + " je vec deklarisan", paramSingle);
        } else if(isInMainMethod()){
            report_error("Greska: Variabla ne moze biti prosledjena kao parametar main emthoda", paramSingle);
            
        } else {
            Obj obj =Tab.insert(Obj.Var, paramSingle.getI2(), State.currentType);
            registerParam(obj);
            report_info("Deklarisan parametar " + paramSingle.getI2(), paramSingle);
        }
    }

    @Override
    public void visit (Param_Array paramArray){
        if (State.symbolAlreadyDeclared(paramArray.getI2())) {
            report_error("Greska: Parametar / Varijabla " + paramArray.getI2() + " je vec deklarisan", paramArray);
        }else if(isInMainMethod()){
            report_error("Greska: Variabla ne moze biti prosledjena kao parametar main emthoda", paramArray);
        } else {
            Obj obj = Tab.insert(Obj.Var, paramArray.getI2(), new Struct(Struct.Array, State.currentType));
            registerParam(obj);
            report_info("Deklarisan parametar " + paramArray.getI2(), paramArray);
        }
    }

    /* Error handling */
        @Override
	public void visit(DesignatorStatement_Error designatorStatement_Error){
		report_error("Greska: Sintaksicki neispravan kod" + designatorStatement_Error.getClass().getSimpleName() + "]", designatorStatement_Error);
	}

    @Override
    public void visit(Var_Error var_Error){
        report_error("Greska: Sintaksicki neispravan kod[" + var_Error.getClass().getSimpleName() + "]", var_Error);
    }

    @Override
    public void visit(Param_Error param_Error){
        report_error("Greska: Sintaksicki neispravan kod [" + param_Error.getClass().getSimpleName() + "]", param_Error);
    }
    @Override
    public void visit(Condition_Error statement_Error){
        report_error("Greska: Sintaksicki neispravan kod [" + statement_Error.getClass().getSimpleName() + "]", statement_Error);
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

    public boolean isAssignable(Struct designatorType, Struct exprType){
        if (designatorType.getKind() == Struct.Array && exprType.getKind() == Struct.Array) {
            return designatorType.getElemType().assignableTo(exprType.getElemType());
        } 
        return designatorType.assignableTo(exprType);
    }

    @Override
    public void visit (DesignatorStatement_Assign statement_DesignatorStatement_Assign){
        Struct designatorType = getTypeOrNull(statement_DesignatorStatement_Assign.getDesignator().obj);
        Struct exprType = statement_DesignatorStatement_Assign.getExpr().struct;
        Assignop assignop = statement_DesignatorStatement_Assign.getAssignop();
        // log.info("Designator type " + designatorType.getKind() + " Expr type " + exprType.getKind());
        if (!isAssignable(designatorType, exprType)){
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

     public List<Struct> getMethodParams(Designator designator){
         List<Struct> params = new ArrayList<Struct>();
         for (Obj obj : designator.obj.getLocalSymbols()){
            if(obj.getFpPos() > 0) params.add(obj.getType());
         }
         return params;
     }

    @Override
    public void visit (DesignatorStatement_ActPars designatorStatement_ActPars){
        if (designatorStatement_ActPars.getDesignator().obj == null || designatorStatement_ActPars.getDesignator().obj.getKind() != Obj.Meth){
            report_error("Greska: Designator mora označavati nestatičku metodu unutrašnje klase ili globalnu funkciju glavnog programa na liniji: " + designatorStatement_ActPars.getLine(), designatorStatement_ActPars);
            return;
        } else {
            List<Struct> methodParams = getMethodParams(designatorStatement_ActPars.getDesignator());
            List<Struct> calledMethodParams = State.calledMethodParams;
            if (methodParams.size() != calledMethodParams.size()){
                report_error(String.format("Broj parametara se ne pokpala [deklarisani: %d, dati: %d]", methodParams.size(), calledMethodParams.size()), designatorStatement_ActPars);
                return;
            }
            for (int i = 0; i < methodParams.size(); i++){
                if (!calledMethodParams.get(i).assignableTo(methodParams.get(i))){
                    report_error("Greska: Tipovi parametara se ne poklapaju ", designatorStatement_ActPars);
                    return;
                }
            }
        } 
        State.calledMethodParams = new ArrayList<>();
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
        int designatorKind = readStatement.getDesignator().obj.getKind();
        if (designatorType.getKind() != Struct.Int && designatorType.getKind() != Struct.Char && designatorType.getKind() != Struct.Bool){
            report_error("Greska: Designator mora biti tipa int, char ili bool na liniji: " + readStatement.getLine(), readStatement);
        }
        else if (designatorKind != Obj.Var && designatorKind != Obj.Elem){
            report_error("Greska: Designator mora biti promenljiva, element niza ili polje objekta unutrasnje klase na liniji: " + readStatement.getLine(), readStatement);
        } else {
            report_info("Read statement ", readStatement);
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
        } else {
            report_info("Print statement ", printStatement);
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
        if (State.returnTypeExpected){
            report_error("Greska: Return nema povratnu vrendost na liniji: ", returnVoidStatement);
        }
        State.returnFound = true;
    }

    @Override
    public void visit(ReturnStatement returnStatement) {
        State.returnFound = true;
        if (!State.returnTypeExpected){
            report_error("Greska: Return ima povratnu vrendost na liniji: " + returnStatement.getLine(), returnStatement);
        }
        Struct exprType = returnStatement.getExpr().struct;
        if (exprType.getKind() != State.currentMethodReturnType.getKind()){
            report_error("Greska: Tip neterminala Expr mora biti ekvivalentan povratnom tipu tekuće metode/ globalne funkcije " + returnStatement.getLine(), returnStatement);
        }
    }

    /*
     * Statement := Designator ʺ=ʺ Designator ʺ.ʺ ʺfindAnyʺ "(" Expr ")" ";"
     *  Designator sa desne strane znaka jednakosti mora označavati jednodimenzionalni niz
        ugrađenog tipa.
        ● Designator sa leve strane znaka jednakosti mora označavati promenljivu tipa bool.
        ● Funkcija ʺfindAnyʺ iterira kroz niz od prvog do poslednjeg elementa niza.
        ● Rezultat funkcije je true ukoliko u nizu kroz koji se iterira postoji element koji po vrednosti
        odgovara rezultatu izraza Expr. U suprotnom je rezultat funkcije false.
     */

    @Override
    public void visit(DesignatorFindAnyStatement designatorFindAnyStatement){
        Designator findAnyResult = designatorFindAnyStatement.getDesignator();
        Designator findAnyArray = designatorFindAnyStatement.getDesignator1();
        // log.info("findAnyResult: " + findAnyResult.obj.getType().getKind() + " findAnyArray: " + findAnyArray.obj.getType().getKind());
        // log.info("Booltype: " + boolType.getKind());
        if(findAnyArray.obj.getType().getKind() != Struct.Array){
            report_error("Greska: Designator sa desne strane znaka jednakosti mora označavati jednodimenzionalni niz ugrađenog tipa na liniji: " + designatorFindAnyStatement.getLine(), designatorFindAnyStatement);
        } else if ( findAnyArray.obj.getType().getElemType().getKind() == Struct.Array){
            report_error("Greska: Designator sa desne strane je visedimenzionalni niz", designatorFindAnyStatement);
        } else if (findAnyResult.obj.getType().getKind() != boolType.getType().getKind()){
            report_error("Greska: Designator sa leve strane znaka jednakosti mora označavati promenljivu tipa bool na liniji: " + designatorFindAnyStatement.getLine(), designatorFindAnyStatement);
        } else {
            report_info("Designator findAny statement ", designatorFindAnyStatement);
        }
    }
    
    /**
     * Statement := Designator ʺ=ʺ Designator ʺ.ʺ ʺfindAndReplaceʺ "(" Expr "," ident "=>" Expr ")" ";".
        ● Designator mora označavati jednodimenzionalni niz ugrađenog tipa.
        ● ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi niza koji opisuje
        Designator.
        ● Funkcija ʺfindAndReplaceʺ iterira kroz niz tako što u svakoj iteraciji ident označava tekući
        element niza, pri čemu iteriranje počinje od prvog elementa niza i završava se sa poslednjim.
        ● Rezultat funkcije je novi niz, iste dužine kao originalni, čije su vrednosti formirane
        primenom izraza Expr (iz drugog argumenta funkcije) nad svakim elementom ident
        originalnog niza za koji važi da po vrednosti odgovara rezultatu evaluacije izraza Expr (prvi
        argument funkcije). Ostali elementi originalnog niza se prepisuju u novi niz neizmenjeni.
        Novi niz prethodno mora biti deklarisan.     
     */



    @Override
    public void visit(DesignatorFindAndReplaceStatement designatorFindAndReplaceStatement){
        Designator findAndReplaceResult = designatorFindAndReplaceStatement.getDesignator();
        Designator findAndReplaceArray = designatorFindAndReplaceStatement.getDesignator1();
        if(findAndReplaceResult.obj.getType().getKind() != Struct.Array){
            report_error("Greska: Resultat findAndReplace statmenta mora biti deklarisan niz", designatorFindAndReplaceStatement);
        } else if (findAndReplaceArray.obj.getType().getKind() != Struct.Array){
            report_error("Greska: Designator koji se koristi za findAndReplace mora da bude niz", findAndReplaceArray);
        } else if (!State.symbolAlreadyDeclared(designatorFindAndReplaceStatement.getFindAndReplaceIterator().getI1())){
            report_error("Greska: Element niza mora biti deklarisan pre pozivanja", findAndReplaceArray);
        } else {
            Obj arrayElement = State.find(designatorFindAndReplaceStatement.getFindAndReplaceIterator().getI1());
            if (arrayElement.getType().getKind() != findAndReplaceArray.obj.getType().getElemType().getKind()){
                report_error("Greska: Element niza mora biti istog tipa kao i niz", findAndReplaceArray);
            } else {
                report_info("Designator findAndReplace statement ", designatorFindAndReplaceStatement);
                designatorFindAndReplaceStatement.getFindAndReplaceIterator().obj = arrayElement;
            }
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
        if (!State.symbolAlreadyDeclared(forEachInterator.getI1())){
            report_error("Greska: Ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi niza koji opisuje Designator na liniji: " + forEachInterator.getLine(), forEachInterator);
            return;
        }
        Obj iteratorObj = State.find(forEachInterator.getI1());
        if (iteratorObj.getType().getKind() != currentForeachType.getKind()){
            report_error("Greska: Ident mora biti lokalna ili globalna promenljiva istog tipa kao i elementi niza koji opisuje Designator na liniji: " + forEachInterator.getLine(), forEachInterator);
            return;
        }
        forEachInterator.obj = iteratorObj;
    }
    /*
     * ActPars = Expr {"," Expr}.
        • Broj formalnih i stvarnih argumenata metode ili konstruktora mora biti isti.
        • Tip svakog stvarnog argumenta mora biti kompatibilan pri dodeli sa tipom svakog formalnog
        argumenta na odgovarajućoj poziciji. 
     */
    
    @Override
    public void visit(OptionalActParsOneOrMany_Single optionalActParsOneOrMany_Single){
        State.calledMethodParams.add(optionalActParsOneOrMany_Single.getExpr().struct);
    }

    @Override
    public void visit(OptionalActParsOneOrMany_Multiple optionalActParsOneOrMany_Multiple){
        State.calledMethodParams.add(optionalActParsOneOrMany_Multiple.getExpr().struct);
    }

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
        report_info("Trenutno u conditionalFactu sa Relop", condFact_ExprRelopExpr);
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
            log.info("Objekat je null, ne moze se dobiti tip");
    		 return Tab.noType;
    	 }
    	 return obj.getType();
     }
     
     @Override 
     public void visit(Factor_Designator factor_Designator){
         factor_Designator.struct = getTypeOrNull(factor_Designator.getDesignator().obj);
     }

     @Override
     public void visit(Factor_DesignatorFuncCall factor_DesignatorFuncCall){
         factor_DesignatorFuncCall.struct = factor_DesignatorFuncCall.getDesignator().obj.getType();
        if (factor_DesignatorFuncCall.getDesignator().obj == null || factor_DesignatorFuncCall.getDesignator().obj.getKind() != Obj.Meth){
            report_error("Greska: Designator mora označavati nestatičku metodu unutrašnje klase ili globalnu funkciju glavnog programa na liniji: " + factor_DesignatorFuncCall.getLine(), factor_DesignatorFuncCall);
        } else {
            List<Struct> methodParams = getMethodParams(factor_DesignatorFuncCall.getDesignator());
            List<Struct> calledMethodParams = State.calledMethodParams;
            if (methodParams.size() != calledMethodParams.size()){
                report_error(String.format("Broj parametara se ne pokpala [deklarisani: %d, dati: %d]", methodParams.size(), calledMethodParams.size()), factor_DesignatorFuncCall);
            } else {
                for (int i = 0; i < methodParams.size(); i++){
                    if (!calledMethodParams.get(i).assignableTo(methodParams.get(i))){
                        report_error("Greska: Tipovi parametara se ne poklapaju ", factor_DesignatorFuncCall);
                    }
                }
            }
            
        } 
        State.calledMethodParams = new ArrayList<>();
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
           factor_NewArray.struct = new Struct(Struct.Array, State.currentType);
        }
    }

    /*
     * Designator ::= (Designator_Single) IDENT
     */

    @Override
    public void visit(Designator_Single designator_Single){
        Obj obj = Tab.find(designator_Single.getI1());
        if (obj == Tab.noObj) {
            report_error("Greska: " + designator_Single.getI1() + " nije deklarisana", designator_Single);
            designator_Single.obj = Tab.noObj;
        }else {
            designator_Single.obj = obj;
            int kind = obj.getKind();
            if (kind == Obj.Con) {
                report_info("Pristup konstanti " + designator_Single.getI1() , designator_Single);
            } else if (kind == Obj.Var) {
                report_info("Pristup promenljivoj " + designator_Single.getI1()+ " tipa " + obj.getType().getKind(), designator_Single);
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
        } else if ( designator_Array.getDesignatorArrayName().getDesignator().obj.getType().getKind() != Struct.Array ){
            report_error("Greska: Designator nije niz", designator_Array);
        } else {
            designator_Array.obj = new Obj(Obj.Elem, designator_Array.getDesignatorArrayName().getDesignator().obj.getName(), designator_Array.getDesignatorArrayName().getDesignator().obj.getType().getElemType());
        }
    }




      
}
