import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IsCryptSolutionTest {

    @Test
    void isCryptSolution() {
        String[] crypt = null;
        char[][] solution = null;

        crypt = new String[]{
                "SEND",
                "MORE",
                "MONEY"
        };

        solution = new char[][] {
                {'O', '0'},
                {'M', '1'},
                {'Y', '2'},
                {'E', '5'},
                {'N', '6'},
                {'D', '7'},
                {'R', '8'},
                {'S', '9'}
        };

        assertThat(new IsCryptSolution().isCryptSolution(crypt, solution), is(true));

        crypt = new String[] {
                "TEN",
                "TWO",
                "ONE"
        };

        solution = new char[][]{
                {'O', '1'},
                {'T', '0'},
                {'W', '9'},
                {'E', '5'},
                {'N', '4'}
        };

        assertThat(new IsCryptSolution().isCryptSolution(crypt, solution), is(false));


        crypt = new String[] {
                "A",
                "A",
                "A"
        };

        solution = new char[][] {
                {'A','0'}
        };

        assertThat(new IsCryptSolution().isCryptSolution(crypt, solution), is(true));


        crypt = new String[]{
                "AAAAAAAAAAAAAA",
                "BBBBBBBBBBBBBB",
                "CCCCCCCCCCCCCC"
        };
        solution = new char[][]{
                {'A', '1'},
                {'B', '2'},
                {'C', '3'}
        };

        assertThat(new IsCryptSolution().isCryptSolution(crypt, solution), is(true));
    }
}