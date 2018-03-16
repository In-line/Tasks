package regex.token.facade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;
import regex.RegexInterpreter;
import regex.token.adaptor.StarAdaptor;
import regex.token.single.DotToken;
import regex.token.StringToken;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class GroupFacadeTest {

    @DisplayName("Test backtracking")
    @Test
    void testBackTracking() {
        GroupFacade rootGroup = new GroupFacade(Arrays.asList(new DotToken(), new StarAdaptor(new StringToken("b"))));

        assertTrue(rootGroup.interpret(new InterpretationContext("aaaaaabcd")).isMatched());
        assertTrue(rootGroup.interpret(new InterpretationContext("aaaaaab")).isMatched());
        assertTrue(rootGroup.interpret(new InterpretationContext("abcd")).isMatched());
    }

    @DisplayName("Test normal match")
    @Test
    void testNormalMatch() {
        RegexInterpreter regexInterpreter = new RegexInterpreter(Arrays.asList(new DotToken(), new StringToken("b")));

        assertTrue(regexInterpreter.interpret("aaaaaabcd").isMatched());
        assertTrue(regexInterpreter.interpret("aaaaaab").isMatched());
        assertTrue(regexInterpreter.interpret("abcd").isMatched());
    }

    @DisplayName("Test hanging with repetive StarAdaptors")
    @Test
    void testHanging() {

        RegexInterpreter regexInterpreter = new RegexInterpreter(Arrays.asList(new StarAdaptor(new StarAdaptor(new DotToken())), new StringToken("b")));

        assertEquals("", regexInterpreter.interpret("aaaacdd").getMatchedSubString());
        assertEquals("aaaacb", regexInterpreter.interpret("aaaacbd").getMatchedSubString());
        assertFalse(regexInterpreter.interpret("aaaacdd").isMatched());
    }
}