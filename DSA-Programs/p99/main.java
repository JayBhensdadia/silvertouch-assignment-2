package p99;
import java.util.*;

class Graph {
    private int V;
    private LinkedList<Integer>[] adjList;

    public Graph(int V) {
        this.V = V;
        adjList = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int u, int v) {
        adjList[u].add(v);
        adjList[v].add(u);
    }

    public void eulerianCircuit() {
        Stack<Integer> circuit = new Stack<>();
        List<Integer> path = new ArrayList<>();

        int startVertex = 0; // Starting vertex for the circuit

        circuit.push(startVertex);
        int currentVertex = startVertex;

        while (!circuit.isEmpty()) {
            if (adjList[currentVertex].size() > 0) {
                circuit.push(currentVertex);
                int nextVertex = adjList[currentVertex].removeFirst();
                removeEdge(currentVertex, nextVertex);
                currentVertex = nextVertex;
            } else {
                path.add(currentVertex);
                currentVertex = circuit.pop();
            }
        }

        System.out.println("Eulerian Circuit:");
        for (int vertex : path) {
            System.out.print(vertex + " ");
        }
    }

    private void removeEdge(int u, int v) {
        adjList[u].remove((Integer) v);
        adjList[v].remove((Integer) u);
    }
}

class EulerianCircuitEnumeration {
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        graph.eulerianCircuit();
    }
}
