package it.gabliz.ast;

import java.util.ArrayList;

/** Classe che rappresenta il nodo programma */
public class NodeProgram extends NodeAST {

    private final ArrayList<NodeDecSt> n;

    public NodeProgram(ArrayList<NodeDecSt> n) {
        this.n = n;
    }

    public String toString() {
        String s = "";
        for(NodeDecSt node : n) {
            s += "<" + node.toString() + ">";
        }

        return s;
    }


    public ArrayList<NodeDecSt> getN() {
        return n;
    }

}
