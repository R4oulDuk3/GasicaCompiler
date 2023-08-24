// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class Condition_OneOrMany extends Condition {

    private Condition Condition;
    private CondTermWrapper CondTermWrapper;

    public Condition_OneOrMany (Condition Condition, CondTermWrapper CondTermWrapper) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CondTermWrapper=CondTermWrapper;
        if(CondTermWrapper!=null) CondTermWrapper.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CondTermWrapper getCondTermWrapper() {
        return CondTermWrapper;
    }

    public void setCondTermWrapper(CondTermWrapper CondTermWrapper) {
        this.CondTermWrapper=CondTermWrapper;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CondTermWrapper!=null) CondTermWrapper.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CondTermWrapper!=null) CondTermWrapper.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CondTermWrapper!=null) CondTermWrapper.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Condition_OneOrMany(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTermWrapper!=null)
            buffer.append(CondTermWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Condition_OneOrMany]");
        return buffer.toString();
    }
}
