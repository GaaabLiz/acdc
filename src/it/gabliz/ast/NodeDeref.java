package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

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

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
