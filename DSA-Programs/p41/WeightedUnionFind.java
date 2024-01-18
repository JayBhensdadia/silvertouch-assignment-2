class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (x != parent[x]) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}

public class WeightedUnionFind {
    public static void main(String[] args) {
        int n = 6;
        UnionFind uf = new UnionFind(n);

        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(4, 5);
        uf.union(2, 5);

        System.out.println(uf.find(0) == uf.find(2)); 
        System.out.println(uf.find(3) == uf.find(5)); 
    }
}
