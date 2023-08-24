// generated with ast extension for cup
// version 0.8
// 24/7/2023 19:50:34


package rs.ac.bg.etf.pp1.ast;

public class Statement_Multiple extends StatementList {

    private StatementList StatementList;
    private StatementWrapper StatementWrapper;

    public Statement_Multiple (StatementList StatementList, StatementWrapper StatementWrapper) {
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.StatementWrapper=StatementWrapper;
        if(StatementWrapper!=null) StatementWrapper.setParent(this);
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public StatementWrapper getStatementWrapper() {
        return StatementWrapper;
    }

    public void setStatementWrapper(StatementWrapper StatementWrapper) {
        this.StatementWrapper=StatementWrapper;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementList!=null) StatementList.accept(visitor);
        if(StatementWrapper!=null) StatementWrapper.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(StatementWrapper!=null) StatementWrapper.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(StatementWrapper!=null) StatementWrapper.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_Multiple(\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementWrapper!=null)
            buffer.append(StatementWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_Multiple]");
        return buffer.toString();
    }
}
