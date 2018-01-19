import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

class FirstNotRepeatingCharacterTest {

    @Test
    void firstNotRepeatingCharacter() {
        FirstNotRepeatingCharacter firstNotRepeatingCharacter = new FirstNotRepeatingCharacter();
        assertThat(firstNotRepeatingCharacter.firstNotRepeatingCharacter("aba"), is('b'));
        assertThat(firstNotRepeatingCharacter.firstNotRepeatingCharacter("abacabaabacaba"), is('_'));

    }
}