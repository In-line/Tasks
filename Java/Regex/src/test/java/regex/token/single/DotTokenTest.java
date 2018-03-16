package regex.token.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;

import static org.junit.jupiter.api.Assertions.*;

class DotTokenTest {
    @DisplayName("Test DotToken")
    @Test
    void test() {
        DotToken token = new DotToken();

        assertTrue(token.interpret(new InterpretationContext("Te")).isMatched());
        assertTrue(token.interpret(new InterpretationContext("T")).isMatched());
        assertFalse(token.interpret(new InterpretationContext("")).isMatched());

        assertEquals(token.interpret(new InterpretationContext("Te")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("T")).getMatchedCharactersLen(), 1);
        assertEquals(token.interpret(new InterpretationContext("")).getMatchedCharactersLen(), 0);

        assertTrue(token.equals(new DotToken()));
    }
}