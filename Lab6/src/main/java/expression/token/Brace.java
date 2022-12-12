package expression.token;

import expression.token.visitor.TokenVisitor;

public class Brace implements Token {

    private final boolean open;

    public Brace(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brace brace = (Brace) o;
        return open == brace.open;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return ")";
    }
}
