// generated with ast extension for cup
// version 0.8
// 19/7/2023 15:47:25


package rs.ac.bg.etf.pp1.ast;

public class EmptyConstVarDeclList_None extends ConstVarDeclList {

    public EmptyConstVarDeclList_None () {
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
        buffer.append("EmptyConstVarDeclList_None(\n");

        buffer.append(tab);
        buffer.append(") [EmptyConstVarDeclList_None]");
        return buffer.toString();
    }
}
