package it.gabliz.ast;

public class NodeDeref extends NodeExpr {
    private NodeId id;

    public NodeDeref(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return id;
    }

    public String toString() {
        return "ID: " + id;
    }
}
