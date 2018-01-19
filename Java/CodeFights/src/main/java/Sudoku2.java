import java.util.HashSet;

// https://codefights.com/interview-practice/task/SKZ45AF99NpbnvgTn/description
public class Sudoku2 {
    private char[][] grid;

    public Sudoku2(char[][] grid) {
        this.grid = grid;
    }

    public boolean sudoku2() {
        if (!checkVerticalAndHorizontal())
            return false;

        if (!checkSubGrids())
            return false;

        return true;
    }

    private boolean checkSubGrids() {
        for(int i = 0; i < grid.length; i+=3) {
            for(int j = 0; j < grid.length; j+=3) {
                if(!checkSubGrid(i,j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkVerticalAndHorizontal() {
        for(int i = 0; i < grid.length; ++i) {
            HashSet set1 = new HashSet();
            HashSet set2 = new HashSet();
            for(int j = 0; j < grid.length; ++j) {
                if (isBadCell(j, i, set1)) return false;
                if (isBadCell(i, j, set2)) return false;
            }
        }
        return true;
    }

    private boolean checkSubGrid(int startX, int startY) {
        HashSet set = new HashSet();
        for(int i = startX; i < startX + 3; ++i) {
            for(int j = startY; j < startY + 3; ++j) {
                if(isBadCell(i, j, set)) return false;
            }
        }
        return true;
    }

    private boolean isBadCell(int j, int i, HashSet set) {
        if (grid[i][j] != '.') {
            Object ob = new Character(grid[i][j]);
            if (set.contains(ob)) {
                return true;
            }

            set.add(ob);
        }
        return false;
    }
}
