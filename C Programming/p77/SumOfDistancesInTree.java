package C.p77;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SumOfDistancesInTree {

    private int[] subtreeSize;
    private int[] result;
    private List<Set<Integer>> tree;

    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        result = new int[n];
        subtreeSize = new int[n];
        tree = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            tree.add(new HashSet<>());
        }

        // Build the tree
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            tree.get(u).add(v);
            tree.get(v).add(u);
        }

        calculateSubtreeSize(0, -1);
        calculateResult(0, -1, n);

        return result;
    }

    private void calculateSubtreeSize(int node, int parent) {
        subtreeSize[node] = 1;
        for (int child : tree.get(node)) {
            if (child != parent) {
                calculateSubtreeSize(child, node);
                subtreeSize[node] += subtreeSize[child];
            }
        }
    }

    private void calculateResult(int node, int parent, int n) {
        for (int child : tree.get(node)) {
            if (child != parent) {
                result[child] = result[node] + n - 2 * subtreeSize[child];
                calculateResult(child, node, n);
            }
        }
    }

    public static void main(String[] args) {
        SumOfDistancesInTree solution = new SumOfDistancesInTree();

        int n = 6;
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}};
        int[] result = solution.sumOfDistancesInTree(n, edges);

        System.out.println(Arrays.toString(result));
    }
}
