package expression.token.visitor;

import expression.token.Brace;
import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

public class PrintVisitor extends AbstractTokenVisitor {

    private PrintStream output;

    public void print(List<Token> tokens, PrintStream output) {
        this.output = output;
        visitAll(tokens);
    }

    public void println(List<Token> tokens, PrintStream output) {
        print(tokens, output);
        output.println();
    }

    @Override
    public void visit(NumberToken token) {
        output.print(token);
        output.print(' ');
    }

    @Override
    public void visit(Brace token) {
        output.print(token);
        if (!token.isOpen()) {
            output.print(' ');
        }
    }

    @Override
    public void visit(Operation token) {
        output.print(token);
        output.print(' ');
    }
}
