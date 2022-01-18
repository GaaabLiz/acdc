package it.gabliz.token;

import it.gabliz.util.Logger;

import java.util.Objects;

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

	/**
	 * Costruttore con campo valore.
	 * @param tipo il tipo di token
	 * @param riga la riga su cui si trovava il token
	 * @param val il valore associato
	 */
	public Token(TokenType tipo, int riga, String val) {
		this.riga = riga;
		this.tipo = tipo;
		this.val = val;
		Logger.i(CLASS_NAME, "Creato nuovo token: \"" + this.toString() + "\".");
	}

	/**
	 * Costruttore senza campo valore.
	 * @param tipo il tipo di token
	 * @param riga la riga su cui si trovava il token
	 */
	public Token(TokenType tipo, int riga) {
		this.riga = riga;
		this.tipo = tipo;
		this.val = EMPTY_VAL;
		Logger.i(CLASS_NAME, "Creato nuovo token: \"" + this.toString() + "\".");
	}

	/**
	 * @return String Una stringa che rappresenta questo token con valore
	 */
	private String getStringWithVal() {return "<" + tipo + "," + "r:" + riga + "," + val + ">";}

	/**
	 * @return String Una stringa che rappresenta questo token senza valore
	 */
	private String getStringWithoutVal() {return "<" + tipo + "," + "r:" + riga + ">";}

	public String toString() {
		if(!Objects.equals(this.val, EMPTY_VAL)) {
			return getStringWithVal();
		} else {
			return getStringWithoutVal();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass()) {
			Logger.e(CLASS_NAME, "Tentativo di equals con due oggetti di classi differenti o non compatibili.");
			Logger.e(CLASS_NAME, "I tipi in questione sono : " + getClass() + " e " + obj.getClass());
			return false;
		}
		Boolean cond1 = this.tipo == ((Token)obj).getTipo();
		Boolean cond2 = Objects.equals(this.val, ((Token) obj).getVal());
		return cond1 && cond2;
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
