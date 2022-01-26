package it.gabliz.exception;

import it.gabliz.util.Logger;

public class ScannerException extends Exception {
    public ScannerException (String errore) {
        super(errore  + "\n");
        Logger.e("", "Rilevata ScannerException ("+ errore +").");
    }
}
