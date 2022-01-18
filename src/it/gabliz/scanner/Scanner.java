package it.gabliz.scanner;

import it.gabliz.token.Token;
import it.gabliz.token.TokenType;
import it.gabliz.util.AcdcLexicalException;
import it.gabliz.util.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che gestisce lo scanner del nostro progetto.
 * @author Gabliz
 * @see Token Questa classe rappresenta il singolo oggetto TOKEN che questo scanner usa.
 */
public class Scanner {

	/** Carattere di terminazione file */
	private static final char EOF = (char) -1;

	/** Indicazione della riga su cui lo scanner è attualmente */
	private int riga;

	/** Oggetto per gestire il buffer di caratteri da leggere */
	private PushbackReader buffer;

	@Deprecated
	private String log;

	/** Lista dei caratteri da saltare */
	private List<Character> skipChars = Arrays.asList(' ', '\n', '\t', '\r', EOF);

	/** Lista dei caratteri che rappresentano lettere */
	private List<Character> letters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

	/** Lista dei caratteri che rappresentano numeri */
	private List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

	/** Hashmap per associare i tipi di token e i relativi caratteri char
	 * @see it.gabliz.token.TokenType */
	private HashMap<Character, TokenType> symbols;

	/** Hashmap per associare i tipi di token e parole chiave del linguaggio.
	 * @see it.gabliz.token.TokenType per i tipi di token */
	private HashMap<String, TokenType> keywords;

	/** Il token attualmente in analisi dallo scanner */
	private Token currentToken;

	/** Classe per gestire i log di questa classe.
	 * @see Logger per gestione log. */
	private Logger logger;

	/** Dichiarazione path di base di tutti i file che leggerà questo scanner */
	private static final String PATH_ROOT = "res/";

	/** Numero minimo di cifre di un numero float */
	private static final int FLOAT_MIN_CIFRE = 1;

	/** Numero massimo di cifre di un numero float */
	private static final int FLOAT_MAX_CIFRE = 4;


	/**
	 * Costruttore di uno scanner.
	 * @param fileName il nome del file da passare allo scanner.
	 * @throws FileNotFoundException Se il file non viene trovato.
	 * @throws UnsupportedOperationException Se il file non può essere letto.
	 */
	public Scanner(String fileName) throws FileNotFoundException, UnsupportedOperationException {

		/* stampo informazioni */
		logger = new Logger(this.getClass().getSimpleName());
		logger.i("Creata nuova istanza di uno scanner per il file \"" + fileName + "\".");

		/* controllo il file */
		File f = new File(PATH_ROOT + fileName);
		if(!f.exists()) throw new FileNotFoundException("Il file \"" + fileName + "\" non è stato trovato in \"" + PATH_ROOT + "\".");
		if(!f.canRead()) throw new UnsupportedOperationException("Il file \"" + fileName + "\" non può essere letto.");

		/* preparazione scanner */
		try {
			this.buffer = new PushbackReader(new FileReader(f));
		}catch (FileNotFoundException e) {
			logger.e("Rilevato errore apertura file per lo scanner. Controllare se path passato è directory.");
			e.printStackTrace();
		}
		logger.i("Il file \"" + fileName + "\" è stato trovato e caricato nello scanner.");
		riga = 1;
		loadSymbolsHashMap();
		loadKeywordsHashMap();
		this.currentToken = null;

	}


	/**
	 * Questo metodo serve per recuperare il prossimo token dall'input MA SENZA CONSUMARLO.
	 * Quindi una successiva chiamata a questo metodo alla funzione nextToken ritorna ancora
	 * il token attuale.
	 *
	 * @return Il token corrente. Il ritorno di questa funzione dipende dal token attuale:
	 * - Se currentToken != null -> la nextToken ha già assegnato il token e lo ritorno.
	 * - se currentToken == null -> chiamo la nextToken che assegnerà il nuovo token e lo ritorno.
	 * @throws IOException
	 * @throws AcdcLexicalException
	 */
	public Token peekToken() throws IOException, AcdcLexicalException {
		logger.d("Peektoken chiamata con currentToken = \"" + currentToken + "\".");
		if(currentToken == null) {
			currentToken = nextToken();
		}
		return currentToken;
	}

	/**
	 * Metodo per recuperare il prossimo token dall'input.
	 * Questo è effettivamente il metodo che può consumare l'input.
	 * Questo metodo può essere visto come una rappresentazione dell'automa riconoscitore.
	 * @return il token recuperato.
	 * @throws IOException Per eventuali errori di lettura nel buffer.
	 * @throws AcdcLexicalException
	 */
	public Token nextToken() throws IOException, AcdcLexicalException {

		/* stampo log */
		logger.d("nextToken chiamata con currentToken = \"" + currentToken + "\".");

		/* Controllo token:
		* - se è già settato (è != null) vuol dire che una precedente chiamata di nextToken lo ha settato.
		*	Ora lo consumo (lo setto a null) ritornandolo.
		* - Se non è settato (è == null) vuol dire che è stato consumato precedentemente l'input o questa è la prima
		* 	chiamata di nextToken(). Essendo null devo prelevare il prossimo token.
		*  */
		if(currentToken != null) {
			Token token = currentToken;
			currentToken = null;
			return token;
		}

		/* stampo log */
		logger.i("Inizio procedura calcolo nuovo token da input.");

		/* prendo il prossimo carattere dall'input */
		int nextCharInt = buffer.read();
		char nextChar = (char) nextCharInt;
		logger.d("Ho letto dal buffer (consumando) il carattere '" + nextChar + "'.");

		/* inizio ciclo per controllo caratteri per creare token */
		while(nextChar != EOF && nextCharInt != -1) {

			if(nextChar == Token.CHAR_NEW_LINE) {
				riga++;
				logger.d("Trovato carattere di terminazione riga. Proseguo con riga " + riga + ".");
			} else if(!skipChars.contains(nextChar)) {
				if(numbers.contains(nextChar)) {
					logger.d("Trovato carattere di tipo numerico. Rimetto il carattere nel buffer e procedo controllo numero.");
					buffer.unread(nextChar);
					return scanAndGetNumberToken();
				}
				if(letters.contains(nextChar)) {
					logger.d("Trovato carattere di tipo letterale. Rimetto il carattere nel buffer e procedo controllo lettere.");
					buffer.unread(nextChar);
					return scanAndGetIdToken();
				}
				if(symbols.containsKey(nextChar)) {
					return new Token(symbols.get(nextChar), riga);
				}
				throw new AcdcLexicalException("Rilevato errore lessicale dello scanner.");
			}

			/* Se arrivati qua è perchè ho trovato un carattere di skip. Leggo prossimo carattere. */
			char oldChar = nextChar;
			nextCharInt = buffer.read();
			nextChar = (char) nextCharInt;
			logger.d("Trovato carattere di skip ('" + oldChar +"'). Prossimo carattere in lettura = '" + nextChar + "'");
		}

		/* se arrivati a questo punto non ho più altri token da generare e sono arrivato a fine file */
		return new Token(TokenType.EOF, riga);

	}

	/**
	 * Se viene chiamato questo metodo è stato trovato carattere numerico. Il compito di questo
	 * metodo è quello di generare il token a partire da questo (e potenzialmente anche dai successivi) carattere/i
	 * numerici (e decidere se numero INT O FLOAT).
	 * @return il token numero di tipo INT o FLOAT associato al numero/numeri in input nel buffer.
	 * @throws IOException se si sono verificati errori di lettura del buffer.
	 * @throws AcdcLexicalException Se vengono trovate più di quattro cifre dopo la virgola.
	 */
	private Token scanAndGetNumberToken() throws IOException, AcdcLexicalException {

		char c;
		int i = 0;
		StringBuilder number = new StringBuilder();

		while(numbers.contains(peekChar())) {
			c = readChar();
			number.append(c);
		}

		c = peekChar();
		if(c == Token.CHAR_DOT) {
			c = readChar();
			number.append(c);
			if(!numbers.contains(peekChar())) {
				buffer.unread(c);
				return new Token(TokenType.INT, riga, number.toString());
			} else {
				while(numbers.contains(peekChar())) {
					c = readChar();
					number.append(c);
					i++;
				}
				if (i >= FLOAT_MIN_CIFRE && i <= FLOAT_MAX_CIFRE) {
					return new Token(TokenType.FLOAT, riga, number.toString());
				} else  {
					throw new AcdcLexicalException("Trovato un numero float con più di " + FLOAT_MAX_CIFRE + " cifre alla riga: "+ riga + ".");
				}
			}
		}
		else return new Token(TokenType.INT, riga, number.toString());
	}

	/**
	 * Se viene chiamato questo metodo è stato trovato carattere alfabetico. Il compito di questo
	 * metodo è quello di generare il token a partire da questo (e potenzialmente anche dai successivi) carattere/i
	 * alfabetici (e decidere se è parola chiave o no).
	 * @return il token associato agli input del buffer.
	 * @throws IOException se si sono verificati errori di lettura del buffer.
	 */
	private Token scanAndGetIdToken() throws IOException {
		char ch;
		StringBuilder word = new StringBuilder();
		while (letters.contains(peekChar())) {
			ch = readChar();
			word.append(ch);
		}
		if (keywords.containsKey(word.toString())) {
			return new Token(keywords.get(word.toString()), riga);
		} else return new Token(TokenType.ID, riga, word.toString());
	}

	/**
	 * Prendo un singolo carattere dal buffer (int).
	 * @return Il carattere preso.
	 * @throws IOException Per eventuali errori di lettura dal buffer.
	 */
	@Deprecated
	private int simpleReadChar() throws IOException {
		int c = buffer.read();
		logger.d("Ho prelevato dal buffer (read) il carattere '" + (char) c + "'.");
		return c;
	}

	/**
	 * Prendo un singolo carattere dal buffer (char).
	 * @return Il carattere preso.
	 * @throws IOException Per eventuali errori di lettura dal buffer.
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 * Prendo un singolo carattere dal buffer senza consumarlo.
	 * @return Il carattere preso.
	 * @throws IOException Per eventuali errori di lettura dal buffer.
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}

	/** Metodo per caricare la hashmap dei simboli */
	private void loadSymbolsHashMap() {
		symbols = new HashMap<>();
		symbols.put('+', TokenType.PLUS);
		symbols.put('-', TokenType.MINUS);
		symbols.put('*', TokenType.TIMES);
		symbols.put('/', TokenType.DIV);
		symbols.put('=', TokenType.ASSIGN);
		symbols.put(';', TokenType.SEMI);
	}

	/** Metodo per caricare la hashmap dei simboli */
	private void loadKeywordsHashMap() {
		keywords = new HashMap<>();
		keywords.put("int", TokenType.TYINT);
		keywords.put("float", TokenType.TYFLOAT);
		keywords.put("print", TokenType.PRINT);
	}
}
