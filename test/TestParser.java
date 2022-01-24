import it.gabliz.parser.Parser;
import it.gabliz.scanner.Scanner;
import it.gabliz.util.AcdcLexicalException;
import it.gabliz.util.AcdcSyntaxException;
import it.gabliz.util.ScannerException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestParser {
    private Parser parser;
    private Scanner scanner;

    @Test
    public void TestMine1() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/testMine1.txt";
        String s = "<ID: a, Type: INT><ID: b, Type: FLOAT>";
        scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        assertEquals(s, parser.parse().toString());
    }

    @Test
    public void TestFileParserCorrect2() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/fileParserCorrect2.txt";
        scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        assertThrows(ScannerException.class, () -> {parser.parse();});
    }

    @Test
    public void TestFileParserCorrect3() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/fileParserCorrect3.txt";
        scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        parser.parse();
    }

    @Test
    public void TestFileParserWrong1() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/fileParserWrong1.txt";
        scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        assertThrows(AcdcSyntaxException.class, () -> {parser.parse();});
    }

    @Test
    public void TestFileParserWrong2() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/fileParserWrong2.txt";
        scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        assertThrows(AcdcSyntaxException.class, () -> {parser.parse();});
    }

    @Test
    public void TestFileScannerCorrect() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/fileScannerCorrect1.txt";
        scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        assertThrows(AcdcSyntaxException.class, () -> {parser.parse();});
    }

}
