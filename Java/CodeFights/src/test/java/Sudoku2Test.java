import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Sudoku2Test {

    @Test
    void sudoku2() {
        char[][] grid = null;

        grid = new char[][]{
                {'.', '.', '.', '1', '4', '.', '.', '2', '.'},
                {'.', '.', '6', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '1', '.', '.', '.', '.', '.', '.'},
                {'.', '6', '7', '.', '.', '.', '.', '.', '9'},
                {'.', '.', '.', '.', '.', '.', '8', '1', '.'},
                {'.', '3', '.', '.', '.', '.', '.', '.', '6'},
                {'.', '.', '.', '.', '.', '7', '.', '.', '.'},
                {'.', '.', '.', '5', '.', '.', '.', '7', '.'}
        };

        assertThat(new Sudoku2(grid).sudoku2(), is(true));

        grid = new char[][] {
                {'.', '.', '.', '.', '2', '.', '.', '9', '.'},
                {'.', '.', '.', '.', '6', '.', '.', '.', '.'},
                {'7', '1', '.', '.', '7', '5', '.', '.', '.'},
                {'.', '7', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '8', '3', '.', '.', '.'},
                {'.', '.', '8', '.', '.', '7', '.', '6', '.'},
                {'.', '.', '.', '.', '.', '2', '.', '.', '.'},
                {'.', '1', '.', '2', '.', '.', '.', '.', '.'},
                {'.', '2', '.', '.', '3', '.', '.', '.', '.'}
        };

        assertThat(new Sudoku2(grid).sudoku2(), is(false));
        
        grid = new char[][]{
                {'.', '.', '.', '.', '.', '.', '5', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'9', '3', '.', '.', '2', '.', '4', '.', '.'},
                {'.', '.', '7', '.', '.', '.', '3', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '3', '4', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '3', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '5', '2', '.', '.'}
        };

        assertThat(new Sudoku2(grid).sudoku2(), is(false));
    }
}