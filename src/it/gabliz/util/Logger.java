package it.gabliz.util;

/**
 * Classe utilizzata per stampare log nella console.
 * @author Gabliz
 */
public class Logger {

    private final String sectionLogName;
    private static final String DEBUG_PREFIX = "[DEBUG]";
    private static final String ERROR_PREFIX = "[ERROR]";
    private static final String INFO_PREFIX = "[INFO]";
    private static final Boolean ENABLE_LOGGIN = true;

    /**
     * Costruttore della classe.
     * @param sectionLogName Il nome della sezione/classe a cui i successivi log si riferiranno.
     */
    public Logger(String sectionLogName) {
        this.sectionLogName = sectionLogName;
    }

    /**
     * Stampa log di DEBUG.
     * @param logText testo del log.
     */
    public void d(String logText) {
        if(ENABLE_LOGGIN) System.out.println(DEBUG_PREFIX + " (" + sectionLogName + "): " + logText);
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
        if(ENABLE_LOGGIN) System.out.println(INFO_PREFIX + " (" + sectionLogName + "): " + logText);
    }

    /**
     * Stampa log di ERRORE.
     * @param logText testo del log.
     */
    public void e(String logText) {
        if(ENABLE_LOGGIN) System.err.println(ERROR_PREFIX + " (" + sectionLogName + "): " + logText);
    }

    /**
     * Stampa log di ERRORE.
     * @param logText testo del log.
     */
    public static void e(String sectionLogName, String logText) {
        if(ENABLE_LOGGIN) System.err.println(ERROR_PREFIX + " (" + sectionLogName + "): " + logText);
    }
}
