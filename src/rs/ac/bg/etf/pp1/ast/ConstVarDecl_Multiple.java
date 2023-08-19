// generated with ast extension for cup
// version 0.8
// 19/7/2023 15:47:25


package rs.ac.bg.etf.pp1.ast;

public class ConstVarDecl_Multiple extends ConstVarDeclList {

    private ConstVarDeclList ConstVarDeclList;
    private ConstVarDecl ConstVarDecl;

    public ConstVarDecl_Multiple (ConstVarDeclList ConstVarDeclList, ConstVarDecl ConstVarDecl) {
        this.ConstVarDeclList=ConstVarDeclList;
        if(ConstVarDeclList!=null) ConstVarDeclList.setParent(this);
        this.ConstVarDecl=ConstVarDecl;
        if(ConstVarDecl!=null) ConstVarDecl.setParent(this);
    }

    public ConstVarDeclList getConstVarDeclList() {
        return ConstVarDeclList;
    }

    public void setConstVarDeclList(ConstVarDeclList ConstVarDeclList) {
        this.ConstVarDeclList=ConstVarDeclList;
    }

    public ConstVarDecl getConstVarDecl() {
        return ConstVarDecl;
    }

    public void setConstVarDecl(ConstVarDecl ConstVarDecl) {
        this.ConstVarDecl=ConstVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstVarDeclList!=null) ConstVarDeclList.accept(visitor);
        if(ConstVarDecl!=null) ConstVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVarDeclList!=null) ConstVarDeclList.traverseTopDown(visitor);
        if(ConstVarDecl!=null) ConstVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVarDeclList!=null) ConstVarDeclList.traverseBottomUp(visitor);
        if(ConstVarDecl!=null) ConstVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstVarDecl_Multiple(\n");

        if(ConstVarDeclList!=null)
            buffer.append(ConstVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstVarDecl!=null)
            buffer.append(ConstVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstVarDecl_Multiple]");
        return buffer.toString();
    }
}
