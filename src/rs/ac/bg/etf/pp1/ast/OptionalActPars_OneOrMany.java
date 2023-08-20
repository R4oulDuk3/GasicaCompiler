// generated with ast extension for cup
// version 0.8
// 19/7/2023 23:44:40


package rs.ac.bg.etf.pp1.ast;

public class OptionalActPars_OneOrMany extends OptionalActPars {

    private OptionalActParsOneOrMany OptionalActParsOneOrMany;

    public OptionalActPars_OneOrMany (OptionalActParsOneOrMany OptionalActParsOneOrMany) {
        this.OptionalActParsOneOrMany=OptionalActParsOneOrMany;
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.setParent(this);
    }

    public OptionalActParsOneOrMany getOptionalActParsOneOrMany() {
        return OptionalActParsOneOrMany;
    }

    public void setOptionalActParsOneOrMany(OptionalActParsOneOrMany OptionalActParsOneOrMany) {
        this.OptionalActParsOneOrMany=OptionalActParsOneOrMany;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalActParsOneOrMany!=null) OptionalActParsOneOrMany.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptionalActPars_OneOrMany(\n");

        if(OptionalActParsOneOrMany!=null)
            buffer.append(OptionalActParsOneOrMany.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptionalActPars_OneOrMany]");
        return buffer.toString();
    }
}
