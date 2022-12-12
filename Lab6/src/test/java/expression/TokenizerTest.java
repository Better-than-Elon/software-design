package expression;

import expression.exception.IllegalCharacterException;
import expression.token.Brace;
import expression.token.NumberToken;
import expression.token.Operation;
import expression.token.Token;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {

    @Test
    public void tokenizeTest() throws IllegalCharacterException, IOException {
        tokenizeScenario(
            "1 + 4",
            new ArrayList<>(List.of(
                new NumberToken(1),
                new Operation(Operation.Type.PLUS),
                new NumberToken(4)
            ))
        );
        tokenizeScenario(
            "44 / (32 - 10 * 1)",
            new ArrayList<>(List.of(
                new NumberToken(44),
                new Operation(Operation.Type.DIV),
                new Brace(true),
                new NumberToken(32),
                new Operation(Operation.Type.MINUS),
                new NumberToken(10),
                new Operation(Operation.Type.MUL),
                new NumberToken(1),
                new Brace(false)
            ))
        );
    }

    private static void tokenizeScenario(
        String input, List<Token> expectedTokens
    ) throws IllegalCharacterException, IOException {
        List<Token> tokens = new Tokenizer().tokenize(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        assertEquals(expectedTokens, tokens);
    }

    @Test(expected = IllegalCharacterException.class)
    public void tokenizeFailTest() throws IllegalCharacterException, IOException {
        List<Token> tokens = new Tokenizer().tokenize(
            new ByteArrayInputStream("%".getBytes(StandardCharsets.UTF_8))
        );
    }
}
