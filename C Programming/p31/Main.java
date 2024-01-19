import java.util.Arrays;

public class Main {
    private int leftSetSize;
    private int rightSetSize;
    private boolean[][] graph;
    private int[] matching;

    public Main(int leftSetSize, int rightSetSize, boolean[][] graph) {
        this.leftSetSize = leftSetSize;
        this.rightSetSize = rightSetSize;
        this.graph = graph;
        this.matching = new int[rightSetSize];
        Arrays.fill(matching, -1);
    }

    public int findMaximumMatching() {
        int maxMatching = 0;

        for (int i = 0; i < leftSetSize; i++) {
            boolean[] visited = new boolean[rightSetSize];
            if (augmentPath(i, visited)) {
                maxMatching++;
            }
        }

        return maxMatching;
    }

    private boolean augmentPath(int u, boolean[] visited) {
        for (int v = 0; v < rightSetSize; v++) {
            if (graph[u][v] && !visited[v]) {
                visited[v] = true;

                if (matching[v] == -1 || augmentPath(matching[v], visited)) {
                    matching[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int leftSetSize = 3;
        int rightSetSize = 3;
        boolean[][] graph = {
                {false, true, false},
                {true, false, true},
                {false, true, false}
        };

        Main bipartiteMatching = new Main(leftSetSize, rightSetSize, graph);
        int maxMatching = bipartiteMatching.findMaximumMatching();

        System.out.println("Maximum Bipartite Matching: " + maxMatching);
    }
}
