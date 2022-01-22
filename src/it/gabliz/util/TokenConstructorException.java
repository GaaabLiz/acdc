package it.gabliz.util;

public class TokenConstructorException extends Exception{
    public TokenConstructorException (String errore) {
        super(errore  + "\n");
        Logger.e("", "Rilevata TokenConstructorException ("+ errore +").");
    }
}
