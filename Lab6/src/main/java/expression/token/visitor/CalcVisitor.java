package expression.token.visitor;

import expression.token.Brace;
import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;

import java.util.List;
import java.util.Stack;

public class CalcVisitor extends AbstractTokenVisitor {

    private Stack<Integer> stack;

    public int calc(List<Token> tokens) {
        stack = new Stack<>();
        stack.push(0);
        visitAll(tokens);
        return stack.pop();
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(Brace token) {
    }

    @Override
    public void visit(Operation token) {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(token.calc(a, b));
    }
}
