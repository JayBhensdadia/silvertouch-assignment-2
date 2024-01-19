import java.util.LinkedList;
import java.util.Queue;

public class Main {

    private static final int[] rowNeighbors = {-1, 0, 1, 0};
    private static final int[] colNeighbors = {0, 1, 0, -1};

    public static int bfs(int[][] grid, int startRow, int startCol, int targetRow, int targetCol) {
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{startRow, startCol, 0});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentRow = current[0];
            int currentCol = current[1];
            int distance = current[2];

            if (currentRow == targetRow && currentCol == targetCol) {
                return distance;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = currentRow + rowNeighbors[i];
                int newCol = currentCol + colNeighbors[i];

                if (isValid(rows, cols, newRow, newCol) && !visited[newRow][newCol] && grid[newRow][newCol] == 1) {
                    queue.offer(new int[]{newRow, newCol, distance + 1});
                    visited[newRow][newCol] = true;
                }
            }
        }

        return -1; 
    }

    private static boolean isValid(int rows, int cols, int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };

        int startRow = 0;
        int startCol = 0;
        int targetRow = 4;
        int targetCol = 4;

        int distance = bfs(grid, startRow, startCol, targetRow, targetCol);

        if (distance != -1) {
            System.out.println("Shortest distance from (" + startRow + "," + startCol + ") to (" + targetRow + "," + targetCol + "): " + distance);
        } else {
            System.out.println("Target is not reachable from the starting point.");
        }
    }
}
