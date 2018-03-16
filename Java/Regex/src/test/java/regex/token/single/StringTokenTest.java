package regex.token.single;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.InterpretationContext;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;
import regex.token.StringToken;

import static org.junit.jupiter.api.Assertions.*;

class StringTokenTest {
    @DisplayName("Test StringToken")
    @Test
    void test() {
        StringToken token = new StringToken("123");

        {
            InterpretationContext context = new InterpretationContext("123");
            InterpreterResult result = token.interpret(context);

            assertTrue(result.isMatched());
            InterpreterResult b = new InterpreterResultBuilder().setString(context.getOriginalString()).setPosition(0).setPossiblyMatchedCharactersLen(3).setMatchedCharactersLen(3).setIsGood(true).complete();
            assertEquals(result, b);
        }
    }
}
