package expression.token.visitor;

import expression.token.Brace;
import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserVisitorTest {

    @Test
    public void parseTest() {
        parseScenario(
            List.of(
                new NumberToken(4),
                new Operation(Operation.Type.PLUS),
                new NumberToken(9)
            ),
            List.of(
                new NumberToken(4),
                new NumberToken(9),
                new Operation(Operation.Type.PLUS)
            )
        );
        parseScenario(
            List.of(
                new NumberToken(3),
                new Operation(Operation.Type.PLUS),
                new NumberToken(4),
                new Operation(Operation.Type.MUL),
                new NumberToken(2),
                new Operation(Operation.Type.DIV),
                new Brace(true),
                new NumberToken(1),
                new Operation(Operation.Type.MINUS),
                new NumberToken(5),
                new Brace(false)
            ),
            List.of(
                new NumberToken(3),
                new NumberToken(4),
                new NumberToken(2),
                new Operation(Operation.Type.MUL),
                new NumberToken(1),
                new NumberToken(5),
                new Operation(Operation.Type.MINUS),
                new Operation(Operation.Type.DIV),
                new Operation(Operation.Type.PLUS)
            )
        );
    }

    private static void parseScenario(List<Token> inputTokens, List<Token> expectedTokens) {
        List<Token> parsedTokens = new ParserVisitor().parse(inputTokens);
        assertEquals(new ArrayList<>(expectedTokens), new ArrayList<>(parsedTokens));
    }
}
