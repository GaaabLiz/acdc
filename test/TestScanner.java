import it.gabliz.scanner.Scanner;
import it.gabliz.token.Token;
import it.gabliz.token.TokenType;
import it.gabliz.util.AcdcLexicalException;
import it.gabliz.util.TokenConstructorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class TestScanner {
	
	@Test
	public void testScanId() throws IOException, AcdcLexicalException, TokenConstructorException {
		String fileName = "scanner/testId.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.ID, 1, "jskjdsfhkjdshkf").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 4, "ccc").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 6, "hhhjj").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.EOF, 7).toString(), scanner.nextToken().toString());
	}

	@Test
	public void testScanId2() throws IOException, AcdcLexicalException, TokenConstructorException {
		String fileName = "scanner/testScanId.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.TYINT, 1).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.TYFLOAT, 2).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.PRINT, 3).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 4, "nome").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 5, "intnome").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.TYINT, 6).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 6, "nome").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.EOF, 6).toString(), scanner.nextToken().toString());
	}

	@Test
	public void testScanNum() throws IOException, AcdcLexicalException, TokenConstructorException {
		String fileName = "scanner/testScanNum.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.INT, 1, "123").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.FLOAT, 2, "123.456").toString(), scanner.nextToken().toString());
		assertThrows(AcdcLexicalException.class, scanner::nextToken);
		assertEquals(new Token(TokenType.EOF, 3).toString(), scanner.nextToken().toString());
	}

	@Test
	public void testScanner() throws IOException, AcdcLexicalException, TokenConstructorException {
		String fileName = "scanner/testScanner.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.TYINT, 1).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 1, "a").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.SEMI, 1).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 2, "a").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ASSIGN, 2).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.INT, 2, "5").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.SEMI, 2).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.TYFLOAT, 3).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 3, "b").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.SEMI, 3).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 4, "b").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ASSIGN, 4).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 4, "a").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.PLUS, 4).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.FLOAT, 4, "3.2").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.SEMI, 4).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.PRINT, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 5, "b").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.SEMI, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.EOF, 5).toString(), scanner.nextToken().toString());
	}

	@Test
	public void testOP() throws IOException, AcdcLexicalException, TokenConstructorException {
		String fileName = "scanner/TestOP.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.PLUS, 1).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.MINUS, 2).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.TIMES, 3).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.DIV, 4).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.PLUS, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.MINUS, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.TIMES, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.DIV, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.SEMI, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ASSIGN, 5).toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.EOF, 5).toString(), scanner.nextToken().toString());
	}

	@Test
	public void testEOF() throws IOException, AcdcLexicalException, TokenConstructorException {
		String fileName = "scanner/testEOF.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.EOF, 1).toString(), scanner.nextToken().toString());
	}

	@Test
	public void testEcc() throws IOException, AcdcLexicalException , TokenConstructorException {
		String fileName = "scanner/testEcc.txt";
		Scanner scanner = new Scanner(fileName);
		assertEquals(new Token(TokenType.ID, 2, "ciao").toString(), scanner.nextToken().toString());
		assertThrows(AcdcLexicalException.class, scanner::nextToken);
		assertThrows(AcdcLexicalException.class, scanner::nextToken);
		assertThrows(AcdcLexicalException.class, scanner::nextToken);
		assertThrows(AcdcLexicalException.class, scanner::nextToken);
		assertEquals(new Token(TokenType.INT, 5, "123").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.INT, 6, "123").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.ID, 6, "a").toString(), scanner.nextToken().toString());
		assertThrows(AcdcLexicalException.class, scanner::nextToken);
		assertEquals(new Token(TokenType.ID, 7, "a").toString(), scanner.nextToken().toString());
		assertEquals(new Token(TokenType.EOF, 7).toString(), scanner.nextToken().toString());
	}

}