import java.util.*;

class EdmondsBlossom {

    static class Graph {
        int V;
        List<Integer>[] adj;

        public Graph(int V) {
            this.V = V;
            adj = new List[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        public void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }
    }

    private static int[] match;
    private static int[] parent;
    private static int[] base;
    private static boolean[] blossom;
    private static Queue<Integer> queue;

    public static List<int[]> findMaxMatching(Graph graph) {
        int n = graph.V;
        match = new int[n];
        parent = new int[n];
        base = new int[n];
        blossom = new boolean[n];
        queue = new LinkedList<>();

        Arrays.fill(match, -1);

        for (int i = 0; i < n; i++) {
            if (match[i] == -1) {
                int v = findAugmentingPath(graph, i);
                while (v != -1) {
                    augmentPath(v);
                    v = findAugmentingPath(graph, i);
                }
            }
        }

        List<int[]> maxMatching = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (match[i] != -1) {
                maxMatching.add(new int[]{i, match[i]});
            }
        }

        return maxMatching;
    }

    private static int findAugmentingPath(Graph graph, int start) {
        Arrays.fill(parent, -1);
        Arrays.fill(base, -1);
        Arrays.fill(blossom, false);
        queue.clear();

        queue.offer(start);
        blossom[start] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph.adj[u]) {
                if (base[u] == base[v] || match[u] == v) {
                    continue;
                }

                if (parent[v] == -1) {
                    parent[v] = u;
                    if (match[v] == -1) {
                        return v;
                    }
                    queue.offer(match[v]);
                    blossom[match[v]] = true;
                } else {
                    int newBase = findCommonAncestor(u, v);
                    markBlossom(u, v, newBase);
                    markBlossom(v, u, newBase);
                }
            }
        }

        return -1;
    }

    private static void augmentPath(int v) {
        while (v != -1) {
            int pv = parent[v];
            int ppv = match[pv];
            match[v] = pv;
            match[pv] = v;
            v = ppv;
        }
    }

    private static int findCommonAncestor(int u, int v) {
        boolean[] inPath = new boolean[match.length];
        while (u != -1 || v != -1) {
            if (u != -1) {
                if (inPath[u]) {
                    return u;
                }
                inPath[u] = true;
                u = base[parent[u]];
            }

            if (v != -1) {
                if (inPath[v]) {
                    return v;
                }
                inPath[v] = true;
                v = base[parent[v]];
            }
        }
        return -1;
    }

    private static void markBlossom(int u, int v, int newBase) {
        while (base[u] != newBase) {
            blossom[base[u]] = blossom[base[match[u]]] = true;
            base[u] = newBase;
            u = parent[match[u]];
        }
    }
}

public class Main {

    public static void main(String[] args) {
        int V = 6;
        EdmondsBlossom.Graph graph = new EdmondsBlossom.Graph(V);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 0);

        List<int[]> maxMatching = EdmondsBlossom.findMaxMatching(graph);

        System.out.println("Maximum Cardinality Matching:");
        for (int[] edge : maxMatching) {
            System.out.println(edge[0] + " - " + edge[1]);
        }
    }
}
