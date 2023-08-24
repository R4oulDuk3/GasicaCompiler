// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private OneOfNumCharBoolConst OneOfNumCharBoolConst;
    private ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst;

    public ConstDecl (Type Type, OneOfNumCharBoolConst OneOfNumCharBoolConst, ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OneOfNumCharBoolConst=OneOfNumCharBoolConst;
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.setParent(this);
        this.ZeroOrManyNumCharBoolConst=ZeroOrManyNumCharBoolConst;
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OneOfNumCharBoolConst getOneOfNumCharBoolConst() {
        return OneOfNumCharBoolConst;
    }

    public void setOneOfNumCharBoolConst(OneOfNumCharBoolConst OneOfNumCharBoolConst) {
        this.OneOfNumCharBoolConst=OneOfNumCharBoolConst;
    }

    public ZeroOrManyNumCharBoolConst getZeroOrManyNumCharBoolConst() {
        return ZeroOrManyNumCharBoolConst;
    }

    public void setZeroOrManyNumCharBoolConst(ZeroOrManyNumCharBoolConst ZeroOrManyNumCharBoolConst) {
        this.ZeroOrManyNumCharBoolConst=ZeroOrManyNumCharBoolConst;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.accept(visitor);
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.traverseTopDown(visitor);
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OneOfNumCharBoolConst!=null) OneOfNumCharBoolConst.traverseBottomUp(visitor);
        if(ZeroOrManyNumCharBoolConst!=null) ZeroOrManyNumCharBoolConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneOfNumCharBoolConst!=null)
            buffer.append(OneOfNumCharBoolConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ZeroOrManyNumCharBoolConst!=null)
            buffer.append(ZeroOrManyNumCharBoolConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
