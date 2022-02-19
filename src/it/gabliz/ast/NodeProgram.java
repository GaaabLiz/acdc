package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

import java.util.ArrayList;

/** Classe che rappresenta il nodo programma */
public class NodeProgram extends NodeAST {

    private final ArrayList<NodeDecSt> n;

    public NodeProgram(ArrayList<NodeDecSt> n) {
        this.n = n;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for(NodeDecSt node : n) {
            s.append("<").append(node.toString()).append(">");
        }
        return s.toString();
    }

    public ArrayList<NodeDecSt> getN() {
        return n;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
