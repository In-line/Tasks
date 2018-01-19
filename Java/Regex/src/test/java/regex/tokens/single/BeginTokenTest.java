package regex.tokens.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;
import regex.tokens.single.special.BeginToken;

import static org.junit.jupiter.api.Assertions.*;

class BeginTokenTest {
    @DisplayName("Test BeginToken")
    @Test
    void test() {
        BeginToken token = new BeginToken();
        assertTrue(token.interpret(new InterpretationContext("Test")).isMatched());
        assertTrue(token.interpret(new InterpretationContext("")).isMatched());
        assertFalse(token.interpret(new InterpretationContext("Test", 1)).isMatched());

        assertEquals(token.interpret(new InterpretationContext("Test")).getMatchedCharactersLen(), 0);
        assertEquals(token.interpret(new InterpretationContext("")).getMatchedCharactersLen(), 0);
        assertEquals(token.interpret(new InterpretationContext("Test", 1)).getMatchedCharactersLen(), 0);
    }
}