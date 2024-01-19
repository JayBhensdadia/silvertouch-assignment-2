import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class Subset {
    int parent, rank;

    public Subset(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

public class Main {

    private static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    private static void union(Subset[] subsets, int x, int y) {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            subsets[yRoot].parent = xRoot;
        } else {
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }

    public static List<Edge> boruvkaMST(List<Edge> edges, int numVertices) {
        List<Edge> result = new ArrayList<>();

        Subset[] subsets = new Subset[numVertices];
        for (int i = 0; i < numVertices; i++) {
            subsets[i] = new Subset(i, 0);
        }

        while (result.size() < numVertices - 1) {
            int[] cheapest = new int[numVertices];
            Arrays.fill(cheapest, -1);

            for (Edge edge : edges) {
                int set1 = find(subsets, edge.src);
                int set2 = find(subsets, edge.dest);

                if (set1 != set2) {
                    if (cheapest[set1] == -1 || edge.weight < edges.get(cheapest[set1]).weight) {
                        cheapest[set1] = edge.dest;
                    }

                    if (cheapest[set2] == -1 || edge.weight < edges.get(cheapest[set2]).weight) {
                        cheapest[set2] = edge.src;
                    }
                }
            }

            for (int i = 0; i < numVertices; i++) {
                if (cheapest[i] != -1) {
                    int set1 = find(subsets, i);
                    int set2 = find(subsets, cheapest[i]);

                    if (set1 != set2) {
                        result.add(new Edge(i, cheapest[i], edges.get(i).weight));
                        union(subsets, set1, set2);
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int numVertices = 4;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        List<Edge> mst = boruvkaMST(edges, numVertices);

        System.out.println("Minimum Spanning Tree edges:");
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
        }
    }
}
