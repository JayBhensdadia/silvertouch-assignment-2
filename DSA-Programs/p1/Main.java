import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Edge implements Comparable<Edge> {
    int u, v, weight;

    public Edge(int u, int v, int weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
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

public class Main {

    public static List<Edge> kruskal(List<Edge> edges, int numVertices) {
        Collections.sort(edges);

        UnionFind uf = new UnionFind(numVertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            if (uf.find(edge.u) != uf.find(edge.v)) {
                uf.union(edge.u, edge.v);
                mst.add(edge);
            }
        }

        return mst;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 2));
        edges.add(new Edge(0, 2, 4));
        edges.add(new Edge(1, 2, 1));
        edges.add(new Edge(1, 3, 7));
        edges.add(new Edge(2, 3, 3));

        int numVertices = 4;

        List<Edge> minimumSpanningTree = kruskal(edges, numVertices);

        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge.u + " - " + edge.v + ": " + edge.weight);
        }
    }
}
