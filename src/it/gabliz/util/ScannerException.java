package it.gabliz.util;

public class ScannerException extends Exception {
    public ScannerException (String errore) {
        super(errore  + "\n");
        Logger.e("", "Rilevata ScannerException ("+ errore +").");
    }
}
