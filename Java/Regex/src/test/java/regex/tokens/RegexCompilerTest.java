package regex.tokens;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import regex.IToken;
import regex.RegexCompiler;
import regex.tokens.adaptors.PlusAdaptor;
import regex.exceptions.RegexCompilerError;
import regex.tokens.single.special.BeginToken;
import regex.tokens.single.special.EndToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexCompilerTest {
    @Test
    @DisplayName("Test compiler tokenList correctness")
    void test(TestInfo testInfo) throws RegexCompilerError {
        List<IToken> tokens = (new RegexCompiler("^ab+$")).getCompiled();

        int i = 0;
        Assertions.assertEquals(new BeginToken(), tokens.get(i++));
        assertEquals(new PlusAdaptor(new StringToken("ab")), tokens.get(i++));
        Assertions.assertEquals(new EndToken(), tokens.get(i++));
    }
}