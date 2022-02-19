package it.gabliz.token;

import it.gabliz.util.Logger;
import it.gabliz.exception.TokenConstructorException;

import java.util.*;

/**
 * Classe che rappresenta la singola istanza 'TOKEN'.
 * @author Gabliz
 */
public class Token {

	/** Indica la riga su cui si trovava il token */
	private int riga;

	/** Indica il tipo di token */
	private TokenType tipo;

	/** Indica il valore associato (se associato) */
	private String val;

	/** Costante che indica che al token non è associato nessun valore. */
	public static final String EMPTY_VAL = "";

	public static final Character CHAR_NEW_LINE = '\n';
	public static final Character CHAR_DOT = '.';
	private final String CLASS_NAME = this.getClass().getSimpleName().toUpperCase();
	private static final Set<TokenType> tokenTypesWithValues = Set.of(TokenType.ID, TokenType.INT, TokenType.FLOAT);

	/**
	 * Costruttore con campo valore.
	 * @param tipo il tipo di token
	 * @param riga la riga su cui si trovava il token
	 * @param val il valore associato
	 * @throws TokenConstructorException se parametri token errati.
	 */
	public Token(TokenType tipo, int riga, String val) throws TokenConstructorException {
		if(tipo == null) throw new TokenConstructorException("Il tipo di un token non può essere nullo.");
		if(riga <=0 ) throw new TokenConstructorException("La riga non può essere 0 o negativa.");
		this.riga = riga;
		this.tipo = tipo;
		this.val = val;
		checkTokenConstructorWithVal();
	}

	/**
	 * Costruttore senza campo valore.
	 * @param tipo il tipo di token
	 * @param riga la riga su cui si trovava il token
	 * @throws TokenConstructorException se parametri token errati.
	 */
	public Token(TokenType tipo, int riga) throws TokenConstructorException {
		if(tipo == null) throw new TokenConstructorException("Il tipo di un token non può essere nullo.");
		if(riga <=0 ) throw new TokenConstructorException("La riga non può essere 0 o negativa.");
		this.riga = riga;
		this.tipo = tipo;
		this.val = EMPTY_VAL;
		checkTokenConstructorWithoutVal();
	}

	/**
	 * @return String Una stringa che rappresenta questo token con valore
	 */
	private String getStringWithVal() {return "<" + tipo + "," + "r:" + riga + "," + val + ">";}

	/**
	 * @return String Una stringa che rappresenta questo token senza valore
	 */
	private String getStringWithoutVal() {return "<" + tipo + "," + "r:" + riga + ">";}

	/**
	 * Metodo per dare log warning in caso di costruttore errato.
	 */
	private void checkTokenConstructorWithVal() {
		if(!tokenTypesWithValues.contains(this.tipo))
			Logger.w(CLASS_NAME, "Il costruttore per il token " + this.tipo + " è errato.");
	}

	/**
	 * Metodo per dare log warning in caso di costruttore errato.
	 */
	private void checkTokenConstructorWithoutVal() {
		if(tokenTypesWithValues.contains(this.tipo))
			Logger.w(CLASS_NAME, "Il costruttore per il token " + this.tipo + " è errato (controllare presenza/assenza valore).");
	}

	/**
	 * Metodo per fare stampare un log con il tipo di token creato
	 * @return il token creato.
	 */
	public Token logCreation() {
		Logger.i(CLASS_NAME, "Creato nuovo token: \"" + this + "\".");
		return this;
	}

	public String toString() {
		if(!Objects.equals(this.val, EMPTY_VAL)) {
			return getStringWithVal();
		} else {
			return getStringWithoutVal();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if (!(obj instanceof Token)) {
			Logger.e(CLASS_NAME, "Tentativo di equals con due oggetti di classi differenti o non compatibili.");
			Logger.e(CLASS_NAME, "I tipi in questione sono : " + getClass() + " e " + obj.getClass());
			return false;
		}
		Boolean cond1 = this.tipo == ((Token)obj).getTipo();
		Boolean cond2 = Objects.equals(this.val, ((Token) obj).getVal());
		Boolean cond3 = Objects.equals(this.riga, ((Token) obj).getRiga());
		return cond1 && cond2 && cond3;
	}

	public int getRiga() {
		return riga;
	}

	public TokenType getTipo() {
		return tipo;
	}

	public String getVal() {
		return val;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public void setTipo(TokenType tipo) {
		this.tipo = tipo;
	}

	public void setVal(String val) {
		this.val = val;
	}
}
