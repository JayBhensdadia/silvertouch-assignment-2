import java.util.*;

public class Main {
    public static int tsp(int[][] graph, int start) {
        int n = graph.length;
        int[][] dp = new int[1 << n][n];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return tspHelper(graph, start, (1 << n) - 1, dp);
    }

    private static int tspHelper(int[][] graph, int u, int mask, int[][] dp) {
        int n = graph.length;

        if (mask == 0) {
            return graph[u][0]; 
        }

        if (dp[mask][u] != -1) {
            return dp[mask][u];
        }

        int minCost = Integer.MAX_VALUE;

        for (int v = 0; v < n; v++) {
            if ((mask & (1 << v)) != 0) {
                int newMask = mask ^ (1 << v);
                int cost = graph[u][v] + tspHelper(graph, v, newMask, dp);
                minCost = Math.min(minCost, cost);
            }
        }

        dp[mask][u] = minCost;
        return minCost;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 16, 15, 29},
                {10, 0, 35, 95},
                {15, 35, 0, 35},
                {20, 27, 40, 0}
        };

        int startCity = 0;
        int minCost = tsp(graph, startCity);

        System.out.println("Minimum Cost of Traveling Salesman Problem: " + minCost);
    }
}
