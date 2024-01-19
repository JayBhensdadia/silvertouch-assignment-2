

import java.util.ArrayList;
import java.util.List;

class EulerTourTree {
    private static final int MAXN = 100005;

    private int[] parent;
    private int[] depth;
    private int[] tin;
    private int[] tout;
    private int[] eulerTour;
    private int eulerIndex;
    private List<Integer>[] adj;

    public EulerTourTree(int n) {
        parent = new int[MAXN];
        depth = new int[MAXN];
        tin = new int[MAXN];
        tout = new int[MAXN];
        eulerTour = new int[2 * MAXN];
        adj = new ArrayList[MAXN];

        for (int i = 0; i < MAXN; i++) {
            adj[i] = new ArrayList<>();
        }

        eulerIndex = 0;
        initialize(n);
    }

    private void initialize(int n) {
        dfs(1, 0, 0);
        buildEulerTour(1, 0);
    }

    private void dfs(int u, int p, int d) {
        parent[u] = p;
        depth[u] = d;
        tin[u] = eulerIndex;
        eulerTour[eulerIndex++] = u;

        for (int v : adj[u]) {
            if (v != p) {
                dfs(v, u, d + 1);
                eulerTour[eulerIndex++] = u;
            }
        }

        tout[u] = eulerIndex - 1;
    }

    private void buildEulerTour(int u, int p) {
        for (int v : adj[u]) {
            if (v != p) {
                buildEulerTour(v, u);
            }
        }
    }

    public int lca(int u, int v) {
        int l = tin[u];
        int r = tin[v];
        if (l > r) {
            int temp = l;
            l = r;
            r = temp;
        }

        int lca = eulerTour[l];
        for (int i = l + 1; i <= r; i++) {
            if (depth[eulerTour[i]] < depth[lca]) {
                lca = eulerTour[i];
            }
        }

        return lca;
    }

    public static void main(String[] args) {
        EulerTourTree tree = new EulerTourTree(10);

        tree.adj[1].add(2);
        tree.adj[2].add(1);
        tree.adj[1].add(3);
        tree.adj[3].add(1);
        tree.adj[2].add(4);
        tree.adj[4].add(2);
        tree.adj[2].add(5);
        tree.adj[5].add(2);
        tree.adj[3].add(6);
        tree.adj[6].add(3);
        tree.adj[3].add(7);
        tree.adj[7].add(3);
        tree.adj[4].add(8);
        tree.adj[8].add(4);
        tree.adj[4].add(9);
        tree.adj[9].add(4);
        tree.adj[5].add(10);
        tree.adj[10].add(5);

        System.out.println(tree.lca(8, 10));
    }
}

