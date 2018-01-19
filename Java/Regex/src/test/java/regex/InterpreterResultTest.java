package regex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import regex.interpretationresult.InterpreterResult;
import regex.interpretationresult.InterpreterResultBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterResultTest {
    @DisplayName("Test isMatched for various situations")
    @Test
    void testIsMatched() {
        InterpretationContext context = new InterpretationContext("Test string");

        assertFalse(InterpreterResult.createRoot(context).isMatched());


        InterpreterResult goodResult = new InterpreterResultBuilder().setContext(context).setPossiblyMatchedCharactersLen(1).setMatchedCharactersLen(1).setIsGood(true).complete();
        InterpreterResult badResult = new InterpreterResultBuilder().setContext(context).setPossiblyMatchedCharactersLen(1).setMatchedCharactersLen(1).setIsGood(false).complete();

        assertTrue(goodResult.isMatched());
        assertFalse(badResult.isMatched());

        {
            InterpreterResult root = InterpreterResult.createRoot(context);
            root.add(goodResult);
            assertTrue(root.isMatched());
        }

        {
            InterpreterResult root = InterpreterResult.createRoot(context);
            root.add(badResult);
            assertFalse(root.isMatched());
        }

        {
            InterpreterResult root = InterpreterResult.createRoot(context);
            root.add(badResult);
            root.add(goodResult);
            assertFalse(root.isMatched());
        }
    }

    @DisplayName("Test negate")
    @Test
    public void testNegate() {
        InterpretationContext context = new InterpretationContext("123");
        InterpreterResult badResult = new
                InterpreterResultBuilder()
                .setContext(context)
                .setPossiblyMatchedCharactersLen(3)
                .setMatchedCharactersLen(0)
                .setIsGood(false)
                .complete();

        InterpreterResult goodResult = new
                InterpreterResultBuilder()
                .setContext(context)
                .setPossiblyMatchedCharactersLen(3)
                .setMatchedCharactersLen(3)
                .setIsGood(true)
                .complete();

        assertNotEquals(goodResult, badResult);
        assertEquals(badResult.negate(), goodResult);
        assertEquals(goodResult.negate(), badResult);
    }

}