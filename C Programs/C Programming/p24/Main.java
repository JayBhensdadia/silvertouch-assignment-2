import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Edge {
    int start;
    int end;
    int capacity;
    int flow;

    public Edge(int start, int end, int capacity) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.flow = 0;
    }
}

public class Main {
    private int numVertices;
    private ArrayList<ArrayList<Edge>> graph;

    public Main(int numVertices) {
        this.numVertices = numVertices;
        this.graph = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.graph.add(new ArrayList<>());
        }
    }

    public void addEdge(int start, int end, int capacity) {
        Edge forwardEdge = new Edge(start, end, capacity);
        Edge backwardEdge = new Edge(end, start, 0);
        forwardEdge.flow = 0;
        backwardEdge.flow = 0;
        graph.get(start).add(forwardEdge);
        graph.get(end).add(backwardEdge);
    }

    public int maxFlow(int source, int sink) {
        int maxFlow = 0;
        int[] parent = new int[numVertices];

        while (hasAugmentingPath(source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                Edge edge = getEdge(u, v);
                pathFlow = Math.min(pathFlow, edge.capacity - edge.flow);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                Edge forwardEdge = getEdge(u, v);
                Edge backwardEdge = getEdge(v, u);

                forwardEdge.flow += pathFlow;
                backwardEdge.flow -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private boolean hasAugmentingPath(int source, int sink, int[] parent) {
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (Edge edge : graph.get(u)) {
                int v = edge.end;

                if (!visited[v] && edge.capacity - edge.flow > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink];
    }

    private Edge getEdge(int start, int end) {
        for (Edge edge : graph.get(start)) {
            if (edge.end == end) {
                return edge;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Main fordFulkerson = new Main(6);

        fordFulkerson.addEdge(0, 1, 16);
        fordFulkerson.addEdge(0, 2, 13);
        fordFulkerson.addEdge(1, 2, 10);
        fordFulkerson.addEdge(1, 3, 12);
        fordFulkerson.addEdge(2, 1, 4);
        fordFulkerson.addEdge(2, 4, 14);
        fordFulkerson.addEdge(3, 2, 9);
        fordFulkerson.addEdge(3, 5, 20);
        fordFulkerson.addEdge(4, 3, 7);
        fordFulkerson.addEdge(4, 5, 4);

        int source = 0;
        int sink = 5;

        int maxFlow = fordFulkerson.maxFlow(source, sink);
        System.out.println("Maximum Flow: " + maxFlow);
    }
}
