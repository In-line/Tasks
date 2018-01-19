package regex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterpretationContextTest {
    @Test
    @DisplayName("Test InterpretationContext position")
    void testPosition(TestInfo testInfo) {
        InterpretationContext context = new InterpretationContext("0123456789");

        for (int i = 0; i < 10; ++i) {
            assertEquals(context.getChar(), (char) (i + '0'));
            context.nextPosition();
        }
    }
}