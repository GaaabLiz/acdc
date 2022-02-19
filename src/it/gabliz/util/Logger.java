package it.gabliz.util;

import it.gabliz.token.Token;

/**
 * Classe utilizzata per stampare log nella console.
 * @author Gabliz
 */
public class Logger {

    private static final Boolean ENABLE_LOGGIN = true;
    private static final Boolean ENABLE_DEBUG = true;

    private final String sectionLogName;
    private static final String DEBUG_PREFIX = "[DEBUG]";
    private static final String ERROR_PREFIX = "[ERROR]";
    private static final String WARN_PREFIX = "[WARNING]";
    private static final String INFO_PREFIX = "[INFO]";
    private final StringBuilder typeCheckingErrors;
    private Integer typeCheckingErrorsCounter = 0;

    /**
     * Costruttore della classe.
     * @param sectionLogName Il nome della sezione/classe a cui i successivi log si riferiranno.
     */
    public Logger(String sectionLogName) {
        this.sectionLogName = sectionLogName.toUpperCase();
        this.typeCheckingErrors = new StringBuilder();
        typeCheckingErrors.append("--------------------\n");
        typeCheckingErrors.append("TYPE CHECKING ERRORS\n");
    }

    /**
     * Stampa log di DEBUG.
     * @param logText testo del log.
     */
    public void d(String logText) {
        if(ENABLE_LOGGIN && ENABLE_DEBUG) System.out.println(DEBUG_PREFIX + " (" + sectionLogName + "): " + logText);
        catchNewLine(logText);
    }

    /**
     * Stampa log di INFO.
     * @param logText testo del log.
     */
    public void i(String logText) {
        if(ENABLE_LOGGIN) System.out.println(INFO_PREFIX + " (" + sectionLogName + "): " + logText);
    }

    /**
     * Stampa log di INFO.
     * @param logText testo del log.
     */
    public static void i(String sectionLogName, String logText) {
        if(ENABLE_LOGGIN) System.out.println(INFO_PREFIX + " (" + sectionLogName.toUpperCase() + "): " + logText);
    }

    /**
     * Stampa log di ERRORE.
     * @param logText testo del log.
     */
    public void e(String logText) {
        if(ENABLE_LOGGIN) System.out.println(ERROR_PREFIX + " (" + sectionLogName + "): " + logText);
    }

    /**
     * Stampa log di ERRORE.
     * @param logText testo del log.
     */
    public static void e(String sectionLogName, String logText) {
        if(ENABLE_LOGGIN) System.out.println(ERROR_PREFIX + " (" + sectionLogName.toUpperCase() + "): " + logText);
    }

    /**
     * Stampa log di WARNING.
     * @param logText testo del log.
     */
    public static void w(String sectionLogName, String logText) {
        if(ENABLE_LOGGIN) System.out.println(WARN_PREFIX + " (" + sectionLogName.toUpperCase() + "): " + logText);
    }

    /**
     * Metodo per formattare correttamente il new line nei log
     * @param c il carattere da controllare.
     * @return se c è new line restituisce stringa formattata, altrimenti restituisce c.
     */
    public static Object formatLogNewLine(char c) {
        if(c == Token.CHAR_NEW_LINE) return "NEW LINE";
        return c;
    }

    public static void catchNewLine(String logToCheck) {
        if(logToCheck.contains("\n") || logToCheck.contains("\\n")) {
            System.out.print("\n\n");
            Logger.w("LOGGER", "Trovato new line.");
        }
    }

    public void addTypeCheckingError(String errorMessage) {
        typeCheckingErrors.append("- ").append(errorMessage).append("\n");
        typeCheckingErrorsCounter++;
        this.e(errorMessage);
    }

    public String getTypeCheckingLogString() {
        if(typeCheckingErrorsCounter == 0)
            typeCheckingErrors.append("none\n").append("--------------------\n");
        return typeCheckingErrors.toString();
    }

}
