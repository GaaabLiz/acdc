package it.gabliz.symboltable;

import it.gabliz.ast.TypeDescriptor;

/**
 * Classe che rappresenta l'istanza 'attributo' per la symbol table
 * @see SymbolTable
 * @author Gabliz
 */
public class Attributes {

    private TypeDescriptor tipo;
    private char register;

    public Attributes(TypeDescriptor tipo) {
        this.tipo = tipo;
    }

    public TypeDescriptor getTipo() {
        return tipo;
    }

    public void setTipo(TypeDescriptor tipo) {
        this.tipo = tipo;
    }

    public char getRegister() {
        return register;
    }

    public void setRegister(char register) {
        this.register = register;
    }
}
