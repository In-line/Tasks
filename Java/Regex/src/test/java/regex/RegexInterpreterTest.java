package regex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.exceptions.RegexCompilerError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexInterpreterTest {

    @DisplayName("Basic interpretator test")
    @Test
    void test() throws RegexCompilerError {
        List<IToken> tokens = (new RegexCompiler("ab.+")).getCompiled();
        RegexInterpreter interpreter = new RegexInterpreter(tokens);

        assertTrue(interpreter.interpret("abababab").isMatched());
        assertTrue(interpreter.interpret("abb").isMatched());
        assertTrue(interpreter.interpret("12     abb").isMatched());

        assertFalse(interpreter.interpret("ab").isMatched());
        assertFalse(interpreter.interpret("12345").isMatched());
        assertFalse(interpreter.interpret("a").isMatched());
    }
}
