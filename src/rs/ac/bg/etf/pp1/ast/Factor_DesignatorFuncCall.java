// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class Factor_DesignatorFuncCall extends Factor {

    private FuncCallDesignator FuncCallDesignator;
    private OptionalActPars OptionalActPars;

    public Factor_DesignatorFuncCall (FuncCallDesignator FuncCallDesignator, OptionalActPars OptionalActPars) {
        this.FuncCallDesignator=FuncCallDesignator;
        if(FuncCallDesignator!=null) FuncCallDesignator.setParent(this);
        this.OptionalActPars=OptionalActPars;
        if(OptionalActPars!=null) OptionalActPars.setParent(this);
    }

    public FuncCallDesignator getFuncCallDesignator() {
        return FuncCallDesignator;
    }

    public void setFuncCallDesignator(FuncCallDesignator FuncCallDesignator) {
        this.FuncCallDesignator=FuncCallDesignator;
    }

    public OptionalActPars getOptionalActPars() {
        return OptionalActPars;
    }

    public void setOptionalActPars(OptionalActPars OptionalActPars) {
        this.OptionalActPars=OptionalActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FuncCallDesignator!=null) FuncCallDesignator.accept(visitor);
        if(OptionalActPars!=null) OptionalActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncCallDesignator!=null) FuncCallDesignator.traverseTopDown(visitor);
        if(OptionalActPars!=null) OptionalActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncCallDesignator!=null) FuncCallDesignator.traverseBottomUp(visitor);
        if(OptionalActPars!=null) OptionalActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor_DesignatorFuncCall(\n");

        if(FuncCallDesignator!=null)
            buffer.append(FuncCallDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalActPars!=null)
            buffer.append(OptionalActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor_DesignatorFuncCall]");
        return buffer.toString();
    }
}
