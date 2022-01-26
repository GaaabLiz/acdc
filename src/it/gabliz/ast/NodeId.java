package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

public class NodeId extends NodeAST {

    private String name;
    private Attributes definition;

    public NodeId (String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);

    }

    public Attributes getDefinition() {
        return definition;
    }

    public void setDefinition(Attributes definition) {
        this.definition = definition;
    }


}
