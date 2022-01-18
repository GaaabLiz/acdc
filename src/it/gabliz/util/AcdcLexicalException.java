package it.gabliz.util;

/**
 * Exception lanciata per errori inerenti al lessico.
 * @author Gabliz
 */
public class AcdcLexicalException extends Exception {
    public AcdcLexicalException (String errore) {
        super(errore + "\n");
    }
}
