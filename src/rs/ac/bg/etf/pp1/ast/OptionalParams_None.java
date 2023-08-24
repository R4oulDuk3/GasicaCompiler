// generated with ast extension for cup
// version 0.8
// 24/7/2023 18:32:15


package rs.ac.bg.etf.pp1.ast;

public class OptionalParams_None extends OptionalParams {

    public OptionalParams_None () {
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
        buffer.append("OptionalParams_None(\n");

        buffer.append(tab);
        buffer.append(") [OptionalParams_None]");
        return buffer.toString();
    }
}
