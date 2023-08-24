// generated with ast extension for cup
// version 0.8
// 23/7/2023 23:28:23


package rs.ac.bg.etf.pp1.ast;

public class OptionalParams_OneOrMany extends OptionalParams {

    private Parameter Parameter;
    private MoreParams MoreParams;

    public OptionalParams_OneOrMany (Parameter Parameter, MoreParams MoreParams) {
        this.Parameter=Parameter;
        if(Parameter!=null) Parameter.setParent(this);
        this.MoreParams=MoreParams;
        if(MoreParams!=null) MoreParams.setParent(this);
    }

    public Parameter getParameter() {
        return Parameter;
    }

    public void setParameter(Parameter Parameter) {
        this.Parameter=Parameter;
    }

    public MoreParams getMoreParams() {
        return MoreParams;
    }

    public void setMoreParams(MoreParams MoreParams) {
        this.MoreParams=MoreParams;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Parameter!=null) Parameter.accept(visitor);
        if(MoreParams!=null) MoreParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Parameter!=null) Parameter.traverseTopDown(visitor);
        if(MoreParams!=null) MoreParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Parameter!=null) Parameter.traverseBottomUp(visitor);
        if(MoreParams!=null) MoreParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptionalParams_OneOrMany(\n");

        if(Parameter!=null)
            buffer.append(Parameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MoreParams!=null)
            buffer.append(MoreParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptionalParams_OneOrMany]");
        return buffer.toString();
    }
}
