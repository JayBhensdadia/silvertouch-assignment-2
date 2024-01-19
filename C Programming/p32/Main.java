import java.util.Arrays;

public class Main {
    private int numRows;
    private int numCols;
    private int[][] costMatrix;
    private int[] matchingRow;
    private int[] matchingCol;
    private boolean[] rowVisited;
    private boolean[] colVisited;

    public Main(int[][] costMatrix) {
        this.numRows = costMatrix.length;
        this.numCols = costMatrix[0].length;
        this.costMatrix = costMatrix;
        this.matchingRow = new int[numRows];
        this.matchingCol = new int[numCols];
        Arrays.fill(matchingRow, -1);
        Arrays.fill(matchingCol, -1);
        this.rowVisited = new boolean[numRows];
        this.colVisited = new boolean[numCols];
    }

    public int findMaximumMatching() {
        for (int row = 0; row < numRows; row++) {
            Arrays.fill(rowVisited, false);
            Arrays.fill(colVisited, false);
            if (findAugmentingPath(row)) {
                Arrays.fill(rowVisited, false);
                augmentPath(row);
            }
        }

        int maxMatching = 0;
        for (int row = 0; row < numRows; row++) {
            if (matchingRow[row] != -1) {
                maxMatching++;
            }
        }

        return maxMatching;
    }

    private boolean findAugmentingPath(int row) {
        rowVisited[row] = true;

        for (int col = 0; col < numCols; col++) {
            if (!colVisited[col] && costMatrix[row][col] == 0) {
                colVisited[col] = true;

                if (matchingCol[col] == -1 || findAugmentingPath(matchingCol[col])) {
                    matchingRow[row] = col;
                    matchingCol[col] = row;
                    return true;
                }
            }
        }

        return false;
    }

    private void augmentPath(int row) {
        for (int col = 0; col < numCols; col++) {
            if (!colVisited[col] && costMatrix[row][col] == 0) {
                colVisited[col] = true;
                augmentPath(matchingCol[col]);
                matchingRow[row] = col;
                matchingCol[col] = row;
            }
        }
    }

    public static void main(String[] args) {
        int[][] costMatrix = {
                {1, 2, 1},
                {3, 4, 1},
                {2, 4, 0}
        };

        Main hungarianAlgorithm = new Main(costMatrix);
        int maxMatching = hungarianAlgorithm.findMaximumMatching();

        System.out.println("Maximum Bipartite Matching: " + maxMatching);
    }
}
