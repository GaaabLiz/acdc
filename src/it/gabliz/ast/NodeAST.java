package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

/** Classe astratta che rappresenta un nodo nell'ast */
public abstract class NodeAST {

    private TypeDescriptor typeDescriptor;

    public abstract void accept(IVisitor visitor);

    public TypeDescriptor getTypeDescriptor() {
        return typeDescriptor;
    }

    public void setTypeDescriptor(TypeDescriptor typeDescriptor) {
        this.typeDescriptor = typeDescriptor;
    }

}
