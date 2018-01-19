package regex.tokens.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;

import static org.junit.jupiter.api.Assertions.*;


class WhiteSpaceTokenTest {
    @DisplayName("Test WhiteSpaceToken")
    @Test
    void test() {
        WhiteSpaceToken token = new WhiteSpaceToken();
        assertTrue(token.interpret(new InterpretationContext(" ")).isMatched());
        assertEquals(token.interpret(new InterpretationContext(" ")).getMatchedCharactersLen(), 1);

        assertFalse(token.interpret(new InterpretationContext("")).isMatched());
        assertEquals(token.interpret(new InterpretationContext("")).getMatchedCharactersLen(), 0);
    }
}
