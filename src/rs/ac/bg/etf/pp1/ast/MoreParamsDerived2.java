// generated with ast extension for cup
// version 0.8
// 24/7/2023 18:32:15


package rs.ac.bg.etf.pp1.ast;

public class MoreParamsDerived2 extends MoreParams {

    public MoreParamsDerived2 () {
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
        buffer.append("MoreParamsDerived2(\n");

        buffer.append(tab);
        buffer.append(") [MoreParamsDerived2]");
        return buffer.toString();
    }
}
