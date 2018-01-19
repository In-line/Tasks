// https://codefights.com/interview-practice/task/yM4uWYeQTHzYewW9H/description
public class IsCryptSolution {
    public boolean isCryptSolution(String[] crypt, char[][] solution) {
        String[] decrypt = new String[crypt.length];

        for(int i = 0; i < crypt.length; ++i) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < crypt[i].length(); ++j) {
                builder.append(numberFromSolution(crypt[i].charAt(j), solution));
            }
            decrypt[i] = builder.toString();
        }

        for(int i = 0; i < decrypt.length; ++i) {
            if(decrypt[i].equals("0")) {
                break;
            }

            if(decrypt[i].charAt(0) == '0') {
                return false;
            }
        }


        return (Long.parseLong(decrypt[0]) + Long.parseLong(decrypt[1])) == Long.parseLong(decrypt[2]);
    }

    public char numberFromSolution(char c, char[][] solution) {
        for(int i = 0; i < solution.length; ++i) {
            if(solution[i][0] == c) {
                return solution[i][1];
            }
        }
        throw new IllegalArgumentException();
    }
}
