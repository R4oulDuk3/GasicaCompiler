// generated with ast extension for cup
// version 0.8
// 21/7/2023 13:42:17


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatement_Error extends DesignatorStatement {

    public DesignatorStatement_Error () {
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
        buffer.append("DesignatorStatement_Error(\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatement_Error]");
        return buffer.toString();
    }
}
