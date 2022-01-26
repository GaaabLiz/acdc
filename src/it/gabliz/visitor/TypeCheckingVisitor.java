package it.gabliz.visitor;


import it.gabliz.ast.*;


public class TypeCheckingVisitor implements IVisitor {
	StringBuilder log = new StringBuilder();
	
	@Override
	public void visit(NodeProgram node) {
	}
	
	@Override
	public void visit(NodeAssign node) {
	}

	@Override
	public void visit(NodeBinOp node) {
		NodeExpr left=node.getLeft();
	}
	
	@Override
	public void visit(NodeConv node) {
	}
	
	@Override
	public void visit(NodeCost node) {
	}

	@Override
	public void visit(NodeDecl node) {
	}

	@Override
	public void visit(NodeDeref node) {
	}

	@Override
	public void visit(NodeId node) {
	}

	@Override
	public void visit(NodePrint node) {
	}
	
	
	private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
		return false;
	}
	
	private NodeExpr convertExpr(NodeExpr node) {
		return null;
	}
	
	public String log() {
		return log.toString();
	}
}
