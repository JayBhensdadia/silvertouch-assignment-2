public class Main {
    public static void main(String[] args) {
        int n = 4; 
        solveNQueens(n);
    }

    public static void solveNQueens(int n) {
        int[] queensPlacement = new int[n];
        placeQueens(queensPlacement, 0, n);
    }

    private static void placeQueens(int[] queensPlacement, int row, int n) {
        if (row == n) {
            printQueens(queensPlacement);
        } else {
            for (int col = 0; col < n; col++) {
                if (isValidPlacement(queensPlacement, row, col)) {
                    queensPlacement[row] = col;
                    placeQueens(queensPlacement, row + 1, n);
                }
            }
        }
    }

    private static boolean isValidPlacement(int[] queensPlacement, int row, int col) {
        for (int i = 0; i < row; i++) {
            int otherCol = queensPlacement[i];
            if (otherCol == col || Math.abs(otherCol - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    private static void printQueens(int[] queensPlacement) {
        int n = queensPlacement.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(queensPlacement[i] == j ? "Q " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
