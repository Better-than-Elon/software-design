package expression.token.visitor;

import expression.token.Token;

import java.util.List;

public abstract class AbstractTokenVisitor implements TokenVisitor {
    protected void visitAll(List<Token> tokens) {
        tokens.forEach(t -> t.accept(this));
    }
}
