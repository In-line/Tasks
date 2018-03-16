package regex.token.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;

import static org.junit.jupiter.api.Assertions.*;

class DigitTokenTest {
    @DisplayName("Test DigitToken")
    @Test
    void test() {
        DigitToken token = new DigitToken();
        assertTrue(token.interpret(new InterpretationContext("1")).isMatched());
        assertEquals(token.interpret(new InterpretationContext("1")).getMatchedCharactersLen(), 1);

        assertFalse(token.interpret(new InterpretationContext("a")).isMatched());
        assertEquals(token.interpret(new InterpretationContext("a")).getMatchedCharactersLen(), 0);
    }
}