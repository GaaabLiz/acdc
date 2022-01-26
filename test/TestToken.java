import it.gabliz.token.Token;
import it.gabliz.token.TokenType;
import it.gabliz.exception.TokenConstructorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestToken {

	@Test
	void testConstructorExceptions() {
		assertThrows(TokenConstructorException.class, () -> new Token(null, 1));
		assertThrows(TokenConstructorException.class, () -> new Token(TokenType.TIMES, 0));
	}

	@Test
	void testToString() throws TokenConstructorException {
		assertEquals("<EOF,r:5>", new Token(TokenType.EOF, 5).toString());
		assertEquals("<PLUS,r:4>", new Token(TokenType.PLUS, 4).toString());
		assertEquals("<ASSIGN,r:2>", new Token(TokenType.ASSIGN, 2).toString());
		assertEquals("<ID,r:4,b>", new Token(TokenType.ID, 4, "b").toString());
		assertEquals("<ID,r:5,a>", new Token(TokenType.ID, 5, "a").toString());
		assertEquals("<ID,r:4,a>", new Token(TokenType.ID, 4, "a").toString());
		assertEquals("<ID,r:2,a>", new Token(TokenType.ID, 2, "a").toString());
		assertEquals("<PRINT,r:5>", new Token(TokenType.PRINT, 5).toString());
		assertEquals("<ID,r:3,b>", new Token(TokenType.ID, 3, "b").toString());
		assertEquals("<SEMI,r:3>", new Token(TokenType.SEMI, 3).toString());
		assertEquals("<ID,r:1,a>", new Token(TokenType.ID, 1, "a").toString());
		assertEquals("<SEMI,r:4>", new Token(TokenType.SEMI, 4).toString());
		assertEquals("<TYFLOAT,r:3>", new Token(TokenType.TYFLOAT, 3).toString());
		assertEquals("<SEMI,r:2>", new Token(TokenType.SEMI, 2).toString());
		assertEquals("<FLOAT,r:4,3.2>", new Token(TokenType.FLOAT, 4, "3.2").toString());
		assertEquals("<TYINT,r:1>", new Token(TokenType.TYINT, 1).toString());
		assertEquals("<SEMI,r:5>", new Token(TokenType.SEMI, 5).toString());
		assertEquals("<ASSIGN,r:4>", new Token(TokenType.ASSIGN, 4).toString());
		assertEquals("<SEMI,r:1>", new Token(TokenType.SEMI, 1).toString());
		assertEquals("<INT,r:2,5>", new Token(TokenType.INT, 2, "5").toString());
	}

}
