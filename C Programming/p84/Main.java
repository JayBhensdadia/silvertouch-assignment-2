package C.p84;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

class ParallelBFS extends RecursiveAction {
    private static final int SEQUENTIAL_THRESHOLD = 5; // Adjust as needed
    private final Graph graph;
    private final int[] distances;
    private final int startNode;

    public ParallelBFS(Graph graph, int[] distances, int startNode) {
        this.graph = graph;
        this.distances = distances;
        this.startNode = startNode;
    }

    @Override
    protected void compute() {
        bfs(startNode);
    }

    private void bfs(int node) {
        List<ParallelBFS> subtasks = new ArrayList<>();

        for (int neighbor : graph.getNeighbors(node)) {
            if (distances[neighbor] == -1) {
                distances[neighbor] = distances[node] + 1;
                subtasks.add(new ParallelBFS(graph, distances, neighbor));
            }
        }

        if (!subtasks.isEmpty()) {
            invokeAll(subtasks);
        }
    }

    public static void parallelBFS(Graph graph, int[] distances, int startNode) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        distances[startNode] = 0;
        forkJoinPool.invoke(new ParallelBFS(graph, distances, startNode));
    }

    public static void main(String[] args) {
        int numNodes = 7;
        Graph graph = new Graph(numNodes);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);

        int[] distances = new int[numNodes];
        for (int i = 0; i < numNodes; i++) {
            distances[i] = -1;
        }

        parallelBFS(graph, distances, 0);

        System.out.println("Distances from node 0: " + java.util.Arrays.toString(distances));
    }
}

class Graph {
    private final int numNodes;
    private final List<List<Integer>> adjacencyList;

    public Graph(int numNodes) {
        this.numNodes = numNodes;
        this.adjacencyList = new ArrayList<>(numNodes);
        for (int i = 0; i < numNodes; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
    }

    public List<Integer> getNeighbors(int node) {
        return adjacencyList.get(node);
    }
}

public class Main {
}
