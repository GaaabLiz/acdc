package it.gabliz.util;

/**
 * Exception lanciata per errori inerenti alla sintassi.
 * @author Gabliz
 */
public class AcdcSyntaxException extends Exception {
    public AcdcSyntaxException (String errore) {
        super(errore  + "\n");
        Logger.e("", "Rilevata AcdcSyntaxException ("+ errore +").");
    }
}
