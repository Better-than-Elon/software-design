package expression.token.visitor;

import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalcVisitorTest {

    @Test
    public void calcTest() {
        calcScenario(
            List.of(
                new NumberToken(10),
                new NumberToken(15),
                new Operation(Operation.Type.MINUS),
                new NumberToken(3),
                new Operation(Operation.Type.MUL)
            ),
            -15
        );
        calcScenario(
            List.of(
                new NumberToken(1),
                new NumberToken(2),
                new Operation(Operation.Type.PLUS),
                new NumberToken(4),
                new Operation(Operation.Type.MUL),
                new NumberToken(3),
                new Operation(Operation.Type.PLUS)
            ),
            15
        );
    }

    private static void calcScenario(List<Token> tokens, int expectedResult) {
        int result = new CalcVisitor().calc(tokens);
        assertEquals(expectedResult, result);
    }
}
