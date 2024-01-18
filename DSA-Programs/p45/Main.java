import java.util.*;

class EulerianPathCircuit {

    private int vertices;
    private List<Integer>[] adjacencyList;

    public EulerianPathCircuit(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int u, int v) {
        adjacencyList[u].add(v);
        adjacencyList[v].add(u);
    }

    private boolean isConnected() {
        boolean[] visited = new boolean[vertices];
        int nonZeroDegreeVertices = 0;
        int startVertex = 0;

        for (int i = 0; i < vertices; i++) {
            if (adjacencyList[i].size() > 0) {
                nonZeroDegreeVertices++;
                startVertex = i;
            }
        }

        dfs(startVertex, visited);

        for (int i = 0; i < vertices; i++) {
            if (!visited[i] && adjacencyList[i].size() > 0) {
                return false; // Graph is not connected
            }
        }

        return nonZeroDegreeVertices == 0 || (nonZeroDegreeVertices - countOddDegreeVertices()) == 0;
    }

    private int countOddDegreeVertices() {
        int count = 0;
        for (int i = 0; i < vertices; i++) {
            if (adjacencyList[i].size() % 2 != 0) {
                count++;
            }
        }
        return count;
    }

    private void dfs(int vertex, boolean[] visited) {
        visited[vertex] = true;
        for (int neighbor : adjacencyList[vertex]) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited);
            }
        }
    }

    private boolean isValidNextEdge(int u, int v) {
        int countU = 0;
        for (int neighbor : adjacencyList[u]) {
            if (neighbor != v) {
                countU++;
            }
        }

        if (countU == 1) {
            return true;
        }

        boolean[] visited = new boolean[vertices];
        int countV1 = countConnectedComponents(u, visited);

        removeEdge(u, v);

        visited = new boolean[vertices];
        int countV2 = countConnectedComponents(u, visited);

        addEdge(u, v);

        return (countV1 > countV2);
    }

    private int countConnectedComponents(int vertex, boolean[] visited) {
        int count = 0;
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                count++;
                dfs(i, visited);
            }
        }
        return count;
    }

    private void removeEdge(int u, int v) {
        adjacencyList[u].remove(Integer.valueOf(v));
        adjacencyList[v].remove(Integer.valueOf(u));
    }

    public void printEulerianPathCircuit() {
        if (!isConnected()) {
            System.out.println("Graph is not Eulerian.");
            return;
        }

        int oddDegreeCount = countOddDegreeVertices();
        int startVertex = (oddDegreeCount == 0) ? 0 : findStartVertex();

        Deque<Integer> path = new LinkedList<>();
        path.push(startVertex);
        int currentVertex = startVertex;

        while (!path.isEmpty()) {
            if (adjacencyList[currentVertex].size() > 0) {
                int nextVertex = adjacencyList[currentVertex].remove(0);
                if (isValidNextEdge(currentVertex, nextVertex)) {
                    path.push(nextVertex);
                    removeEdge(currentVertex, nextVertex);
                    currentVertex = nextVertex;
                }
            } else {
                System.out.print(path.pop() + " ");
                currentVertex = path.isEmpty() ? -1 : path.peek();
            }
        }

        System.out.println();
    }

    private int findStartVertex() {
        for (int i = 0; i < vertices; i++) {
            if (adjacencyList[i].size() % 2 != 0) {
                return i;
            }
        }
        return 0;
    }


}


public class Main {

    public static void main(String[] args) {
        EulerianPathCircuit eulerianGraph = new EulerianPathCircuit(5);
        eulerianGraph.addEdge(0, 1);
        eulerianGraph.addEdge(0, 2);
        eulerianGraph.addEdge(1, 2);
        eulerianGraph.addEdge(2, 3);
        eulerianGraph.addEdge(3, 4);
        eulerianGraph.addEdge(4, 2);

        System.out.println("Eulerian Path or Circuit:");
        eulerianGraph.printEulerianPathCircuit();
    }
    
}