import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    private int vertices;
    private List<List<Integer>> adjList;
    private int time;
    private int[] disc;
    private int[] low;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> stronglyConnectedComponents;

    public Main(int vertices) {
        this.vertices = vertices;
        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            this.adjList.add(new ArrayList<>());
        }
        this.time = 0;
        this.disc = new int[vertices];
        this.low = new int[vertices];
        this.onStack = new boolean[vertices];
        this.stack = new Stack<>();
        this.stronglyConnectedComponents = new ArrayList<>();
    }

    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
    }

    public List<List<Integer>> findStronglyConnectedComponents() {
        for (int i = 0; i < vertices; i++) {
            if (disc[i] == 0) {
                tarjanDFS(i);
            }
        }
        return stronglyConnectedComponents;
    }

    private void tarjanDFS(int u) {
        disc[u] = low[u] = ++time;
        stack.push(u);
        onStack[u] = true;

        for (int v : adjList.get(u)) {
            if (disc[v] == 0) {
                tarjanDFS(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> component = new ArrayList<>();
            int popped;
            do {
                popped = stack.pop();
                onStack[popped] = false;
                component.add(popped);
            } while (popped != u);
            stronglyConnectedComponents.add(component);
        }
    }

    public static void main(String[] args) {
        Main tarjan = new Main(8);
        tarjan.addEdge(0, 1);
        tarjan.addEdge(1, 2);
        tarjan.addEdge(1, 3);
        tarjan.addEdge(3, 4);
        tarjan.addEdge(4, 0);
        tarjan.addEdge(4, 5);
        tarjan.addEdge(5, 6);
        tarjan.addEdge(6, 4);
        tarjan.addEdge(6, 7);

        List<List<Integer>> stronglyConnectedComponents = tarjan.findStronglyConnectedComponents();

        System.out.println("Strongly Connected Components:");
        for (List<Integer> component : stronglyConnectedComponents) {
            System.out.println(component);
        }
    }
}
