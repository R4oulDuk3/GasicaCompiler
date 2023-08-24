// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class OptionalActParsOneOrMany_Multiple extends OptionalActParsOneOrMany {

    private OptionalActParsOneOrMany OptionalActParsOneOrMany;
    private Expr Expr;

    public OptionalActParsOneOrMany_Multiple (OptionalActParsOneOrMany OptionalActParsOneOrMany, Expr Expr) {
        this.OptionalActParsOneOrMany=OptionalActParsOneOrMany;
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public OptionalActParsOneOrMany getOptionalActParsOneOrMany() {
        return OptionalActParsOneOrMany;
    }

    public void setOptionalActParsOneOrMany(OptionalActParsOneOrMany OptionalActParsOneOrMany) {
        this.OptionalActParsOneOrMany=OptionalActParsOneOrMany;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptionalActParsOneOrMany_Multiple(\n");

        if(OptionalActParsOneOrMany!=null)
            buffer.append(OptionalActParsOneOrMany.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptionalActParsOneOrMany_Multiple]");
        return buffer.toString();
    }
}
