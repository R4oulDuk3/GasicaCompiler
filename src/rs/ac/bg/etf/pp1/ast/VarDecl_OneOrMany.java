// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class VarDecl_OneOrMany extends VarDecl {

    private Type Type;
    private OneOfVarDecl OneOfVarDecl;
    private ZeroOrManyVarDecl ZeroOrManyVarDecl;

    public VarDecl_OneOrMany (Type Type, OneOfVarDecl OneOfVarDecl, ZeroOrManyVarDecl ZeroOrManyVarDecl) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OneOfVarDecl=OneOfVarDecl;
        if(OneOfVarDecl!=null) OneOfVarDecl.setParent(this);
        this.ZeroOrManyVarDecl=ZeroOrManyVarDecl;
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OneOfVarDecl getOneOfVarDecl() {
        return OneOfVarDecl;
    }

    public void setOneOfVarDecl(OneOfVarDecl OneOfVarDecl) {
        this.OneOfVarDecl=OneOfVarDecl;
    }

    public ZeroOrManyVarDecl getZeroOrManyVarDecl() {
        return ZeroOrManyVarDecl;
    }

    public void setZeroOrManyVarDecl(ZeroOrManyVarDecl ZeroOrManyVarDecl) {
        this.ZeroOrManyVarDecl=ZeroOrManyVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.accept(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.traverseTopDown(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.traverseBottomUp(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl_OneOrMany(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneOfVarDecl!=null)
            buffer.append(OneOfVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ZeroOrManyVarDecl!=null)
            buffer.append(ZeroOrManyVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl_OneOrMany]");
        return buffer.toString();
    }
}
