package it.gabliz.exception;

import it.gabliz.token.Token;
import it.gabliz.util.Logger;

/**
 * Exception lanciata per errori inerenti alla sintassi.
 * @author Gabliz
 */
public class AcdcSyntaxException extends Exception {
    private Token tk;
    private int riga;
    public static final String SYNTAX_ERROR_TEMPLATE = "Errore di sintassi relativo al token % alla riga #.";

    public AcdcSyntaxException (String errore) {
        super(errore  + "\n");
        Logger.e("", "Rilevata AcdcSyntaxException ("+ errore +").");
    }

    public AcdcSyntaxException (String errore, Token tk, Integer riga) {
        super(errore.replace("%", tk.getTipo().toString().replace("#", riga.toString()))  + "\n");
        Logger.e("", "Rilevata AcdcSyntaxException (errore di sintassi token"+ tk +").");
    }


}
