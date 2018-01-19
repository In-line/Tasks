import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FirstDuplicateTest {

    @Test
    void firstDuplicate() {
        FirstDuplicate firstDuplicate = new FirstDuplicate();
        assertThat(firstDuplicate.firstDuplicate(new int[]{2, 3, 3, 1, 5, 2}), is(3));
        assertThat(firstDuplicate.firstDuplicate(new int[]{2, 4, 3, 5, 1}), is(-1));
        assertThat(firstDuplicate.firstDuplicate(new int[]{2, 3, 3}), is(3));

    }
}