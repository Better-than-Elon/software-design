package expression.token;

import expression.token.visitor.TokenVisitor;

public class NumberToken implements Token {

    private final int value;

    public NumberToken(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberToken numberToken = (NumberToken) o;
        return value == numberToken.value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
