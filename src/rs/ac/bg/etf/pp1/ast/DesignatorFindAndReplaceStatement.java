// generated with ast extension for cup
// version 0.8
// 24/7/2023 19:50:34


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFindAndReplaceStatement extends Statement {

    private Designator Designator;
    private Assignop Assignop;
    private Designator Designator1;
    private FindAndReplaceStart FindAndReplaceStart;
    private Expr Expr;
    private FindAndReplaceCondExprFinished FindAndReplaceCondExprFinished;
    private FindAndReplaceIterator FindAndReplaceIterator;
    private Expr Expr2;

    public DesignatorFindAndReplaceStatement (Designator Designator, Assignop Assignop, Designator Designator1, FindAndReplaceStart FindAndReplaceStart, Expr Expr, FindAndReplaceCondExprFinished FindAndReplaceCondExprFinished, FindAndReplaceIterator FindAndReplaceIterator, Expr Expr2) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
        this.FindAndReplaceStart=FindAndReplaceStart;
        if(FindAndReplaceStart!=null) FindAndReplaceStart.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.FindAndReplaceCondExprFinished=FindAndReplaceCondExprFinished;
        if(FindAndReplaceCondExprFinished!=null) FindAndReplaceCondExprFinished.setParent(this);
        this.FindAndReplaceIterator=FindAndReplaceIterator;
        if(FindAndReplaceIterator!=null) FindAndReplaceIterator.setParent(this);
        this.Expr2=Expr2;
        if(Expr2!=null) Expr2.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public FindAndReplaceStart getFindAndReplaceStart() {
        return FindAndReplaceStart;
    }

    public void setFindAndReplaceStart(FindAndReplaceStart FindAndReplaceStart) {
        this.FindAndReplaceStart=FindAndReplaceStart;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public FindAndReplaceCondExprFinished getFindAndReplaceCondExprFinished() {
        return FindAndReplaceCondExprFinished;
    }

    public void setFindAndReplaceCondExprFinished(FindAndReplaceCondExprFinished FindAndReplaceCondExprFinished) {
        this.FindAndReplaceCondExprFinished=FindAndReplaceCondExprFinished;
    }

    public FindAndReplaceIterator getFindAndReplaceIterator() {
        return FindAndReplaceIterator;
    }

    public void setFindAndReplaceIterator(FindAndReplaceIterator FindAndReplaceIterator) {
        this.FindAndReplaceIterator=FindAndReplaceIterator;
    }

    public Expr getExpr2() {
        return Expr2;
    }

    public void setExpr2(Expr Expr2) {
        this.Expr2=Expr2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(Assignop!=null) Assignop.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
        if(FindAndReplaceStart!=null) FindAndReplaceStart.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
        if(FindAndReplaceCondExprFinished!=null) FindAndReplaceCondExprFinished.accept(visitor);
        if(FindAndReplaceIterator!=null) FindAndReplaceIterator.accept(visitor);
        if(Expr2!=null) Expr2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
        if(FindAndReplaceStart!=null) FindAndReplaceStart.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(FindAndReplaceCondExprFinished!=null) FindAndReplaceCondExprFinished.traverseTopDown(visitor);
        if(FindAndReplaceIterator!=null) FindAndReplaceIterator.traverseTopDown(visitor);
        if(Expr2!=null) Expr2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        if(FindAndReplaceStart!=null) FindAndReplaceStart.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(FindAndReplaceCondExprFinished!=null) FindAndReplaceCondExprFinished.traverseBottomUp(visitor);
        if(FindAndReplaceIterator!=null) FindAndReplaceIterator.traverseBottomUp(visitor);
        if(Expr2!=null) Expr2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFindAndReplaceStatement(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAndReplaceStart!=null)
            buffer.append(FindAndReplaceStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAndReplaceCondExprFinished!=null)
            buffer.append(FindAndReplaceCondExprFinished.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FindAndReplaceIterator!=null)
            buffer.append(FindAndReplaceIterator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr2!=null)
            buffer.append(Expr2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFindAndReplaceStatement]");
        return buffer.toString();
    }
}
