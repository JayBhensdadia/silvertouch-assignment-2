import java.util.ArrayList;
import java.util.List;

class HeavyLightDecomposition {
    private int[] parent;
    private int[] depth;
    private int[] size;
    private int[] chainHead;
    private int[] chainIndex;
    private int[] position;
    private int chainCount;
    private int positionCount;
    private List<List<Integer>> tree;

    private class SegmentTree {
        private int[] tree;

        public SegmentTree(int size) {
            tree = new int[4 * size];
        }

        public void build(int[] arr, int node, int start, int end) {
            if (start > end) {
                return; 
            }

            if (start == end) {
                tree[node] = arr[start];
                return; 
            }

            int mid = (start + end) / 2;
            build(arr, 2 * node, start, mid);
            build(arr, 2 * node + 1, mid + 1, end);
            tree[node] = tree[2 * node] + tree[2 * node + 1];
        }

        public int query(int node, int start, int end, int left, int right) {
            if (right < start || end < left) {
                return 0;
            }

            if (left <= start && end <= right) {
                return tree[node];
            }

            int mid = (start + end) / 2;
            int leftSum = query(2 * node, start, mid, left, right);
            int rightSum = query(2 * node + 1, mid + 1, end, left, right);

            return leftSum + rightSum;
        }

        
        public void update(int node, int start, int end, int pos, int value) {
            if (start == end) {
                tree[node] = value;
            } else {
                int mid = (start + end) / 2;
                if (pos <= mid) {
                    update(2 * node, start, mid, pos, value);
                } else {
                    update(2 * node + 1, mid + 1, end, pos, value);
                }
                tree[node] = tree[2 * node] + tree[2 * node + 1];
            }
        }
    }

    private int[] values;
    private SegmentTree segmentTree;

    public HeavyLightDecomposition(int n) {
        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        chainHead = new int[n];
        chainIndex = new int[n];
        position = new int[n];
        chainCount = 0;
        positionCount = 0;
        tree = new ArrayList<>();
        values = new int[n];
        segmentTree = new SegmentTree(n);

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        tree.get(u).add(v);
        tree.get(v).add(u);
    }

    public void decompose(int root) {
        dfs(root, -1, 0);

        for (int i = 0; i < parent.length; i++) {
            if (chainHead[i] == -1) {
                chainHead[i] = i;
                decomposeChain(i, chainCount++);
            }
        }

        buildSegmentTree();
    }

    private int dfs(int node, int par, int d) {
        parent[node] = par;
        depth[node] = d;
        size[node] = 1;

        for (int child : tree.get(node)) {
            if (child != par) {
                size[node] += dfs(child, node, d + 1);
            }
        }

        return size[node];
    }

    private void decomposeChain(int node, int chain) {
        chainIndex[node] = chain;
        position[node] = positionCount++;
        int heavyChild = -1;
        int maxChildSize = -1;

        for (int child : tree.get(node)) {
            if (child != parent[node] && size[child] > maxChildSize) {
                maxChildSize = size[child];
                heavyChild = child;
            }
        }

        if (heavyChild != -1) {
            decomposeChain(heavyChild, chain);
        }

        for (int child : tree.get(node)) {
            if (child != parent[node] && child != heavyChild) {
                decomposeChain(child, chain + 1);
            }
        }
    }

    public int query(int u, int v) {
        int result = 0;

        while (chainIndex[u] != chainIndex[v]) {
            if (depth[chainHead[u]] < depth[chainHead[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            int chainU = chainIndex[u];
            result += querySegmentTree(position[chainHead[u]], position[u]);
            u = parent[chainHead[u]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        result += querySegmentTree(position[u], position[v]);

        return result;
    }

    private int querySegmentTree(int u, int v) {
        if (u > v) {
            return 0;
        }

        return segmentTree.query(1, 0, positionCount - 1, u, v);
    }

    public void buildSegmentTree() {
        segmentTree.build(values, 1, 0, positionCount - 1);
    }

    public void updateSegmentTree(int node, int value) {
        int pos = position[node];
        segmentTree.update(1, 0, positionCount - 1, pos, value);
    }
}

public class Main {
    public static void main(String[] args) {
        int n = 7;
        HeavyLightDecomposition hld = new HeavyLightDecomposition(n);
    
        hld.addEdge(0, 1);
        hld.addEdge(1, 2);
        hld.addEdge(1, 3);
        hld.addEdge(2, 4);
        hld.addEdge(2, 5);
        hld.addEdge(3, 6);
    
        hld.decompose(0);
    
        
        int result1 = hld.query(2, 5);
        System.out.println("Query result 1 (path from 2 to 5): " + result1);
    
        
        hld.updateSegmentTree(4, 3);
    
        
        int result2 = hld.query(4, 6);
        System.out.println("Query result 2 (path from 4 to 6): " + result2);
    }
    
}
