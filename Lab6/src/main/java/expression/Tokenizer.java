package expression;

import expression.exception.IllegalCharacterException;
import expression.token.Brace;
import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private State state;
    private List<Token> tokens;

    public List<Token> tokenize(InputStream input) throws IOException, IllegalCharacterException {
        state = State.START;
        tokens = new ArrayList<>();

        int read;
        while (true) {
            read = input.read();
            if (read < 0) {
                break;
            }
            step((char) read);
        }

        return tokens;
    }

    private void step(char ch) throws IllegalCharacterException {
        if (state == State.START) {
            if (Character.isDigit(ch)) {
                state = State.NUMBER;
                tokens.add(new NumberToken(toDigit(ch)));
            } else if (ch == '(') {
                tokens.add(new Brace(true));
            } else if (ch == ')') {
                tokens.add(new Brace(false));
            } else if (ch == '+') {
                tokens.add(new Operation(Operation.Type.PLUS));
            } else if (ch == '-') {
                tokens.add(new Operation(Operation.Type.MINUS));
            } else if (ch == '*') {
                tokens.add(new Operation(Operation.Type.MUL));
            } else if (ch == '/') {
                tokens.add(new Operation(Operation.Type.DIV));
            } else if (!Character.isWhitespace(ch)) {
                throw new IllegalCharacterException("illegal char '" + ch + "'");
            }
        } else if (state == State.NUMBER) {
            if (Character.isDigit(ch)) {
                int valueWas = ((NumberToken) tokens.get(tokens.size() - 1)).getValue();
                NumberToken element = new NumberToken(valueWas * 10 + toDigit(ch));
                tokens.set(tokens.size() - 1, element);
            } else {
                state = State.START;
                step(ch);
            }
        }
    }

    private static int toDigit(char ch) {
        return ch - '0';
    }

    private enum State {
        START, NUMBER
    }
}
