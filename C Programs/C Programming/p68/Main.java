class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); 
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootX] = rootY;
                rank[rootY]++;
            }
        }
    }

    public boolean isConnected(int x, int y) {
        return find(x) == find(y);
    }
}

public class Main {

    public static void main(String[] args) {
        int size = 10;
        UnionFind uf = new UnionFind(size);

        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);

        System.out.println("Is 1 connected to 0? " + uf.isConnected(1, 0));
        System.out.println("Is 3 connected to 2? " + uf.isConnected(3, 2));
        System.out.println("Is 5 connected to 4? " + uf.isConnected(5, 4));

        uf.union(0, 9);

        System.out.println("Is 1 connected to 9 after union? " + uf.isConnected(1, 9));
    }
}
