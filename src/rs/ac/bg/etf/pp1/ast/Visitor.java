// generated with ast extension for cup
// version 0.8
// 24/7/2023 19:50:35


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(PrintNumConst PrintNumConst);
    public void visit(ReturnType ReturnType);
    public void visit(Parameter Parameter);
    public void visit(Mulop Mulop);
    public void visit(OneOfVarDecl OneOfVarDecl);
    public void visit(MatchedStatement MatchedStatement);
    public void visit(Relop Relop);
    public void visit(Assignop Assignop);
    public void visit(NumConstList NumConstList);
    public void visit(OneOfNumCharBoolConst OneOfNumCharBoolConst);
    public void visit(ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst);
    public void visit(StatementList StatementList);
    public void visit(ConstVarDeclList ConstVarDeclList);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(ConstVarDecl ConstVarDecl);
    public void visit(CondTerm CondTerm);
    public void visit(Designator Designator);
    public void visit(ZeroOrManyVarDecl ZeroOrManyVarDecl);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(Label Label);
    public void visit(ElseStatement ElseStatement);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(OptionalActPars OptionalActPars);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(UnmatchedStatement UnmatchedStatement);
    public void visit(OptionalParams OptionalParams);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(CondFact CondFact);
    public void visit(OptionalActParsOneOrMany OptionalActParsOneOrMany);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(OptionalMinus OptionalMinus);
    public void visit(MoreParams MoreParams);
    public void visit(Modulo Modulo);
    public void visit(Division Division);
    public void visit(Multiplication Multiplication);
    public void visit(Subtraction Subtraction);
    public void visit(Addition Addition);
    public void visit(LessThanOrEqual LessThanOrEqual);
    public void visit(LessThan LessThan);
    public void visit(GreaterThanOrEqual GreaterThanOrEqual);
    public void visit(GreaterThan GreaterThan);
    public void visit(NotEqual NotEqual);
    public void visit(Equals Equals);
    public void visit(Assign Assign);
    public void visit(LabelIdent LabelIdent);
    public void visit(DesignatorArrayName DesignatorArrayName);
    public void visit(Designator_Array Designator_Array);
    public void visit(Designator_Single Designator_Single);
    public void visit(FuncCallDesignator FuncCallDesignator);
    public void visit(Factor_ArrAccessCount Factor_ArrAccessCount);
    public void visit(Factor_Expr Factor_Expr);
    public void visit(Factor_NewObject Factor_NewObject);
    public void visit(Factor_NewArray Factor_NewArray);
    public void visit(Factor_BoolConst Factor_BoolConst);
    public void visit(Factor_CharConst Factor_CharConst);
    public void visit(Factor_NumConst Factor_NumConst);
    public void visit(Factor_DesignatorFuncCall Factor_DesignatorFuncCall);
    public void visit(Factor_Designator Factor_Designator);
    public void visit(Term_MulopFactor Term_MulopFactor);
    public void visit(Term_Factor Term_Factor);
    public void visit(OptionalMinus_None OptionalMinus_None);
    public void visit(OptionalMinus_Single OptionalMinus_Single);
    public void visit(Expr_AddopTerm Expr_AddopTerm);
    public void visit(Expr_MinusTerm Expr_MinusTerm);
    public void visit(Expr_Term Expr_Term);
    public void visit(CondFact_ExprRelopExpr CondFact_ExprRelopExpr);
    public void visit(CondFact_Expr CondFact_Expr);
    public void visit(CondTerm_Single CondTerm_Single);
    public void visit(CondTerm_OneOrMany CondTerm_OneOrMany);
    public void visit(CondTermWrapper CondTermWrapper);
    public void visit(Condition_Single Condition_Single);
    public void visit(Condition_OneOrMany Condition_OneOrMany);
    public void visit(ConditionWrapper ConditionWrapper);
    public void visit(OptionalActParsOneOrMany_Single OptionalActParsOneOrMany_Single);
    public void visit(OptionalActParsOneOrMany_Multiple OptionalActParsOneOrMany_Multiple);
    public void visit(OptionalActPars_None OptionalActPars_None);
    public void visit(OptionalActPars_OneOrMany OptionalActPars_OneOrMany);
    public void visit(DesignatorFuncCallStart DesignatorFuncCallStart);
    public void visit(DesignatorStatement_Decrement DesignatorStatement_Decrement);
    public void visit(DesignatorStatement_Increment DesignatorStatement_Increment);
    public void visit(DesignatorStatement_ActPars DesignatorStatement_ActPars);
    public void visit(DesignatorStatement_Error DesignatorStatement_Error);
    public void visit(DesignatorStatement_Assign DesignatorStatement_Assign);
    public void visit(PrintNumConst_None PrintNumConst_None);
    public void visit(PrintNumConst_Single PrintNumConst_Single);
    public void visit(ElseStart ElseStart);
    public void visit(ElseStatement_None ElseStatement_None);
    public void visit(ElseStatement_ ElseStatement_);
    public void visit(WhileStart WhileStart);
    public void visit(ForeachStart ForeachStart);
    public void visit(ForEachDesignator ForEachDesignator);
    public void visit(ForEachInterator ForEachInterator);
    public void visit(WhileStatementStart WhileStatementStart);
    public void visit(FindAndReplaceStart FindAndReplaceStart);
    public void visit(FindAnyStart FindAnyStart);
    public void visit(FindAndReplaceIterator FindAndReplaceIterator);
    public void visit(FindAndReplaceCondExprFinished FindAndReplaceCondExprFinished);
    public void visit(SkipStatement SkipStatement);
    public void visit(MaxStatement MaxStatement);
    public void visit(IfStatement IfStatement);
    public void visit(BlockStatement BlockStatement);
    public void visit(ForeachStatement ForeachStatement);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(ReturnVoidStatement ReturnVoidStatement);
    public void visit(ContinueStatement ContinueStatement);
    public void visit(BreakStatement BreakStatement);
    public void visit(WhileStatement WhileStatement);
    public void visit(DesignatorFindAndReplaceStatement DesignatorFindAndReplaceStatement);
    public void visit(DesignatorFindAnyStatement DesignatorFindAnyStatement);
    public void visit(DesignatorStatement_ DesignatorStatement_);
    public void visit(StatementWrapper StatementWrapper);
    public void visit(Statement_None Statement_None);
    public void visit(Statement_Multiple Statement_Multiple);
    public void visit(Param_Array Param_Array);
    public void visit(Param_Single Param_Single);
    public void visit(MoreParamsDerived2 MoreParamsDerived2);
    public void visit(MoreParamsDerived1 MoreParamsDerived1);
    public void visit(OptionalParams_None OptionalParams_None);
    public void visit(OptionalParams_OneOrMany OptionalParams_OneOrMany);
    public void visit(ReturnType_Void ReturnType_Void);
    public void visit(ReturnType_Type ReturnType_Type);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(MethodDecl_None MethodDecl_None);
    public void visit(MethodDecl_Multiple MethodDecl_Multiple);
    public void visit(Var_None Var_None);
    public void visit(Var_Multiple Var_Multiple);
    public void visit(Var_Array Var_Array);
    public void visit(Var_Single Var_Single);
    public void visit(VarDeclList_None VarDeclList_None);
    public void visit(VarDeclList_Multiple VarDeclList_Multiple);
    public void visit(Final Final);
    public void visit(VarDecl_FinalOneOrMany VarDecl_FinalOneOrMany);
    public void visit(VarDecl_OneOrMany VarDecl_OneOrMany);
    public void visit(NumCharBoolConst_None NumCharBoolConst_None);
    public void visit(NumCharBoolConst_Multiple NumCharBoolConst_Multiple);
    public void visit(BoolConst_Single BoolConst_Single);
    public void visit(CharConst_Single CharConst_Single);
    public void visit(NumConst_Single NumConst_Single);
    public void visit(ConstDecl ConstDecl);
    public void visit(VarDecl_Single VarDecl_Single);
    public void visit(ConstDecl_Single ConstDecl_Single);
    public void visit(EmptyConstVarDeclList_None EmptyConstVarDeclList_None);
    public void visit(ConstVarDecl_Multiple ConstVarDecl_Multiple);
    public void visit(Type Type);
    public void visit(ProgramName ProgramName);
    public void visit(Start Start);
    public void visit(Program Program);

}
