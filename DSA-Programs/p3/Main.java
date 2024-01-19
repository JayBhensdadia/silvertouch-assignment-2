import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph {
    private final int vertices;
    private final List<List<Integer>> adjList;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjList.get(source).add(destination);
    }

    public boolean isCyclic() {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();

        for (int i = 0; i < vertices; i++) {
            if (!visited.contains(i) && isCyclicUtil(i, visited, recursionStack)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCyclicUtil(int vertex, Set<Integer> visited, Set<Integer> recursionStack) {
        visited.add(vertex);
        recursionStack.add(vertex);

        for (int neighbor : adjList.get(vertex)) {
            if (!visited.contains(neighbor)) {
                if (isCyclicUtil(neighbor, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                return true;
            }
        }

        recursionStack.remove(vertex);
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(4);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);

        if (graph.isCyclic()) {
            System.out.println("The graph contains a cycle.");
        } else {
            System.out.println("The graph does not contain a cycle.");
        }
    }
}

