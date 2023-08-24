// generated with ast extension for cup
// version 0.8
// 24/7/2023 19:50:34


package rs.ac.bg.etf.pp1.ast;

public class WhileStatement extends Statement {

    private WhileStart WhileStart;
    private ConditionWrapper ConditionWrapper;
    private WhileStatementStart WhileStatementStart;
    private Statement Statement;

    public WhileStatement (WhileStart WhileStart, ConditionWrapper ConditionWrapper, WhileStatementStart WhileStatementStart, Statement Statement) {
        this.WhileStart=WhileStart;
        if(WhileStart!=null) WhileStart.setParent(this);
        this.ConditionWrapper=ConditionWrapper;
        if(ConditionWrapper!=null) ConditionWrapper.setParent(this);
        this.WhileStatementStart=WhileStatementStart;
        if(WhileStatementStart!=null) WhileStatementStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public WhileStart getWhileStart() {
        return WhileStart;
    }

    public void setWhileStart(WhileStart WhileStart) {
        this.WhileStart=WhileStart;
    }

    public ConditionWrapper getConditionWrapper() {
        return ConditionWrapper;
    }

    public void setConditionWrapper(ConditionWrapper ConditionWrapper) {
        this.ConditionWrapper=ConditionWrapper;
    }

    public WhileStatementStart getWhileStatementStart() {
        return WhileStatementStart;
    }

    public void setWhileStatementStart(WhileStatementStart WhileStatementStart) {
        this.WhileStatementStart=WhileStatementStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WhileStart!=null) WhileStart.accept(visitor);
        if(ConditionWrapper!=null) ConditionWrapper.accept(visitor);
        if(WhileStatementStart!=null) WhileStatementStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileStart!=null) WhileStart.traverseTopDown(visitor);
        if(ConditionWrapper!=null) ConditionWrapper.traverseTopDown(visitor);
        if(WhileStatementStart!=null) WhileStatementStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileStart!=null) WhileStart.traverseBottomUp(visitor);
        if(ConditionWrapper!=null) ConditionWrapper.traverseBottomUp(visitor);
        if(WhileStatementStart!=null) WhileStatementStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WhileStatement(\n");

        if(WhileStart!=null)
            buffer.append(WhileStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionWrapper!=null)
            buffer.append(ConditionWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(WhileStatementStart!=null)
            buffer.append(WhileStatementStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WhileStatement]");
        return buffer.toString();
    }
}
