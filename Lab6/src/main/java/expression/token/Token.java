package expression.token;

import expression.token.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
