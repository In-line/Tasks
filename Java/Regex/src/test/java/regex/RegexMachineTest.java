package regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexMachineTest {
    @Test
    public void dummy() throws Exception {
        final RegexMachine rm = new RegexMachine("^..$");

        assertFalse(rm.execute("a").isMatched());
        assertFalse(rm.execute("").isMatched());
        assertNotEquals("aa", rm.execute("aaa").getMatchedSubString());
        assertEquals("..", rm.execute("..").getMatchedSubString());
    }
}