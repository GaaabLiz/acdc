package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

public class NodeConv extends NodeExpr {

    private NodeExpr n;

    public NodeConv(NodeExpr n) {
        this.n = n;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    public NodeExpr getN() {
        return n;
    }

    public void setN(NodeExpr n) {
        this.n = n;
    }
}
