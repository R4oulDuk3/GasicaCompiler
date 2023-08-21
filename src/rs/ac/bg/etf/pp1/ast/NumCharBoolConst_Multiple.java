// generated with ast extension for cup
// version 0.8
// 21/7/2023 13:42:17


package rs.ac.bg.etf.pp1.ast;

public class NumCharBoolConst_Multiple extends ZeroOrManyNumCharBoolConst {

    private ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst;
    private OneOfNumCharBoolConst OneOfNumCharBoolConst;

    public NumCharBoolConst_Multiple (ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst, OneOfNumCharBoolConst OneOfNumCharBoolConst) {
        this.ZeroOrManyNumCharBoolConst=ZeroOrManyNumCharBoolConst;
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.setParent(this);
        this.OneOfNumCharBoolConst=OneOfNumCharBoolConst;
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.setParent(this);
    }

    public ZeroOrManyNumCharBoolConst getZeroOrManyNumCharBoolConst() {
        return ZeroOrManyNumCharBoolConst;
    }

    public void setZeroOrManyNumCharBoolConst(ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst) {
        this.ZeroOrManyNumCharBoolConst=ZeroOrManyNumCharBoolConst;
    }

    public OneOfNumCharBoolConst getOneOfNumCharBoolConst() {
        return OneOfNumCharBoolConst;
    }

    public void setOneOfNumCharBoolConst(OneOfNumCharBoolConst OneOfNumCharBoolConst) {
        this.OneOfNumCharBoolConst=OneOfNumCharBoolConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.accept(visitor);
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.traverseTopDown(visitor);
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.traverseBottomUp(visitor);
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NumCharBoolConst_Multiple(\n");

        if(ZeroOrManyNumCharBoolConst!=null)
            buffer.append(ZeroOrManyNumCharBoolConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneOfNumCharBoolConst!=null)
            buffer.append(OneOfNumCharBoolConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NumCharBoolConst_Multiple]");
        return buffer.toString();
    }
}
