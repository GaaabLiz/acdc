package it.gabliz.parser;

import it.gabliz.ast.*;
import it.gabliz.exception.AcdcLexicalException;
import it.gabliz.exception.AcdcSyntaxException;
import it.gabliz.exception.ScannerException;
import it.gabliz.exception.TokenConstructorException;
import it.gabliz.scanner.Scanner;
import it.gabliz.token.Token;
import it.gabliz.token.TokenType;
import it.gabliz.util.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe che implementa il parser.
 * @author Gabliz
 */
public class Parser {

    /** Variabile scanner fase precedente */
    private Scanner scanner;

    /** Lista dei nodi Dec/St */
    private ArrayList<NodeDecSt> arrayNode;

    /** Classe per gestire i log di questa classe.
     * @see Logger per gestione log. */
    private final Logger logger;

    /**
     * Costruttore di un parser.
     * @param scanner l'oggetto scanner per chiamare {@link Scanner#peekToken()}
     * @throws IllegalArgumentException se parametri costruttore nulli
     */
    public Parser(Scanner scanner) throws IllegalArgumentException {
        if(scanner == null) throw new IllegalArgumentException("L'oggetto scanner non può essere nullo!");
        this.scanner = scanner;
        this.arrayNode = new ArrayList<>();
        logger = new Logger(this.getClass().getSimpleName());
        logger.i("Inizio fase di parsing per il file corrente.");
    }

    /**
     * Questo metodo serve per vedere nel futuro e controllare se il prossimo token è un token corretto
     * (ha un certo tipo). Se ha il tipo che mi aspettavo lo consumo, altrimenti restituisco un errore.
     * @param type il tipo di token che mi aspetto.
     * @throws AcdcSyntaxException se viene rilevato un errore di sintassi.
     */
    private Token match(TokenType type) throws AcdcSyntaxException, TokenConstructorException, IOException, AcdcLexicalException {
        /* TODO : togliere valore di ritorno */
        Token tk = scanner.peekToken();
        if (type.equals(tk.getTipo())) {
            scanner.nextToken();
            return tk;
        }
        else throw new AcdcSyntaxException("Errore di sintassi rilevato durante il match. Il parser si aspettava " +
                "un token di tipo " + type + " ma ha trovato un token di tipo " + tk.getTipo() + "(Riga"
                + tk.getRiga() + ").");
    }

    /**
     * Metodo pubblico che si occupa di fare tutte le operazioni di parsing.
     * @return Il nodo programma che contiene l'AST.
     * @throws ScannerException Se viene rilevato un errore dello scanner.
     * @throws AcdcSyntaxException Se viene rilevato un errore di sintassi.
     */
    public NodeProgram parse() throws ScannerException, AcdcSyntaxException {
        try {
            NodeProgram nodeProgram = parsePrg();
            logger.i("Il parser ha restituito : " + nodeProgram);
            return nodeProgram;
        }catch (IOException | TokenConstructorException | AcdcLexicalException e) {
            throw new ScannerException("Durante l'esecuzione del parser è stata rilevata un exception " +
                    "relativa allo scanner: \"" + e.getMessage() + "\".");
        }
    }

    /** Parsing della produzione Prg */
    private NodeProgram parsePrg() throws AcdcSyntaxException, TokenConstructorException, IOException, AcdcLexicalException, ScannerException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case TYFLOAT:
            case TYINT:
            case ID:
            case PRINT:
                parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(arrayNode);
            default:
                break;
        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }

    /** Parsing della produzione DSs */
    private ArrayList<NodeDecSt> parseDSs() throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {
        /* TODO : Togliere valore di ritorno e mettere null */
        Token token = scanner.peekToken();
        switch(token.getTipo()) {
            case TYFLOAT:
            case TYINT:
                arrayNode.add(parseDcl());
                parseDSs();
                return arrayNode;
            case ID:
            case PRINT:
                arrayNode.add(parseStm());
                parseDSs();
                return arrayNode;
            case EOF:
                return arrayNode;
            default:
                break;
        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, token, token.getRiga());

    }

    /** parsing della produzione Dcl */
    private NodeDecl parseDcl() throws IOException, AcdcLexicalException, AcdcSyntaxException, TokenConstructorException {
        Token token = scanner.peekToken();
        switch(token.getTipo()) {
            case TYFLOAT:
                match(TokenType.TYFLOAT);
                token = match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodeDecl(new NodeId(token.getVal()), LangType.FLOAT);
            case TYINT:
                match(TokenType.TYINT);
                token = match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodeDecl(new NodeId(token.getVal()), LangType.INT);
            default:
                break;
        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, token, token.getRiga());
    }

    /** parsing della produzione Stm */
    private NodeStm parseStm() throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {

        Token tk = scanner.peekToken();
        NodeExpr t;
        switch(tk.getTipo()) {
            case ID:
                tk = match(TokenType.ID);
                match(TokenType.ASSIGN);
                t = parseExp();
                match(TokenType.SEMI);
                return new NodeAssign(new NodeId(tk.getVal()), t);
            case PRINT:
                match(TokenType.PRINT);
                tk = match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodePrint(new NodeId(tk.getVal()));
            default:
                break;
        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }

    /** parsing della produzione Exp */
    private NodeExpr parseExp() throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {
        Token tk = scanner.peekToken();
        NodeExpr temp;
        switch(tk.getTipo()) {
            case FLOAT:
            case INT:
            case ID:
                temp = parseTr();
                return parseExpP(temp);
            default:
                break;
        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }

    /** parsing della produzione Tr */
    private NodeExpr parseTr() throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {
        Token tk = scanner.peekToken();
        NodeExpr temp;
        switch(tk.getTipo()) {
            case INT:
            case FLOAT:
            case ID:
                temp = parseVal();
                return parseTrP(temp);
            default:
                break;

        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }

    /** parsing della produzione Val */
    private NodeExpr parseVal() throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {
        Token tk = scanner.peekToken();
        switch(tk.getTipo()) {
            case INT:
                tk = match(TokenType.INT);
                return new NodeCost(tk.getVal(), LangType.INT);
            case FLOAT:
                tk = match(TokenType.FLOAT);
                return new NodeCost(tk.getVal(), LangType.FLOAT);
            case ID:
                tk = match(TokenType.ID);
                return new NodeDeref(new NodeId(tk.getVal()));
            default:
                break;
        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }

    /** parsing della produzione TrP */
    private NodeExpr parseTrP(NodeExpr left) throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {
        Token tk = scanner.peekToken();
        NodeExpr temp;
        switch(tk.getTipo()) {
            case TIMES:
                tk = match(TokenType.TIMES);
                temp = parseVal();
                return parseTrP(new NodeBinOp(LangOper.TIMES, left, temp));
            case DIV:
                tk = match(TokenType.DIV);
                temp = parseVal();
                return parseTrP(new NodeBinOp(LangOper.DIV, left, temp));
            case PLUS:
            case MINUS:
            case FLOAT:
            case INT:
            case SEMI:
            case ID:
            case PRINT:
            case EOF:
                return left;
            default:
                break;

        }
        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }

    private NodeExpr parseExpP(NodeExpr left) throws IOException, TokenConstructorException, AcdcLexicalException, AcdcSyntaxException {
        /* TODO VEDERE video 27 aprile per eventiali problemi */
        /* TODO provare a lasciare solo SEMI nel terzo branch */
        Token tk = scanner.peekToken();
        NodeExpr temp;
        switch(tk.getTipo()) {

            case PLUS:
                tk = match(TokenType.PLUS);
                temp = parseTr();
                return parseExpP(new NodeBinOp(LangOper.PLUS, left, temp));
            case MINUS:
                tk = match(TokenType.MINUS);
                temp = parseTr();
                return parseExpP(new NodeBinOp(LangOper.MINUS, left, temp));
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case SEMI:
            case EOF:
                return left;
            default:
                break;
        }

        throw new AcdcSyntaxException(AcdcSyntaxException.SYNTAX_ERROR_TEMPLATE, tk, tk.getRiga());
    }
}
