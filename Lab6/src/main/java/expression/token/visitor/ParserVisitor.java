package expression.token.visitor;

import expression.token.Brace;
import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor extends AbstractTokenVisitor {

    List<Token> outputTokens;
    Stack<Token> tokenStack;

    public List<Token> parse(List<Token> inputTokens) {
        outputTokens = new ArrayList<>();
        tokenStack = new Stack<>();
        visitAll(inputTokens);
        while (!tokenStack.empty()) {
            outputTokens.add(tokenStack.pop());
        }
        return outputTokens;
    }

    @Override
    public void visit(NumberToken token) {
        outputTokens.add(token);
    }

    @Override
    public void visit(Brace brace) {
        if (brace.isOpen()) {
            tokenStack.push(brace);
        } else {
            while (!isOpenBrace(tokenStack.peek())) {
                outputTokens.add(tokenStack.pop());
            }
            tokenStack.pop();  // open brace
        }
    }

    private boolean isOpenBrace(Token token) {
        if (token instanceof Brace) {
            return ((Brace) token).isOpen();
        }
        return false;
    }

    @Override
    public void visit(Operation operation) {
        while (
            !tokenStack.empty()
                && isOperationWithHigherOrEqualPriority(tokenStack.peek(), operation.getPriority())
        ) {
            outputTokens.add(tokenStack.pop());
        }
        tokenStack.push(operation);
    }

    private boolean isOperationWithHigherOrEqualPriority(Token token, int priority) {
        if (token instanceof Operation) {
            return ((Operation) token).getPriority() >= priority;
        }
        return false;
    }
}
