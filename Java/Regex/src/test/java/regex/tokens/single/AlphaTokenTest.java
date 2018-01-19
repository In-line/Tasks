package regex.tokens.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;

import static org.junit.jupiter.api.Assertions.*;

class AlphaTokenTest {
    @DisplayName("Test AlphaToken")
    @Test
    void test() {
        AlphaToken token = new AlphaToken();
        assertTrue(token.interpret(new InterpretationContext("T")).isMatched());
        assertTrue(token.interpret(new InterpretationContext("t")).isMatched());
        assertTrue(token.interpret(new InterpretationContext("0")).isMatched());
        assertTrue(token.interpret(new InterpretationContext("_")).isMatched());
        assertFalse(token.interpret(new InterpretationContext("$")).isMatched());

        assertEquals(token.interpret(new InterpretationContext("T")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("t")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("0")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("_")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("$")).getMatchedCharactersLen(), 0);
    }
}
