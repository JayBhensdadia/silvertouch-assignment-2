import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    private int n;
    private List<List<Integer>> graph;
    private List<List<Integer>> dominators;

    public Main(int n) {
        this.n = n;
        this.graph = new ArrayList<>(n);
        this.dominators = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            dominators.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to) {
        graph.get(from).add(to);
    }

    public List<Integer> findDominators(int startNode) {
        List<Integer> dominatorsList = new ArrayList<>();
        dominatorsList.add(startNode);

        for (int i = 0; i < n; i++) {
            dominators.get(i).clear();
            dominators.get(i).add(i);
        }

        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        dfs(startNode, visited, stack);

        for (int i = 0; i < n; i++) {
            visited[i] = false;
        }

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                visited[node] = true;

                for (int pred : graph.get(node)) {
                    intersect(dominatorsList, dominatorsList, dominators.get(pred));
                }

                for (int succ : graph.get(node)) {
                    stack.push(succ);
                }
            }
        }

        return dominatorsList;
    }

    private void dfs(int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;

        for (int succ : graph.get(node)) {
            if (!visited[succ]) {
                dfs(succ, visited, stack);
            }
        }

        stack.push(node);
    }

    private void intersect(List<Integer> result, List<Integer> set1, List<Integer> set2) {
        List<Integer> intersection = new ArrayList<>(set1);
        intersection.retainAll(set2);
        result.clear();
        result.addAll(intersection);
    }

    public static void main(String[] args) {
        int n = 7;
        Main dominatorsFinder = new Main(n);

        dominatorsFinder.addEdge(0, 1);
        dominatorsFinder.addEdge(1, 2);
        dominatorsFinder.addEdge(2, 3);
        dominatorsFinder.addEdge(2, 4);
        dominatorsFinder.addEdge(3, 5);
        dominatorsFinder.addEdge(4, 5);
        dominatorsFinder.addEdge(5, 6);

        int startNode = 0;
        List<Integer> dominatorsList = dominatorsFinder.findDominators(startNode);

        System.out.println("Dominators for node " + startNode + ": " + dominatorsList);
    }
}
