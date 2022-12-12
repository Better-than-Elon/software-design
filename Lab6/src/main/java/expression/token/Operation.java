package expression.token;

import expression.token.visitor.TokenVisitor;

import java.util.Objects;
import java.util.function.BiFunction;

public class Operation implements Token {

    private final Type type;

    public Operation(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public int getPriority() {
        return type.getPriority();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return type == operation.type;
    }

    public int calc(int a, int b) {
        return type.calc(a, b);
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public enum Type {
        PLUS("+", 1, Integer::sum),
        MINUS("-", 1, (a, b) -> a - b),
        MUL("*", 2, (a, b) -> a * b),
        DIV("/", 2, (a, b) -> a / b);

        private final String str;
        private final int priority;
        private final BiFunction<Integer, Integer, Integer> calcFunction;

        Type(String str, int priority, BiFunction<Integer, Integer, Integer> calcFunction) {
            this.str = str;
            this.priority = priority;
            this.calcFunction = calcFunction;
        }

        @Override
        public String toString() {
            return str;
        }

        public int getPriority() {
            return priority;
        }

        public int calc(int a, int b) {
            return calcFunction.apply(a, b);
        }
    }
}
