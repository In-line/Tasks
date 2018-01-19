// https://codefights.com/interview-practice/task/uX5iLwhc6L5ckSyNC/description
public class FirstNotRepeatingCharacter {
    public char firstNotRepeatingCharacter(String s) {
        for(int i = 0; i < s.length(); ++i) {
            boolean isGood = true;
            for(int j = 0; j < s.length(); ++j) {
                if(i == j)
                    continue;

                if(s.charAt(i) == s.charAt(j)) {
                    isGood = false;
                    break;
                }
            }

            if(isGood) {
                return s.charAt(i);
            }
        }
        return '_';
    }
}
