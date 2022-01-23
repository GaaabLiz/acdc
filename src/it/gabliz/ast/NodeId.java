package it.gabliz.ast;

public class NodeId extends NodeAST {

    private String name;

    public NodeId(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
