// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl_None extends MethodDeclList {

    public MethodDecl_None () {
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
        buffer.append("MethodDecl_None(\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl_None]");
        return buffer.toString();
    }
}
