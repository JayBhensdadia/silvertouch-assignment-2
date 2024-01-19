

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class UnionFind {
    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;

    public UnionFind() {
        this.parent = new HashMap<>();
        this.rank = new HashMap<>();
    }

    public void makeSet(int vertex) {
        if (!parent.containsKey(vertex)) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
    }

    public int find(int vertex) {
        if (vertex != parent.get(vertex)) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    public void union(int vertex1, int vertex2) {
        int root1 = find(vertex1);
        int root2 = find(vertex2);

        if (root1 != root2) {
            if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
}

public class Main {
    private UnionFind unionFind;
    private Set<Integer> vertices;

    public Main() {
        this.unionFind = new UnionFind();
        this.vertices = new HashSet<>();
    }

    public void addVertex(int vertex) {
        unionFind.makeSet(vertex);
        vertices.add(vertex);
    }

    public void addEdge(int vertex1, int vertex2) {
        if (!vertices.contains(vertex1) || !vertices.contains(vertex2)) {
            throw new IllegalArgumentException("Vertices must be added first");
        }
        unionFind.union(vertex1, vertex2);
    }

    public void removeEdge(int vertex1, int vertex2) {
        if (!vertices.contains(vertex1) || !vertices.contains(vertex2)) {
            throw new IllegalArgumentException("Vertices must be added first");
        }

        int root1 = unionFind.find(vertex1);
        int root2 = unionFind.find(vertex2);

        if (root1 != root2) {
            throw new IllegalArgumentException("The edge does not exist");
        }
    }

    public boolean isConnected(int vertex1, int vertex2) {
        if (!vertices.contains(vertex1) || !vertices.contains(vertex2)) {
            throw new IllegalArgumentException("Vertices must be added first");
        }
        return unionFind.find(vertex1) == unionFind.find(vertex2);
    }

    public static void main(String[] args) {
        Main dynamicConnectivity = new Main();

        dynamicConnectivity.addVertex(1);
        dynamicConnectivity.addVertex(2);
        dynamicConnectivity.addVertex(3);

        dynamicConnectivity.addEdge(1, 2);
        dynamicConnectivity.addEdge(2, 3);

        System.out.println(dynamicConnectivity.isConnected(1, 3));

        dynamicConnectivity.removeEdge(1, 2);

        System.out.println(dynamicConnectivity.isConnected(1, 3)); 
    }
}
