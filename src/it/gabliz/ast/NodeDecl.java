package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

public class NodeDecl extends NodeDecSt {
    private NodeId id;
    private LangType type;

    public NodeDecl(NodeId id, LangType type) {
        this.id = id;
        this.type = type;
    }

    public NodeId getId() {
        return id;
    }

    public LangType getType() {
        return type;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    public void setType(LangType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
