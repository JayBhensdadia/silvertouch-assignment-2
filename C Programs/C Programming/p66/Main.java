import java.util.Arrays;

public class Main {

    public static double bitonicEuclideanTSP(int[] x, int[] y) {
        int n = x.length;

        double[][] distance = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distance[i][j] = distance[j][i] = euclideanDistance(x[i], y[i], x[j], y[j]);
            }
        }

        double[][] dp = new double[n][n];
        for (double[] row : dp) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }

        dp[0][1] = distance[0][1];

        for (int j = 2; j < n; j++) {
            for (int i = 0; i < j - 1; i++) {
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + distance[j - 1][j]);
            }
            for (int i = 0; i < j - 1; i++) {
                dp[j - 1][j] = Math.min(dp[j - 1][j], dp[i][j - 1] + distance[i][j]);
            }
        }

        double minCost = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n - 1; i++) {
            minCost = Math.min(minCost, dp[i][n - 1] + distance[i][n - 1]);
        }

        return minCost;
    }

    private static double euclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void main(String[] args) {
        int[] x = { 0, 1, 2, 4, 5, 6, 8 };
        int[] y = { 0, 2, 5, 3, 2, 6, 8 };

        double minCost = bitonicEuclideanTSP(x, y);
        System.out.println("Minimum Cost of Bitonic Euclidean TSP: " + minCost);
    }
}
