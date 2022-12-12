package expression;

import expression.exception.IllegalCharacterException;
import expression.token.Token;
import expression.token.visitor.CalcVisitor;
import expression.token.visitor.ParserVisitor;
import expression.token.visitor.PrintVisitor;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalCharacterException, IOException {
        List<Token> tokens = new Tokenizer().tokenize(System.in);
        tokens = new ParserVisitor().parse(tokens);
        new PrintVisitor().println(tokens, System.out);
        System.out.println(new CalcVisitor().calc(tokens));
    }
}
