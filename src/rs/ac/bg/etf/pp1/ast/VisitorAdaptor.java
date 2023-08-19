// generated with ast extension for cup
// version 0.8
// 19/7/2023 15:47:25


package rs.ac.bg.etf.pp1.ast;

public abstract class VisitorAdaptor implements Visitor { 

    public void visit(ReturnType ReturnType) { }
    public void visit(Parameter Parameter) { }
    public void visit(Mulop Mulop) { }
    public void visit(OneOfVarDecl OneOfVarDecl) { }
    public void visit(MatchedStatement MatchedStatement) { }
    public void visit(Relop Relop) { }
    public void visit(Assignop Assignop) { }
    public void visit(NumConstList NumConstList) { }
    public void visit(OneOfNumCharBoolConst OneOfNumCharBoolConst) { }
    public void visit(ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst) { }
    public void visit(StatementList StatementList) { }
    public void visit(ConstVarDeclList ConstVarDeclList) { }
    public void visit(Addop Addop) { }
    public void visit(Factor Factor) { }
    public void visit(ConstVarDecl ConstVarDecl) { }
    public void visit(CondTerm CondTerm) { }
    public void visit(Designator Designator) { }
    public void visit(ZeroOrManyVarDecl ZeroOrManyVarDecl) { }
    public void visit(Term Term) { }
    public void visit(Condition Condition) { }
    public void visit(Label Label) { }
    public void visit(ElseStatement ElseStatement) { }
    public void visit(VarDeclList VarDeclList) { }
    public void visit(Expr Expr) { }
    public void visit(OptionalActPars OptionalActPars) { }
    public void visit(DesignatorStatement DesignatorStatement) { }
    public void visit(UnmatchedStatement UnmatchedStatement) { }
    public void visit(OptionalParams OptionalParams) { }
    public void visit(Statement Statement) { }
    public void visit(VarDecl VarDecl) { }
    public void visit(OptionalActParsOneOrMany OptionalActParsOneOrMany) { }
    public void visit(CondFact CondFact) { }
    public void visit(MethodDeclList MethodDeclList) { }
    public void visit(OptionalMinus OptionalMinus) { }
    public void visit(MoreParams MoreParams) { }
    public void visit(Modulo Modulo) { visit(); }
    public void visit(Division Division) { visit(); }
    public void visit(Multiplication Multiplication) { visit(); }
    public void visit(Subtraction Subtraction) { visit(); }
    public void visit(Addition Addition) { visit(); }
    public void visit(LessThanOrEqual LessThanOrEqual) { visit(); }
    public void visit(LessThan LessThan) { visit(); }
    public void visit(GreaterThanOrEqual GreaterThanOrEqual) { visit(); }
    public void visit(GreaterThan GreaterThan) { visit(); }
    public void visit(NotEqual NotEqual) { visit(); }
    public void visit(Equals Equals) { visit(); }
    public void visit(Assign Assign) { visit(); }
    public void visit(LabelIdent LabelIdent) { visit(); }
    public void visit(Designator_Array Designator_Array) { visit(); }
    public void visit(Designator_Dot Designator_Dot) { visit(); }
    public void visit(Designator_Single Designator_Single) { visit(); }
    public void visit(Factor_Expr Factor_Expr) { visit(); }
    public void visit(Factor_NewObject Factor_NewObject) { visit(); }
    public void visit(Factor_NewArray Factor_NewArray) { visit(); }
    public void visit(Factor_BoolConst Factor_BoolConst) { visit(); }
    public void visit(Factor_CharConst Factor_CharConst) { visit(); }
    public void visit(Factor_NumConst Factor_NumConst) { visit(); }
    public void visit(Factor_DesignatorActPars Factor_DesignatorActPars) { visit(); }
    public void visit(Factor_Designator Factor_Designator) { visit(); }
    public void visit(Term_MulopFactor Term_MulopFactor) { visit(); }
    public void visit(Term_Factor Term_Factor) { visit(); }
    public void visit(OptionalMinus_None OptionalMinus_None) { visit(); }
    public void visit(OptionalMinus_Single OptionalMinus_Single) { visit(); }
    public void visit(Expr_AddopTerm Expr_AddopTerm) { visit(); }
    public void visit(Expr_MinusTerm Expr_MinusTerm) { visit(); }
    public void visit(Expr_Term Expr_Term) { visit(); }
    public void visit(CondFact_ExprRelopExpr CondFact_ExprRelopExpr) { visit(); }
    public void visit(CondFact_Expr CondFact_Expr) { visit(); }
    public void visit(CondTerm_Single CondTerm_Single) { visit(); }
    public void visit(CondTerm_OneOrMany CondTerm_OneOrMany) { visit(); }
    public void visit(Condition_Single Condition_Single) { visit(); }
    public void visit(Condition_OneOrMany Condition_OneOrMany) { visit(); }
    public void visit(OptionalActParsOneOrMany_Single OptionalActParsOneOrMany_Single) { visit(); }
    public void visit(OptionalActParsOneOrMany_Multiple OptionalActParsOneOrMany_Multiple) { visit(); }
    public void visit(OptionalActPars_None OptionalActPars_None) { visit(); }
    public void visit(OptionalActPars_OneOrMany OptionalActPars_OneOrMany) { visit(); }
    public void visit(DesignatorStatement_Decrement DesignatorStatement_Decrement) { visit(); }
    public void visit(DesignatorStatement_Increment DesignatorStatement_Increment) { visit(); }
    public void visit(DesignatorStatement_ActPars DesignatorStatement_ActPars) { visit(); }
    public void visit(DesignatorStatement_Error DesignatorStatement_Error) { visit(); }
    public void visit(DesignatorStatement_Assign DesignatorStatement_Assign) { visit(); }
    public void visit(NumConstList_None NumConstList_None) { visit(); }
    public void visit(NumConstList_Multiple NumConstList_Multiple) { visit(); }
    public void visit(ElseStatement_None ElseStatement_None) { visit(); }
    public void visit(ElseStatement_ ElseStatement_) { visit(); }
    public void visit(IfStatement IfStatement) { visit(); }
    public void visit(BlockStatement BlockStatement) { visit(); }
    public void visit(ForeachStatement ForeachStatement) { visit(); }
    public void visit(PrintStatement PrintStatement) { visit(); }
    public void visit(ReadStatement ReadStatement) { visit(); }
    public void visit(ReturnStatement ReturnStatement) { visit(); }
    public void visit(ReturnVoidStatement ReturnVoidStatement) { visit(); }
    public void visit(ContinueStatement ContinueStatement) { visit(); }
    public void visit(BreakStatement BreakStatement) { visit(); }
    public void visit(WhileStatement WhileStatement) { visit(); }
    public void visit(DesignatorFindAndReplaceStatement DesignatorFindAndReplaceStatement) { visit(); }
    public void visit(DesignatorFindAnyStatement DesignatorFindAnyStatement) { visit(); }
    public void visit(DesignatorStatement_ DesignatorStatement_) { visit(); }
    public void visit(Statement_None Statement_None) { visit(); }
    public void visit(Statement_Multiple Statement_Multiple) { visit(); }
    public void visit(Param_Array Param_Array) { visit(); }
    public void visit(Param_Single Param_Single) { visit(); }
    public void visit(MoreParamsDerived2 MoreParamsDerived2) { visit(); }
    public void visit(MoreParamsDerived1 MoreParamsDerived1) { visit(); }
    public void visit(OptionalParams_None OptionalParams_None) { visit(); }
    public void visit(OptionalParams_OneOrMany OptionalParams_OneOrMany) { visit(); }
    public void visit(ReturnType_Void ReturnType_Void) { visit(); }
    public void visit(ReturnType_Type ReturnType_Type) { visit(); }
    public void visit(MethodName MethodName) { visit(); }
    public void visit(MethodDecl MethodDecl) { visit(); }
    public void visit(MethodDecl_None MethodDecl_None) { visit(); }
    public void visit(MethodDecl_Multiple MethodDecl_Multiple) { visit(); }
    public void visit(Var_None Var_None) { visit(); }
    public void visit(Var_Multiple Var_Multiple) { visit(); }
    public void visit(Var_Array Var_Array) { visit(); }
    public void visit(Var_Single Var_Single) { visit(); }
    public void visit(VarDeclList_None VarDeclList_None) { visit(); }
    public void visit(VarDeclList_Multiple VarDeclList_Multiple) { visit(); }
    public void visit(VarDecl_OneOrMany VarDecl_OneOrMany) { visit(); }
    public void visit(NumCharBoolConst_None NumCharBoolConst_None) { visit(); }
    public void visit(NumCharBoolConst_Multiple NumCharBoolConst_Multiple) { visit(); }
    public void visit(BoolConst_Single BoolConst_Single) { visit(); }
    public void visit(CharConst_Single CharConst_Single) { visit(); }
    public void visit(NumConst_Single NumConst_Single) { visit(); }
    public void visit(ConstDecl ConstDecl) { visit(); }
    public void visit(VarDecl_Single VarDecl_Single) { visit(); }
    public void visit(ConstDecl_Single ConstDecl_Single) { visit(); }
    public void visit(EmptyConstVarDeclList_None EmptyConstVarDeclList_None) { visit(); }
    public void visit(ConstVarDecl_Multiple ConstVarDecl_Multiple) { visit(); }
    public void visit(Type Type) { visit(); }
    public void visit(ProgramName ProgramName) { visit(); }
    public void visit(Program Program) { visit(); }


    public void visit() { }
}
