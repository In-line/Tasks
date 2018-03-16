package regex.token.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;

import static org.junit.jupiter.api.Assertions.*;

class CharacterRangeTokenTest {

    @Test
    @DisplayName("Test CharacterRangeToken")
    void test() {
        CharacterRangeToken token = new CharacterRangeToken('a', 'z');

        for(char i = 'a'; i <= 'z'; ++i) {
            assertTrue(token.interpret(new InterpretationContext(String.valueOf(i))).isMatched());
            assertEquals(String.valueOf(i), token.interpret(new InterpretationContext(String.valueOf(i))).getMatchedSubString());
        }

        assertFalse(token.interpret(new InterpretationContext("$")).isMatched());
        assertEquals("", token.interpret(new InterpretationContext("$")).getMatchedSubString());

    }
}