// generated with ast extension for cup
// version 0.8
// 19/7/2023 15:47:25


package rs.ac.bg.etf.pp1.ast;

public class NumConstList_Multiple extends NumConstList {

    private NumConstList NumConstList;
    private Integer N2;

    public NumConstList_Multiple (NumConstList NumConstList, Integer N2) {
        this.NumConstList=NumConstList;
        if(NumConstList!=null) NumConstList.setParent(this);
        this.N2=N2;
    }

    public NumConstList getNumConstList() {
        return NumConstList;
    }

    public void setNumConstList(NumConstList NumConstList) {
        this.NumConstList=NumConstList;
    }

    public Integer getN2() {
        return N2;
    }

    public void setN2(Integer N2) {
        this.N2=N2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NumConstList!=null) NumConstList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NumConstList!=null) NumConstList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NumConstList!=null) NumConstList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumConstList_Multiple(\n");

        if(NumConstList!=null)
            buffer.append(NumConstList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+N2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumConstList_Multiple]");
        return buffer.toString();
    }
}
