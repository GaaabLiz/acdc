package it.gabliz.ast;


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


}
