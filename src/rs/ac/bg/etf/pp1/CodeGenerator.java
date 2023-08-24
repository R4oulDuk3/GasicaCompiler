package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.mj.runtime.Run;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.security.PublicKey;

import java.util.Stack;
import java.util.function.Consumer;

import javax.management.RuntimeErrorException;

import rs.ac.bg.etf.pp1.ast.*;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc = 0;
	public int getMainPc() {
		return mainPc;
	}

	public static class State {
		public static boolean returnVoidCalled = false;
		public static void setReturnVoidCalled() {
			returnVoidCalled = true;
		}
		public static boolean getAndResetReturnVoidCalled() {
			boolean ret = returnVoidCalled;
			returnVoidCalled = false;
			return ret;
		}
	}

	{
		globalMethodInit((Void) -> {
			Code.put(Code.load_n);
			Code.put(Code.arraylength);
		}, "len");

		globalMethodInit((Void) -> {
		Code.put(Code.load_n);
		}, "ord", "chr");
	}

	private void globalMethodInit(Consumer<Void> methodOps, String... methodNames){
		for (String methodName : methodNames) {
			Obj methodObj = Tab.find(methodName);
			methodObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			methodOps.accept(null);
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
	}

	/* Method stuff */

	private void putMethodOperations(MethodName methodName) {
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLocalSymbols().size());
	}

	@Override
	public void visit(MethodName methodName){
		methodName.obj.setAdr(Code.pc);
		if(methodName.obj.getName().equals("main")){
			mainPc = Code.pc;
		}
		putMethodOperations(methodName);
	}

	@Override
	public void visit(MethodDecl methodDecl){
		if (!State.getAndResetReturnVoidCalled())
			executeReturn();
	}

	/*
    Statement = DesignatorStatement ";"
    | Designator ʺ=ʺ Designator ʺ.ʺ ʺfindAnyʺ "(" Expr ")" ";".
    | Designator ʺ=ʺ Designator ʺ.ʺ ʺfindAndReplaceʺ "(" Expr "," ident "=>" Expr ")" ";"
    | "if" "(" Condition ")" Statement ["else" Statement]
    | "while" "(" Condition ")" Statement
    | "break" ";"
    | "continue" ";"
    | "return" [Expr] ";"
    | "read" "(" Designator ")" ";"
    | "print" "(" Expr ["," numConst] ")" ";"
    | "{" {Statement} "}".
*/

	private void putCorrectReadOperation(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getType() != Tab.charType) Code.put(Code.read);
		else Code.put(Code.bread);
	}

	@Override
	public void visit(ReadStatement readStatement){
		putCorrectReadOperation(readStatement);
		Code.store(readStatement.getDesignator().obj);
	}

	@Override
	public void visit(PrintNumConst_Single printNumConst_Single){
		Code.loadConst(printNumConst_Single.getN1());
	}

	@Override
	public void visit(PrintNumConst_None printNumConst_None){
		Code.loadConst(
			((PrintStatement)printNumConst_None.getParent()).getExpr().struct.getKind() == Tab.charType.getKind() ? 1 : 5 );
	}

	@Override
	public void visit(PrintStatement printStatement){
		// Code.loadConst(0);
		if(printStatement.getExpr().struct == Tab.charType)
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

private void executeReturn() {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}


	@Override
	public void visit(ReturnVoidStatement returnVoidStatement){
		State.setReturnVoidCalled();
		executeReturn();
	}
 
	@Override
	public void visit(ReturnStatement returnStatement){
		executeReturn();
	}


	/*
	 * DesignatorStatement = Designator (Assignop Expr | "(" [ActPars] ")" | "++" | "‐‐")
	 */
	@Override
	public void visit(DesignatorStatement_Assign designatorStatementAssign){
		Code.store(designatorStatementAssign.getDesignator().obj);
	}

	@Override 
	public void visit(DesignatorStatement_Increment designatorStatementInc){
		if (designatorStatementInc.getDesignator().obj.getKind() == Obj.Elem){
			Code.put(Code.dup2);
		}
		Code.loadConst(1);
		Code.load(designatorStatementInc.getDesignator().obj);
		Code.put(Code.add);
		Code.store(designatorStatementInc.getDesignator().obj);
	}

	@Override
	public void visit(DesignatorStatement_Decrement designatorStatementDec){
		if (designatorStatementDec.getDesignator().obj.getKind() == Obj.Elem){
			Code.put(Code.dup2);
		}
		Code.loadConst(-1);
		Code.load(designatorStatementDec.getDesignator().obj);
		Code.put(Code.add);
		Code.store(designatorStatementDec.getDesignator().obj);
	}

	@Override
	public void visit(Factor_DesignatorFuncCall factorDesignatorFuncCall){
		Obj functionObj = factorDesignatorFuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	@Override
	public void visit(DesignatorStatement_ActPars designatorFuncCall){
		Obj functionObj = designatorFuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (functionObj.getType() != Tab.noType)
			Code.put(Code.pop);
	}

	/* Conditional stuff */



	public class PatchingStacksOfSets {
		public static final String BREAK = "BREAK";

		private HashMap<String, Stack<Set<Integer>>> stacksOfSets = new HashMap<>();
		{
			stacksOfSets.put(BREAK, new Stack<>());
		}

		public void initializeNewSet(String stackName){
			Stack<Set<Integer>> stack = stacksOfSets.get(stackName);
			stack.push(new HashSet<>());
		}

		public void popAndPatchSet(String stackName){
			Stack<Set<Integer>> stack = stacksOfSets.get(stackName);
			Set<Integer> set = stack.pop();
			for (Integer adr : set) {
				Code.fixup(adr);
			}
		}

		public void addToCurrentSet(String stackName, int adr){
			Stack<Set<Integer>> stack = stacksOfSets.get(stackName);
			Set<Integer> set = stack.peek();
			set.add(adr);
		}

		public void addAfterJumpToCurrentSet(String stackName){
			addToCurrentSet(stackName, Code.pc - 2);
		}
	}

	public class PatchingStacks{

		public static final String AND_CONDS = "AND_CONDS";
		public static final String OR_CONDS = "OR_CONDS";
		public static final String END_STMT_BLOCK = "END_STMT_BLOCK";
		public static final String END_ELSE_BLOCK = "END_ELSE_BLOCK";
		public static final String END_FOREACH = "END_FOREACH";
		public static final String START_WHILE = "START_WHILE";
		public static final String START_FOREACH = "START_FOREACH";
		public static final String START_FINDANY = "START_FINDANY";
		public static final String END_FINDANY = "END_FINDANY";
		public static final String START_FINDANDREPLACE = "START_FINDANDREPLACE";
		public static final String END_FINDANDREPLACE = "END_FINDANDREPLACE";
		private Stack<Integer> condFact = new Stack<>();
		private Stack<Integer> condTerm = new Stack<>();
		private Stack<Integer> skipBlock = new Stack<>();
		private Stack<Integer> skipElse = new Stack<>();
		private Stack<Integer> startWhile = new Stack<>();
		private Stack<Integer> startForEach = new Stack<>();
		private Stack<Integer> endForEach = new Stack<>();
		private Stack<Integer> startFindAny = new Stack<>();
		private Stack<Integer> endFindAny = new Stack<>();
		private Stack<Integer> startFindAndReplace = new Stack<>();
		private Stack<Integer> endFindAndReplace = new Stack<>();
		private HashMap<String, Stack<Integer>> stacks = new HashMap<>();
		{
			stacks.put(AND_CONDS, condFact);
			stacks.put(OR_CONDS, condTerm);
			stacks.put(END_STMT_BLOCK, skipBlock);
			stacks.put(END_ELSE_BLOCK, skipElse);
			stacks.put(END_FOREACH, endForEach);
			stacks.put(START_WHILE, startWhile);
			stacks.put(START_FOREACH, startForEach);
			stacks.put(START_FINDANY, startFindAny);
			stacks.put(END_FINDANY, endFindAny);
			stacks.put(START_FINDANDREPLACE, startFindAndReplace);
			stacks.put(END_FINDANDREPLACE, endFindAndReplace);
		}
		private Stack<String> loopStack = new Stack<>();

		public PatchingStacks(){}

		public void push(String stackName, int adr ){
			stacks.get(stackName).push(adr);
		}

		public void pushAfterJumpCall(String stackName){
			push(stackName, Code.pc-2);
		}

		public void pushOnStartingPositionStack(String stackName){
			if (!START_WHILE.equals(stackName) && !START_FOREACH.equals(stackName) && !START_FINDANY.equals(stackName) && !START_FINDANDREPLACE.equals(stackName))
				throw new RuntimeException("Only START_WHILE and START_FOREACH can be be used as starting position stacks");
			push(stackName, Code.pc);
			loopStack.push(stackName);
		}

		public int popFromStartingStack(String stackName){
			if (!START_WHILE.equals(stackName) && !START_FOREACH.equals(stackName) && !START_FINDANY.equals(stackName) && !START_FINDANDREPLACE.equals(stackName))
				throw new RuntimeException("Only START_WHILE, START_FOREACH can be popped from starting stack");
			loopStack.pop();
			return stacks.get(stackName).pop();
			
		}

		public int peekOnStartingStack(){
			return stacks.get(loopStack.peek()).peek();
		}

		public void patchAndEmptyConditionalStack(String stackName){
			if (!AND_CONDS.equals(stackName) && !OR_CONDS.equals(stackName))
				throw new RuntimeException("Only AND_CONDS and OR_CONDS can be patched and emptied");

			Stack<Integer> stack = stacks.get(stackName);
			while(!stack.isEmpty()){
				int adr = stack.pop();
				Code.fixup(adr);
			}
		}

		public void patchAndPopFromEndStack(String stackName){
			if (!END_STMT_BLOCK.equals(stackName) && !END_ELSE_BLOCK.equals(stackName) && !END_FOREACH.equals(stackName) && !END_FINDANY.equals(stackName) && !END_FINDANDREPLACE.equals(stackName)	)
				throw new RuntimeException("Only END_STMT_BLOCK, END_ELSE_BLOCK and END_FOREACH can be patched and emptied");

			Stack<Integer> stack = stacks.get(stackName);
			int adr = stack.pop();
			Code.fixup(adr);
		}
	}

	PatchingStacks patchingStacks = new PatchingStacks();
	PatchingStacksOfSets patchingStacksOfSets = new PatchingStacksOfSets();

	private int mapRelOp(Relop relop){
		switch(relop.getClass().getSimpleName()){
			case "Equals": return Code.eq;
			case "NotEqual": return Code.ne;
			case "GreaterThan": return Code.gt;
			case "GreaterThanOrEqual": return Code.ge;
			case "LessThan": return Code.lt;
			case "LessThanOrEqual": return Code.le;
			default: throw new RuntimeException("Unknown relop");
		}
	}

	@Override
	public void visit(CondFact_Expr conditionFactor){
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		patchingStacks.pushAfterJumpCall(PatchingStacks.AND_CONDS);
	}


	@Override
	public void visit(CondFact_ExprRelopExpr conditionFactor){
		Code.putFalseJump(mapRelOp(conditionFactor.getRelop()), 0); // TODO: replace with visiting all relops
		patchingStacks.pushAfterJumpCall(PatchingStacks.AND_CONDS);
	}

	@Override
	public void visit(CondTermWrapper condTermWrapper){
		Code.putJump(0);
		patchingStacks.pushAfterJumpCall(PatchingStacks.OR_CONDS);
		patchingStacks.patchAndEmptyConditionalStack(PatchingStacks.AND_CONDS);
	}

	@Override
	public void visit(ConditionWrapper conditionWrapper ){
		Code.putJump(0);
		patchingStacks.pushAfterJumpCall(PatchingStacks.END_STMT_BLOCK);
		patchingStacks.patchAndEmptyConditionalStack(PatchingStacks.OR_CONDS);
	}

	/* IF ELSE PATCHING */

	@Override
	public void visit(ElseStart elseStart){
		Code.putJump(0);
		patchingStacks.pushAfterJumpCall(PatchingStacks.END_ELSE_BLOCK);
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_STMT_BLOCK);
	}

	@Override
	public void visit(ElseStatement_None elseStatementNone){
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_STMT_BLOCK);
	}

	@Override
	public void visit(ElseStatement_ elseStatement_){
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_ELSE_BLOCK);
	}

	/* While patching */

	@Override
	public void visit(WhileStart whileStart){
		// patchingStacks.pushAfterJumpCall(PatchingStacks.START_WHILE);
		patchingStacks.pushOnStartingPositionStack(PatchingStacks.START_WHILE);
		patchingStacksOfSets.initializeNewSet(PatchingStacksOfSets.BREAK);
	}

	@Override
	public void visit(WhileStatement whileStatementEnd){
		int whileStartPc = patchingStacks.popFromStartingStack(PatchingStacks.START_WHILE);
		Code.putJump(whileStartPc);
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_STMT_BLOCK);
		patchingStacksOfSets.popAndPatchSet(PatchingStacksOfSets.BREAK);
	}

	@Override
	public void visit(BreakStatement breakStatement){
		Code.putJump(0);
		patchingStacksOfSets.addAfterJumpToCurrentSet(PatchingStacksOfSets.BREAK);
	}

	@Override
	public void visit(ContinueStatement continueStatement){
		Code.putJump(patchingStacks.peekOnStartingStack()); // This will peek depending on what is the current loop, either on while or foreach
	}

	/*
	 * Designator "." "foreach" "(" ident "=>" Statement ")" ";" * za B i C nivo
	 */

	@Override
	public void visit(ForeachStart foreachStart){

		patchingStacksOfSets.initializeNewSet(PatchingStacksOfSets.BREAK); // Init new set for breaks, which should jump to the end of THIS foreach

		// patchingStacks.pushOnStartingStack(PatchingStacks.START_FOREACH);
		ForeachStatement foreachStatement = (ForeachStatement) foreachStart.getParent();
		Code.load(foreachStatement.getForEachDesignator().getDesignator().obj); // load array
		Code.loadConst(-1); // load initial index
		patchingStacks.pushOnStartingPositionStack(PatchingStacks.START_FOREACH); // Save starting position of foreach

		Code.loadConst(1); // load incement
		Code.put(Code.add); // increment index
		Code.put(Code.dup); // duplicate index -> array, index, index
		Code.load(foreachStatement.getForEachDesignator().getDesignator().obj); // load array -> array, index, index, array
		
		Code.put(Code.arraylength); // get array length -> array, index, index, length
		Code.putFalseJump(Code.lt, 0); // if index < length -> array, index
		patchingStacks.pushAfterJumpCall(PatchingStacks.END_FOREACH); // Memorize that we need to patch this jump to the end of for each
		
		Code.put(Code.dup2); // duplicate array and index -> array, index, array, index
		if (foreachStatement.getForEachDesignator().getDesignator().obj.getType().getElemType().getKind() != Struct.Char){
			Code.put(Code.aload); // get element from array -> array, index, element
		} else {
			Code.put(Code.baload); // get char from array -> array, index, char
		}

		Code.store(foreachStatement.getForEachInterator().obj); // store element in designator -> array, index
	}

	@Override 
	public void visit (ForeachStatement foreachStatement){
		int foreachStartPc = patchingStacks.popFromStartingStack(PatchingStacks.START_FOREACH);
		Code.putJump(foreachStartPc);
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_FOREACH);
		patchingStacksOfSets.popAndPatchSet(PatchingStacksOfSets.BREAK);

		Code.put(Code.pop); // remove index
		Code.put(Code.pop); // remove array
	}

	/*
	 * Designator ʺ=ʺ Designator ʺ.ʺ ʺfindAnyʺ "(" Expr ")" ";".
	 */


	@Override
	public void visit(FindAnyStart findAnyStart){
		DesignatorFindAnyStatement designatorFindAnyStatement = (DesignatorFindAnyStatement) findAnyStart.getParent();
		Obj arrayObj = designatorFindAnyStatement.getDesignator1().obj;
		Code.load(arrayObj); // load array
		Code.loadConst(-1); // load initial index
		patchingStacks.pushOnStartingPositionStack(PatchingStacks.START_FINDANY); // Save starting position of foreach
		
		Code.loadConst(1); // load incement
		Code.put(Code.add); // increment index -> array, index (incremented)
		Code.put(Code.dup); // duplicate index -> array, index, index
		Code.load(arrayObj); // load array -> array, index, index, array
		Code.put(Code.arraylength); // get array length -> array, index, index, length

		Code.putFalseJump(Code.lt, 0); // if index < length -> array, index
		patchingStacks.pushAfterJumpCall(PatchingStacks.END_FINDANY); // Memorize that we need to patch this jump to the end of findany
		Code.put(Code.dup2); // duplicate array and index -> array, index, array, index
		// System.out.println("Array obj type: " + arrayObj.getType().getKind());
		if (arrayObj.getType().getElemType().getKind() != Struct.Char){
			Code.put(Code.aload); // get element from array -> array, index, element
		} else {
			Code.put(Code.baload); // get char from array -> array, index, char
		}
		
	}

	 @Override
	 public void visit(DesignatorFindAnyStatement designatorFindAnyStatement){
		// Code.store(designatorFindAnyStatement.getDesignator().obj);

		// Stack: array, index, elem, expr_res
		Code.putFalseJump(Code.eq, patchingStacks.peekOnStartingStack()); // jumping if elems not equal check for next one
		Code.put(Code.pop); // removing index
		Code.put(Code.pop); // removing array
		Code.loadConst(1); // Elements are equal, loading boolean true
		Code.putJump(0); // Jumping to the end of findany
		int patchAddress = Code.pc - 2;
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_FINDANY); // Patching jump to the end of findany
		Code.put(Code.pop); // removing index
		Code.put(Code.pop); // removing array
		Code.loadConst(0); // Elements are not equal, loading boolean false
		Code.fixup(patchAddress); // Fixing jump to the end of findany

		Designator boolDesignator = designatorFindAnyStatement.getDesignator();
		Code.store(boolDesignator.obj); // storing result in designator
		patchingStacks.popFromStartingStack(PatchingStacks.START_FINDANY); // Poping starting position of findany
	 }

	 /*
	  * Designator ʺ=ʺ Designator ʺ.ʺ ʺfindAndReplaceʺ "(" Expr "," ident "=>" Expr ")" ";"
	  	 Designator Assignop Designator DOT FindAndReplaceStart FINDANDREPLACE LPAREN Expr FindAndReplaceCondExprFinished COMMA FindAndReplaceIterator ARROW Expr RPAREN SEMI |
	  */

	@Override 
	public void visit(FindAndReplaceStart findAndReplaceStart){
		DesignatorFindAndReplaceStatement designatorFindAndReplaceStatement = (DesignatorFindAndReplaceStatement) findAndReplaceStart.getParent();
		Obj originalArrObj = designatorFindAndReplaceStatement.getDesignator1().obj;
		Code.load(originalArrObj); // load array
		Code.put(Code.arraylength); // put lenght -> len
		Code.put(Code.newarray);
		if(originalArrObj.getType().getElemType().getKind() == Tab.charType.getKind())
			Code.put(0);
		else
			Code.put(1); 

		Code.load(originalArrObj);
		Code.loadConst(-1); // load initial index -> arr2, arr, index
		patchingStacks.pushOnStartingPositionStack(PatchingStacks.START_FINDANDREPLACE); // Save starting position of foreach
		Code.loadConst(1); // load 1 -> arr2, arr, index, 1
		Code.put(Code.add); // increment index -> arr2, arr, index (incremented)
		Code.put(Code.dup); // duplicate index -> arr2, arr, index, index
		Code.load(originalArrObj); // load original array -> arr2, arr, index, index, arr
		Code.put(Code.arraylength); // get array length -> arr2, arr, index, index, len
		Code.putFalseJump(Code.lt, 0); // if index < length -> arr2, arr, index
		patchingStacks.pushAfterJumpCall(PatchingStacks.END_FINDANDREPLACE); // Memorize that we need to patch this jump to the end of findany
		Code.put(Code.dup2); // duplicate array and index -> arr2, arr, index, arr, index
		
		if (originalArrObj.getType().getElemType().getKind() != Struct.Char){
			Code.put(Code.aload); // get element from array -> arr2, arr, index, element
		} else {
			Code.put(Code.baload); // get char from array -> arr2, arr, index, char
		}
		Code.store(designatorFindAndReplaceStatement.getFindAndReplaceIterator().obj); // store element in iterator -> arr2, arr, index
		// Now our stack looks like this -> arr2, arr, index
		// Step 1: dup_x2 -> index, arr2, arr, index
		// Step 2: pop -> index, arr2, arr
		// Step 3: pop -> index, arr2
		// Step 4: dup2 -> index, arr2, index, arr2
		// Step 5: dup_x1 -> index, arr2, arr2, index, arr2
		// Step 6: pop -> index, arr2, arr2, index
		// Step 7: load iterator -> index, arr2, arr2, index, elem
		// Step 8: store -> index, arr2
		// Step 9: dup_x1 -> arr2, index, arr2
		// Step 10: pop -> arr2, index
		// Step 11: load original array -> arr2, index, arr
		// Step 12: dup_x1 -> arr2, arr, index, arr
		// Step 13: pop -> arr2, arr, index

		Code.put(Code.dup_x2); // dupx2 -> index, arr2, arr, index
		Code.put(Code.pop); // pop -> index, arr2, arr
		Code.put(Code.pop); // pop -> index, arr2
		Code.put(Code.dup2); // dup2 -> index, arr2, index, arr2
		Code.put(Code.dup_x1); // dupx1 -> index, arr2, arr2, index, arr2
		Code.put(Code.pop); // pop -> index, arr2, arr2, index
		Code.load(designatorFindAndReplaceStatement.getFindAndReplaceIterator().obj); // load iterator -> index, arr2, arr2, index, elem
		if(designatorFindAndReplaceStatement.getDesignator().obj.getType().getElemType().getKind() == Tab.charType.getKind())
			Code.put(Code.bastore);
		else
			Code.put(Code.astore); // store -> index, arr2
		Code.put(Code.dup_x1); // dupx1 -> arr2, index, arr2
		Code.put(Code.pop); // pop -> arr2, index
		Code.load(originalArrObj); // load original array -> arr2, index, arr
		Code.put(Code.dup_x1); // dupx1 -> arr2, arr, index, arr
		Code.put(Code.pop); // pop -> arr2, arr, index

		Code.load(designatorFindAndReplaceStatement.getFindAndReplaceIterator().obj); // load elem back -> arr2, arr, index, elem 
	}

	@Override
	public void visit(FindAndReplaceCondExprFinished findAndReplaceCondExprFinished){
		DesignatorFindAndReplaceStatement designatorFindAndReplaceStatement = (DesignatorFindAndReplaceStatement) findAndReplaceCondExprFinished.getParent();
		// Stack: arr, index, elem, expr_res
		Code.putFalseJump(Code.eq, patchingStacks.peekOnStartingStack()); // jumping to starting position of foreach if false, we do not replace this element
		// if true we continue, current stack: arr2, arr, index
		// Now our stack looks like this -> arr2, arr, index
		// Step 1: dup_x2 -> index, arr2, arr, index
		// Step 2: pop -> index, arr2, arr
		// Step 3: pop -> index, arr2
		// Step 4: dup2 -> index, arr2, index, arr2
		// Step 5: dup_x1 -> index, arr2, arr2, index, arr2
		// Step 6: pop -> index, arr2, arr2, index
		// Step 7: load iterator -> index, arr2, arr2, index, elem
		// Step 8: store -> index, arr2
		// Step 9: dup_x1 -> arr2, index, arr2
		// Step 10: pop -> arr2, index
		// Step 11: load original array -> arr2, index, arr
		// Step 12: dup_x1 -> arr2, arr, index, arr
		// Step 13: pop -> arr2, arr, index

		Code.put(Code.dup_x2); // dupx2 -> index, arr2, arr, index
		Code.put(Code.pop); // pop -> index, arr2, arr
		Code.put(Code.pop); // pop -> index, arr2
		Code.put(Code.dup2); // dup2 -> index, arr2, index, arr2
		Code.put(Code.dup_x1); // dupx1 -> index, arr2, arr2, index, arr2
		Code.put(Code.pop); // pop -> index, arr2, arr2, index

		// Now we wait for curr expr to finish
	}

	@Override
	public void visit (DesignatorFindAndReplaceStatement designatorFindAndReplaceStatement){
		// Now our stack looks like this -> index, arr2, arr2, index, expr_res
		if(designatorFindAndReplaceStatement.getDesignator().obj.getType().getElemType().getKind() == Tab.charType.getKind())
			Code.put(Code.bastore);
		else
			Code.put(Code.astore);
		// We stored the value, our stack looks like this -> index, arr2
		Code.put(Code.dup_x1); // dupx1 -> arr2, index, arr2
		Code.put(Code.pop); // pop -> arr2, index
		Code.load(designatorFindAndReplaceStatement.getDesignator1().obj); // load original array -> arr2, index, arr
		Code.put(Code.dup_x1); // dupx1 -> arr2, arr, index, arr
		Code.put(Code.pop); // pop -> arr2, arr, index
		Code.putJump(patchingStacks.peekOnStartingStack()); // Jumping to the starting position of foreach
		// HERE IS THE AND OF FIND AND REPLACE
		patchingStacks.patchAndPopFromEndStack(PatchingStacks.END_FINDANDREPLACE); // Patching jump to the end of findandreplce
		patchingStacks.popFromStartingStack(PatchingStacks.START_FINDANDREPLACE); // Poping starting position of findandreplce, we no longer need it
		// At the end, stack looks like this: arr2, arr, index, len,
		Code.put(Code.pop); // remove index
		Code.put(Code.pop); // remove arr
		Code.store(designatorFindAndReplaceStatement.getDesignator().obj); // store arr2 in designator
	}

	/* Expr stuff */

	@Override
	public void visit(Expr_AddopTerm exprAddopTerm){
		if (exprAddopTerm.getAddop() instanceof Addition){
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	@Override
	public void visit(Expr_MinusTerm exprMinusTerm){
		Code.put(Code.neg);
	}

	/* Factor shizz */

		@Override
	public void visit(Factor_Designator designator){
		Code.load(designator.getDesignator().obj);
	}

	@Override
	public void visit(DesignatorArrayName designatorArrayName){
		Code.load(designatorArrayName.getDesignator().obj);
	}

	@Override
	public void visit(Factor_NumConst factorNumConst){
		Code.loadConst(factorNumConst.getN1());
	}

	@Override
	public void visit(Factor_CharConst factorCharConst){
		Code.loadConst(factorCharConst.getC1().charAt(1));
	}

	@Override
	public void visit(Factor_BoolConst factorBoolConst){
		Code.loadConst(factorBoolConst.getB1() ? 1 : 0);
	}

	@Override
	public void visit(Term_MulopFactor termMulopFactor) {
		switch (termMulopFactor.getMulop().getClass().getSimpleName()) {
			case "Division":
				Code.put(Code.div);
				break;
			case "Modulo":
				Code.put(Code.rem);
				break;
			case "Multiplication":
				Code.put(Code.mul);
				break;
			default:
				throw new RuntimeErrorException(null, "Unknown mulop operation");
		}
	}

	@Override
	public void visit(Factor_NewArray factor_NewArray){
		Code.put(Code.newarray);
		if(factor_NewArray.struct.getElemType().getKind() != Tab.charType.getKind())
			Code.put(1);
		else
			Code.put(0);
	}


}
