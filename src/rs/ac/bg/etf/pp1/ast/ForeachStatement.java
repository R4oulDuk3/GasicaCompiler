// generated with ast extension for cup
// version 0.8
// 24/7/2023 19:50:34


package rs.ac.bg.etf.pp1.ast;

public class ForeachStatement extends Statement {

    private ForEachDesignator ForEachDesignator;
    private ForeachStart ForeachStart;
    private ForEachInterator ForEachInterator;
    private Statement Statement;

    public ForeachStatement (ForEachDesignator ForEachDesignator, ForeachStart ForeachStart, ForEachInterator ForEachInterator, Statement Statement) {
        this.ForEachDesignator=ForEachDesignator;
        if(ForEachDesignator!=null) ForEachDesignator.setParent(this);
        this.ForeachStart=ForeachStart;
        if(ForeachStart!=null) ForeachStart.setParent(this);
        this.ForEachInterator=ForEachInterator;
        if(ForEachInterator!=null) ForEachInterator.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForEachDesignator getForEachDesignator() {
        return ForEachDesignator;
    }

    public void setForEachDesignator(ForEachDesignator ForEachDesignator) {
        this.ForEachDesignator=ForEachDesignator;
    }

    public ForeachStart getForeachStart() {
        return ForeachStart;
    }

    public void setForeachStart(ForeachStart ForeachStart) {
        this.ForeachStart=ForeachStart;
    }

    public ForEachInterator getForEachInterator() {
        return ForEachInterator;
    }

    public void setForEachInterator(ForEachInterator ForEachInterator) {
        this.ForEachInterator=ForEachInterator;
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
        if(ForEachDesignator!=null) ForEachDesignator.accept(visitor);
        if(ForeachStart!=null) ForeachStart.accept(visitor);
        if(ForEachInterator!=null) ForEachInterator.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForEachDesignator!=null) ForEachDesignator.traverseTopDown(visitor);
        if(ForeachStart!=null) ForeachStart.traverseTopDown(visitor);
        if(ForEachInterator!=null) ForEachInterator.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForEachDesignator!=null) ForEachDesignator.traverseBottomUp(visitor);
        if(ForeachStart!=null) ForeachStart.traverseBottomUp(visitor);
        if(ForEachInterator!=null) ForEachInterator.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachStatement(\n");

        if(ForEachDesignator!=null)
            buffer.append(ForEachDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForeachStart!=null)
            buffer.append(ForeachStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEachInterator!=null)
            buffer.append(ForEachInterator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachStatement]");
        return buffer.toString();
    }
}
