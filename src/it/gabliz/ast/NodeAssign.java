package it.gabliz.ast;

import it.gabliz.visitor.IVisitor;

public class NodeAssign extends NodeStm {
	
	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}
	
	public NodeId getId() {
		return id;
	}
	
	public NodeExpr getExpr() {
		return expr;
	}

	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}

	public String toString() {
		return "ID:  " + id + ", " + "Expr: " + expr;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
