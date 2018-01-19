import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RotateImageTest {

    @Test
    void rotateImage() {
        RotateImage ri = new RotateImage();
        int[][] in = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] out = {
                {7, 4, 1},
                {8, 5, 2},
                {9, 6, 3}
        };

        assertThat(new RotateImage().rotateImage(in), is(out));
    }
}