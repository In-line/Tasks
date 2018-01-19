import static java.lang.Math.abs;

// https://codefights.com/interview-practice/task/pMvymcahZ8dY4g75q/description
public class FirstDuplicate {
    public int firstDuplicate(int[] a) {
        for(int i = 0; i < a.length; ++i) {
            int valAbs = abs(a[i]) - 1;

            if(a[valAbs] < 0) {
                return valAbs + 1;
            }

            a[valAbs] = -a[valAbs];
        }
        return -1;
    }
}
