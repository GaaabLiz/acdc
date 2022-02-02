import it.gabliz.ast.NodeProgram;
import it.gabliz.exception.AcdcSyntaxException;
import it.gabliz.exception.ScannerException;
import it.gabliz.parser.Parser;
import it.gabliz.scanner.Scanner;
import it.gabliz.visitor.CodeGeneratorVisitor;
import it.gabliz.visitor.IVisitor;
import it.gabliz.visitor.TypeCheckingVisitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TestVisitor {

    private IVisitor visitor;
    private IVisitor typeCheckingVisitor;

    @BeforeEach
    public void setUp() {
        typeCheckingVisitor = new TypeCheckingVisitor();
        visitor = new CodeGeneratorVisitor();
    }

    @Test
    public void testTypeCheckingVisitor() throws FileNotFoundException, ScannerException, AcdcSyntaxException {
        Parser parser = new Parser(new Scanner("visitor/code1.txt"));
        NodeProgram astree  = parser.parse();
        astree.accept(typeCheckingVisitor);
        astree.accept(visitor);
        System.out.println(typeCheckingVisitor.toString());
        System.out.println(visitor.toString());
        assertEquals("1.0 6 5 k / sb 0 k lb p P 1 6 / sa 0 k la p P",(visitor.toString()));
    }

}