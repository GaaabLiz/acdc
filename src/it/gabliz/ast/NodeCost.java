package it.gabliz.ast;

public class NodeCost extends NodeExpr {

    private String value;
    private LangType type;

    public NodeCost(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "value = " + value + ", type: " + type;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public LangType getType() {
        return type;
    }


    public void setType(LangType type) {
        this.type = type;
    }

}
