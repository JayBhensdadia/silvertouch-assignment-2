import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Main {

    public static int maximalNetworkRank(int n, int[][] roads) {
        Map<Integer, List<Integer>> graph = buildGraph(n, roads);
        int maxRank = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int rank = graph.get(i).size() + graph.get(j).size();

                
                if (graph.get(i).contains(j) || graph.get(j).contains(i)) {
                    rank--;
                }

                maxRank = Math.max(maxRank, rank);
            }
        }

        return maxRank;
    }

    private static Map<Integer, List<Integer>> buildGraph(int n, int[][] roads) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int[] road : roads) {
            int city1 = road[0];
            int city2 = road[1];

            graph.get(city1).add(city2);
            graph.get(city2).add(city1);
        }

        return graph;
    }

    public static void main(String[] args) {
        
        int n = 4;
        int[][] roads = {{0, 1}, {0, 3}, {1, 2}, {1, 3}};
        int result = maximalNetworkRank(n, roads);
        System.out.println("Maximal Network Rank: " + result);
    }
}
