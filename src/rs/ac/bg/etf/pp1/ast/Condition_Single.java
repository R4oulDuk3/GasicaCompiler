// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class Condition_Single extends Condition {

    private CondTermWrapper CondTermWrapper;

    public Condition_Single (CondTermWrapper CondTermWrapper) {
        this.CondTermWrapper=CondTermWrapper;
        if(CondTermWrapper!=null) CondTermWrapper.setParent(this);
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
        if(CondTermWrapper!=null) CondTermWrapper.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTermWrapper!=null) CondTermWrapper.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTermWrapper!=null) CondTermWrapper.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Condition_Single(\n");

        if(CondTermWrapper!=null)
            buffer.append(CondTermWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Condition_Single]");
        return buffer.toString();
    }
}
