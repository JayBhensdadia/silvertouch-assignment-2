import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int numRowsA = matrixA.length;
        int numColsA = matrixA[0].length;
        int numRowsB = matrixB.length;
        int numColsB = matrixB[0].length;

        if (numColsA != numRowsB) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        double[][] result = new double[numRowsA][numColsB];

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        MultiplyTask multiplyTask = new MultiplyTask(matrixA, matrixB, result, 0, numRowsA, 0, numColsB);
        forkJoinPool.invoke(multiplyTask);

        return result;
    }

    static class MultiplyTask extends RecursiveTask<Void> {
        private static final int THRESHOLD = 50;

        private final double[][] matrixA;
        private final double[][] matrixB;
        private final double[][] result;
        private final int startRow;
        private final int endRow;
        private final int startCol;
        private final int endCol;

        MultiplyTask(double[][] matrixA, double[][] matrixB, double[][] result,
                     int startRow, int endRow, int startCol, int endCol) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
            this.startCol = startCol;
            this.endCol = endCol;
        }

        @Override
        protected Void compute() {
            int numRows = endRow - startRow;
            int numCols = endCol - startCol;

            if (numRows <= THRESHOLD || numCols <= THRESHOLD) {
                multiplySequential();
            } else {
                int midRow = startRow + numRows / 2;
                int midCol = startCol + numCols / 2;

                MultiplyTask topLeft = new MultiplyTask(matrixA, matrixB, result, startRow, midRow, startCol, midCol);
                MultiplyTask topRight = new MultiplyTask(matrixA, matrixB, result, startRow, midRow, midCol, endCol);
                MultiplyTask bottomLeft = new MultiplyTask(matrixA, matrixB, result, midRow, endRow, startCol, midCol);
                MultiplyTask bottomRight = new MultiplyTask(matrixA, matrixB, result, midRow, endRow, midCol, endCol);

                invokeAll(topLeft, topRight, bottomLeft, bottomRight);
            }
            return null;
        }

        private void multiplySequential() {
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    for (int k = 0; k < matrixB.length; k++) {
                        result[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        double[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        double[][] matrixB = {
            {9, 8, 7},
            {6, 5, 4},
            {3, 2, 1}
        };

        double[][] result = multiply(matrixA, matrixB);

        System.out.println("Matrix A:");
        printMatrix(matrixA);

        System.out.println("\nMatrix B:");
        printMatrix(matrixB);

        System.out.println("\nResultant Matrix:");
        printMatrix(result);
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

