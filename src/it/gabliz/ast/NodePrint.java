package it.gabliz.ast;


import it.gabliz.visitor.IVisitor;

public class NodePrint extends NodeStm {
	
	private NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	public NodeId getId() {
		return id;
	}
	public String toString() {
		return "Print: " + id;
	}
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);

	}



}
