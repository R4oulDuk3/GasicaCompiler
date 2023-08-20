// generated with ast extension for cup
// version 0.8
// 19/7/2023 23:44:40


package rs.ac.bg.etf.pp1.ast;

public class CharConst_Single extends OneOfNumCharBoolConst {

    private String I1;
    private Assignop Assignop;
    private String C3;

    public CharConst_Single (String I1, Assignop Assignop, String C3) {
        this.I1=I1;
        this.Assignop=Assignop;
        if(Assignop!=null) Assignop.setParent(this);
        this.C3=C3;
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public Assignop getAssignop() {
        return Assignop;
    }

    public void setAssignop(Assignop Assignop) {
        this.Assignop=Assignop;
    }

    public String getC3() {
        return C3;
    }

    public void setC3(String C3) {
        this.C3=C3;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Assignop!=null) Assignop.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Assignop!=null) Assignop.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Assignop!=null) Assignop.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CharConst_Single(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(Assignop!=null)
            buffer.append(Assignop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+C3);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharConst_Single]");
        return buffer.toString();
    }
}
