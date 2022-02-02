package it.gabliz.visitor;

import it.gabliz.ast.*;
import it.gabliz.symboltable.SymbolTable;

public class CodeGeneratorVisitor implements IVisitor {

    private static char[] reg;
    private StringBuffer codice;
    static int i = 0;

    public CodeGeneratorVisitor() {
        this.codice = new StringBuffer();
        reg = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    }

    @Override
    public void visit(NodeProgram node) {
        SymbolTable.init();
        for(NodeAST currentNode : node.getN())
            currentNode.accept(this);
    }

    @Override
    public void visit(NodeAssign node) {
        NodeId id = node.getId();
        char s = id.getDefinition().getRegister();
        node.getExpr().accept(this);
        codice.append("s").append(s);
        codice.append(" 0 k ");
    }

    @Override
    public void visit(NodeBinOp node) {
        NodeExpr left = node.getLeft();
        NodeExpr right = node.getRight();
        left.accept(this);
        right.accept(this);

        switch(node.getOp()) {
            case PLUS:
                codice.append("+ ");
                break;
            case MINUS:
                codice.append("- ");
                break;
            case TIMES:
                codice.append("* ");
                break;
            case DIV:
                codice.append("/ ");
                break;
            default:
                break;
        }
    }

    @Override
    public void visit(NodeCost node) {
        codice.append(node.getValue()).append(" ");
    }

    @Override
    public void visit(NodeDecl node) {
        NodeId id = node.getId();
        id.getDefinition().setRegister(newRegister());
    }

    private static char newRegister() {
        char c = reg[i];
        i++;
        return c;
    }

    @Override
    public void visit(NodeDeref node) {
        NodeId id = node.getId();
        char s = id.getDefinition().getRegister();
        codice.append("l ").append(s).append(" ");
    }

    @Override
    public void visit(NodeId node) {

    }

    @Override
    public void visit(NodePrint node) {
        NodeId id = node.getId();
        char s = id.getDefinition().getRegister();
        codice.append("l").append(s).append(" p P ");
    }

    @Override
    public void visit(NodeConv node) {
        node.getN().accept(this);
        codice.append("5 k ");
    }

    public String toString() {
        return codice.toString().trim();
    }

}
