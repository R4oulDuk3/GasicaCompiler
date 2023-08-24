// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class Var_Multiple extends ZeroOrManyVarDecl {

    private ZeroOrManyVarDecl ZeroOrManyVarDecl;
    private OneOfVarDecl OneOfVarDecl;

    public Var_Multiple (ZeroOrManyVarDecl ZeroOrManyVarDecl, OneOfVarDecl OneOfVarDecl) {
        this.ZeroOrManyVarDecl=ZeroOrManyVarDecl;
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.setParent(this);
        this.OneOfVarDecl=OneOfVarDecl;
        if(OneOfVarDecl!=null) OneOfVarDecl.setParent(this);
    }

    public ZeroOrManyVarDecl getZeroOrManyVarDecl() {
        return ZeroOrManyVarDecl;
    }

    public void setZeroOrManyVarDecl(ZeroOrManyVarDecl ZeroOrManyVarDecl) {
        this.ZeroOrManyVarDecl=ZeroOrManyVarDecl;
    }

    public OneOfVarDecl getOneOfVarDecl() {
        return OneOfVarDecl;
    }

    public void setOneOfVarDecl(OneOfVarDecl OneOfVarDecl) {
        this.OneOfVarDecl=OneOfVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.accept(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.traverseTopDown(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.traverseBottomUp(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Var_Multiple(\n");

        if(ZeroOrManyVarDecl!=null)
            buffer.append(ZeroOrManyVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneOfVarDecl!=null)
            buffer.append(OneOfVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Var_Multiple]");
        return buffer.toString();
    }
}
