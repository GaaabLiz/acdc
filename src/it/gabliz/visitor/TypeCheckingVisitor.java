package it.gabliz.visitor;


import it.gabliz.ast.*;
import it.gabliz.symboltable.Attributes;
import it.gabliz.symboltable.SymbolTable;
import it.gabliz.util.Logger;

/*
il programma chiama il visitor di tutti i nodi e no nsi ferma al primo errore.
il promamma ha un erroe se ce qualche nodo settato con ERROR:

 */


public class TypeCheckingVisitor implements IVisitor {
	private final Logger logger;

	public TypeCheckingVisitor() {
		logger = new Logger(this.getClass().getSimpleName());
		logger.i("Inizio fase di type checking.");
	}

	@Override
	public void visit(NodeProgram node) {
		SymbolTable.init();
		for(NodeAST currentNode : node.getN()) {
			currentNode.accept(this);
		}
	}
	
	@Override
	public void visit(NodeAssign node) {
		NodeId id = node.getId();
		NodeExpr exp = node.getExpr();
		id.accept(this);
		exp.accept(this);

		if(id.getTypeDescriptor().equals(TypeDescriptor.INT) && exp.getTypeDescriptor().equals(TypeDescriptor.INT)) {
			node.setExpr(exp);
			node.setTypeDescriptor(id.getTypeDescriptor());
		} else if( compatible(id.getTypeDescriptor(),exp.getTypeDescriptor()) ) {
			node.setExpr(convertExpr(exp));
			node.setTypeDescriptor(id.getTypeDescriptor());
		} else {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			logger.addTypeCheckingError("{NodeAssign} Impossibile assegnare l'esprresione.");
		}
	}

	@Override
	public void visit(NodeBinOp node) {
		NodeExpr left = node.getLeft();
		left.accept(this);
		NodeExpr right = node.getRight();
		right.accept(this);

		if(left.getTypeDescriptor() == TypeDescriptor.ERROR || right.getTypeDescriptor() == TypeDescriptor.ERROR) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			logger.addTypeCheckingError("{NodeBinOp} uno dei due operandi ha errore!");
		} else if(left.getTypeDescriptor() == right.getTypeDescriptor()) {
			node.setTypeDescriptor(left.getTypeDescriptor());
		} else {
			if(left.getTypeDescriptor() == TypeDescriptor.INT) {
				node.setLeft(convertExpr(left));
			} else if (right.getTypeDescriptor() == TypeDescriptor.INT) {
				node.setRight(convertExpr(right));
			}
			node.setTypeDescriptor(TypeDescriptor.FLOAT);
		}
	}
	
	@Override
	public void visit(NodeConv node) {
		NodeExpr n = node.getN();
		n.accept(this);
		if(n.getTypeDescriptor() != TypeDescriptor.INT) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			logger.addTypeCheckingError("{NodeConv} Rilevata conversione non consentita");
		} else {
			node.setTypeDescriptor(TypeDescriptor.FLOAT);
		}
	}
	
	@Override
	public void visit(NodeCost node) {
		if(node.getType() == LangType.INT) {
			node.setTypeDescriptor(TypeDescriptor.INT);
		} else if(node.getType() == LangType.FLOAT) {
			node.setTypeDescriptor(TypeDescriptor.FLOAT);
		} else {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			logger.addTypeCheckingError("{NodeConst} la costante " + node.getValue() + " è errata.");
		}
	}

	@Override
	public void visit(NodeDecl node) {
		NodeId id = node.getId();
		String idName = id.getName();
		LangType type = node.getType();
		if(SymbolTable.lookup(idName) != null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			logger.addTypeCheckingError("{NodeDecl} l'id " + idName + " è già presente nella symbol table.");
		} else {
			Attributes att;
			if(type.equals(LangType.INT))
				att = new Attributes(TypeDescriptor.INT);
			else if(type.equals(LangType.FLOAT))
				att = new Attributes(TypeDescriptor.FLOAT);
			else
				att  = new Attributes(TypeDescriptor.ERROR);
			SymbolTable.enter(idName, att);
			id.setTypeDescriptor(att.getTipo());
			id.setDefinition(att);
		}
	}

	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
		node.setTypeDescriptor(node.getId().getTypeDescriptor());
	}

	@Override
	public void visit(NodeId node) {
		String name = node.getName();
		if(SymbolTable.lookup(name) == null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			logger.addTypeCheckingError("{NodeId} la variabile + " + name +" non è dichiarata.");
			node.setDefinition(new Attributes(TypeDescriptor.ERROR));
		} else {
			Attributes att = SymbolTable.lookup(name);
			node.setDefinition(att);
			node.setTypeDescriptor(att.getTipo());
		}
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		TypeDescriptor typeDescriptor = node.getId().getTypeDescriptor();
		node.setTypeDescriptor(typeDescriptor);
	}
	
	
	private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
		return (!t1.equals(TypeDescriptor.ERROR) && !t2.equals(TypeDescriptor.ERROR) && t1.equals(t2)) || (t1.equals(TypeDescriptor.FLOAT) && t2.equals(TypeDescriptor.INT));
	}
	
	private NodeExpr convertExpr(NodeExpr node) {
		if(node.getTypeDescriptor() == TypeDescriptor.FLOAT) {
			return node;
		} else {
			NodeConv n = new NodeConv(node);
			n.setTypeDescriptor(TypeDescriptor.FLOAT);
			return n;
		}
	}
	
	public String toString() {
		return logger.getTypeCheckingLogString();
	}
}
