package regex.tokens.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;

import static org.junit.jupiter.api.Assertions.*;

class OneCharacterTokenTest {
    @DisplayName("Test OneCharacterToken")
    @Test
    void test() {
        OneCharacterToken token = new OneCharacterToken('1');

        assertTrue(token.interpret(new InterpretationContext("1")).isMatched());
        assertFalse(token.interpret(new InterpretationContext("2")).isMatched());
        assertFalse(token.interpret(new InterpretationContext("21")).isMatched());

        assertEquals("1", token.interpret(new InterpretationContext("1")).getMatchedSubString());
        assertEquals("", token.interpret(new InterpretationContext("2")).getMatchedSubString());
        assertEquals("", token.interpret(new InterpretationContext("21")).getMatchedSubString());

    }
}