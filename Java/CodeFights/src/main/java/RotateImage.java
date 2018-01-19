// https://codefights.com/interview-practice/task/5A8jwLGcEpTPyyjTB/description
public class RotateImage {
    public int[][] rotateImage(int[][] a) {
        for(int i = 0; i < a.length; ++i) {
            for(int j = 0; j < a[i].length; ++j) {
                if(a[i][j] > 0) {
                    int temp = a[i][j];
                    a[i][j] = a[j][a.length - i - 1];
                    a[j][a.length - i - 1] = -temp;
                }
            }
        }

        for(int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[i].length; ++j) {
                if(a[i][j] < 0) {
                    a[i][j] = -a[i][j];
                }
            }
        }
        return a;
    }
}
