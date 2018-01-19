package regex.tokens.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;
import regex.tokens.single.special.EndToken;

import static org.junit.jupiter.api.Assertions.*;


class EndTokenTest {
    @DisplayName("Test EndToken")
    @Test
    void test() {
        EndToken token = new EndToken();
        assertTrue(token.interpret(new InterpretationContext("")).isMatched());
        assertTrue(token.interpret(new InterpretationContext("\n")).isMatched());
        assertFalse(token.interpret(new InterpretationContext("\n\n")).isMatched());

        assertEquals(token.interpret(new InterpretationContext("")).getMatchedCharactersLen(), 0);
        assertEquals(token.interpret(new InterpretationContext("\n")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("\n\n")).getMatchedCharactersLen(), 0);
    }
}