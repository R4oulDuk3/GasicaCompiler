// generated with ast extension for cup
// version 0.8
// 22/7/2023 22:46:12


package rs.ac.bg.etf.pp1.ast;

public class LessThanOrEqual extends Relop {

    public LessThanOrEqual () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LessThanOrEqual(\n");

        buffer.append(tab);
        buffer.append(") [LessThanOrEqual]");
        return buffer.toString();
    }
}
