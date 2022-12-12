package expression.token.visitor;

import expression.token.*;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
