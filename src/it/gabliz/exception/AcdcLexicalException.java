package it.gabliz.exception;

import it.gabliz.util.Logger;

/**
 * Exception lanciata per errori inerenti al lessico.
 * @author Gabliz
 */
public class AcdcLexicalException extends Exception {
    public AcdcLexicalException (String errore) {
        super(errore + "\n");
        Logger.e("", "Rilevata AcdcLexicalException ("+ errore +").");
    }
}
