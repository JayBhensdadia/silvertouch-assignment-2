import java.util.*;

public class HopcroftKarp {

    private int[] pairU, pairV, dist;
    private List<Integer>[] graph;
    private int NIL, INF;

    public HopcroftKarp(int u, int v) {
        graph = new ArrayList[u];
        for (int i = 0; i < u; i++) {
            graph[i] = new ArrayList<>();
        }

        pairU = new int[u];
        pairV = new int[v];
        dist = new int[u + v + 1]; // Corrected initialization

        NIL = u + v;
        INF = Integer.MAX_VALUE;
    }

    public void addEdge(int u, int v) {
        graph[u].add(v);
    }

    private boolean bfs() {
        Queue<Integer> queue = new LinkedList<>();
        for (int u = 0; u < pairU.length; u++) {
            if (pairU[u] == NIL) {
                dist[u] = 0;
                queue.add(u);
            } else {
                dist[u] = INF;
            }
        }

        dist[NIL] = INF;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (dist[u] < dist[NIL]) {
                for (int v : graph[u]) {
                    if (dist[pairV[v]] == INF) {
                        dist[pairV[v]] = dist[u] + 1;
                        queue.add(pairV[v]);
                    }
                }
            }
        }

        return dist[NIL] != INF;
    }

    private boolean dfs(int u) {
        if (u != NIL) {
            for (int v : graph[u]) {
                if (dist[pairV[v]] == dist[u] + 1 && dfs(pairV[v])) {
                    pairV[v] = u;
                    pairU[u] = v;
                    return true;
                }
            }

            dist[u] = INF;
            return false;
        }
        return true;
    }

    public int maxCardinalityMatching() {
        int matching = 0;

        while (bfs()) {
            for (int u = 0; u < pairU.length; u++) {
                if (pairU[u] == NIL && dfs(u)) {
                    matching++;
                }
            }
        }

        return matching;
    }

    public static void main(String[] args) {
        int u = 4; // Number of vertices in the left set
        int v = 4; // Number of vertices in the right set

        HopcroftKarp hopcroftKarp = new HopcroftKarp(u, v);

        // Add edges to the bipartite graph
        hopcroftKarp.addEdge(0, 1);
        hopcroftKarp.addEdge(0, 2);
        hopcroftKarp.addEdge(1, 2);
        hopcroftKarp.addEdge(2, 0);
        hopcroftKarp.addEdge(2, 3);
        hopcroftKarp.addEdge(3, 3);

        int maxCardinalityMatching = hopcroftKarp.maxCardinalityMatching();
        System.out.println("Maximum Cardinality Matching: " + maxCardinalityMatching);
    }
}
