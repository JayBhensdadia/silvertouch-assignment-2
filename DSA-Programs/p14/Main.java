class UnionFind {
    int[] parent;
    int[] rank;

    UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0; 
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

}

class Main{
    public static void main(String[] args) {
       UnionFind uf = new UnionFind(10);
       uf.union(1, 2);
       uf.union(4, 5);
       uf.union(7, 8);
       uf.union(2, 8); 
       System.out.println(uf.find(1)); 
       System.out.println(uf.find(4));
    }
}
