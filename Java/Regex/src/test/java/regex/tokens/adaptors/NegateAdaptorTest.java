package regex.tokens.adaptors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.interpretationresult.InterpreterResult;
import regex.RegexInterpreter;
import regex.RegexMachine;
import regex.exceptions.RegexCompilerError;
import regex.tokens.single.special.BeginToken;
import regex.tokens.StringToken;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NegateAdaptorTest {

    @DisplayName("Test basic NegateAdaptor")
    @Test
    void test() {
        RegexInterpreter interpreter = new RegexInterpreter(Arrays.asList(new BeginToken(), new NegateAdaptor(new StringToken("Test"))));
        assertFalse(interpreter.interpret("Test").isMatched());
        assertEquals("", interpreter.interpret("Test").getMatchedSubString());

        InterpreterResult result = interpreter.interpret("TEst1");

        assertTrue(result.isMatched());
        // TODO: Think about this specific case. What should match?
//        assertEquals("Test1", interpreter.interpret("Test1").getMatchedSubString());
    }

    @DisplayName("Test NegateAdaptor with some single tokens")
    @Test
    void testSimpleTokens() throws RegexCompilerError {
        RegexMachine regexMachine = new RegexMachine("^\\S\\D$");

        InterpreterResult result = regexMachine.execute("1 ");

        assertTrue(result.isMatched());
        assertEquals("1 ",result.getMatchedSubString());


    }
}