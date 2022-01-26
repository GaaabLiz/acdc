package it.gabliz.exception;

import it.gabliz.util.Logger;

public class TokenConstructorException extends Exception{
    public TokenConstructorException (String errore) {
        super(errore  + "\n");
        Logger.e("", "Rilevata TokenConstructorException ("+ errore +").");
    }
}
