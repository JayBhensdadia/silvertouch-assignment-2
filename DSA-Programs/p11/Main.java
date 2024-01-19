import java.util.*;

class Main {
    static int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        List<EdgeCost> costs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            costs.add(new EdgeCost(0, i + 1, wells[i])); // Connect each house to a pseudo-source
        }
        for (int[] pipe : pipes) {
            costs.add(new EdgeCost(pipe[0], pipe[1], pipe[2]));
        }

        Collections.sort(costs); // Sort edges by cost

        int mstCost = 0;
        UnionFind uf = new UnionFind(n + 1); // Create a UnionFind object with n+1 nodes (including pseudo-source)
        for (EdgeCost edge : costs) {
            if (!uf.connected(edge.from, edge.to)) {
                mstCost += edge.cost;
                uf.union(edge.from, edge.to);
            }
        }

        return mstCost;
    }

    static class EdgeCost implements Comparable<EdgeCost> {
        int from, to, cost;

        EdgeCost(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(EdgeCost other) {
            return this.cost - other.cost;
        }
    }

    static class UnionFind {
        int[] parent;

        UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int x, int y) {
            parent[find(x)] = find(y);
        }

        boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    public static void main(String[] args) {
        int N = 3;
        int[] wells = {1, 2, 2};
        int[][] pipes = {{1, 2, 1}, {2, 3, 1}};
        System.out.println(minCostToSupplyWater(N, wells, pipes));

        N = 4;
        wells = new int[]{1, 1, 1, 1};
        pipes = new int[][]{{1, 2, 100}, {2, 3, 100}, {2, 4, 50}};
        System.out.println(minCostToSupplyWater(N, wells, pipes));
    }
}
