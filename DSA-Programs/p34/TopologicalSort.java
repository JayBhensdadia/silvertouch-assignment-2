import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Graph {
    private int vertices;
    private List<List<Integer>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new LinkedList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
    }

    public List<Integer> topologicalSort() {
        LinkedList<Integer> result = new LinkedList<>();
        boolean[] visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, result);
            }
        }

        return result;
    }

    private void topologicalSortUtil(int vertex, boolean[] visited, LinkedList<Integer> result) {
        visited[vertex] = true;

        for (int adjacentVertex : adjacencyList.get(vertex)) {
            if (!visited[adjacentVertex]) {
                topologicalSortUtil(adjacentVertex, visited, result);
            }
        }

        result.addFirst(vertex);
    }
}

public class TopologicalSort {
    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);

        List<Integer> result = graph.topologicalSort();

        System.out.println("Topological Sorting Order:");
        for (int vertex : result) {
            System.out.print(vertex + " ");
        }
    }
}
