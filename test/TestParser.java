import it.gabliz.parser.Parser;
import it.gabliz.scanner.Scanner;
import it.gabliz.util.AcdcSyntaxException;
import it.gabliz.util.ScannerException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestParser {
    private Parser parser;
    private Scanner scanner;

    @Test
    public void TestMine1() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        String fileName = "parser/testMine1.txt";
        String s = "<ID: a, Type: INT><ID: b, Type: FLOAT>";
        Scanner scanner = new Scanner(fileName);
        parser = new Parser(scanner);
        assertEquals(s, parser.parse().toString());
    }

}
