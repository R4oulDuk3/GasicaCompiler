// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class VarDecl_FinalOneOrMany extends VarDecl {

    private Final Final;
    private Type Type;
    private OneOfVarDecl OneOfVarDecl;
    private ZeroOrManyVarDecl ZeroOrManyVarDecl;

    public VarDecl_FinalOneOrMany (Final Final, Type Type, OneOfVarDecl OneOfVarDecl, ZeroOrManyVarDecl ZeroOrManyVarDecl) {
        this.Final=Final;
        if(Final!=null) Final.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OneOfVarDecl=OneOfVarDecl;
        if(OneOfVarDecl!=null) OneOfVarDecl.setParent(this);
        this.ZeroOrManyVarDecl=ZeroOrManyVarDecl;
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.setParent(this);
    }

    public Final getFinal() {
        return Final;
    }

    public void setFinal(Final Final) {
        this.Final=Final;
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
        if(Final!=null) Final.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.accept(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Final!=null) Final.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.traverseTopDown(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Final!=null) Final.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OneOfVarDecl!=null) OneOfVarDecl.traverseBottomUp(visitor);
        if(ZeroOrManyVarDecl!=null) ZeroOrManyVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl_FinalOneOrMany(\n");

        if(Final!=null)
            buffer.append(Final.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
        buffer.append(") [VarDecl_FinalOneOrMany]");
        return buffer.toString();
    }
}
