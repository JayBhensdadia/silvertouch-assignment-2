import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Main {

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = buildGraph(equations, values);
        double[] results = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String source = queries.get(i).get(0);
            String destination = queries.get(i).get(1);
            Set<String> visited = new HashSet<>();
            results[i] = dfs(graph, source, destination, visited);
        }

        return results;
    }

    private static double dfs(Map<String, Map<String, Double>> graph, String current, String destination, Set<String> visited) {
        if (!graph.containsKey(current) || !graph.containsKey(destination)) {
            return -1.0;
        }

        if (current.equals(destination)) {
            return 1.0;
        }

        visited.add(current);

        Map<String, Double> neighbors = graph.get(current);
        for (Map.Entry<String, Double> neighborEntry : neighbors.entrySet()) {
            String neighbor = neighborEntry.getKey();
            if (!visited.contains(neighbor)) {
                double result = dfs(graph, neighbor, destination, visited);
                if (result != -1.0) {
                    return result * neighborEntry.getValue();
                }
            }
        }

        visited.remove(current);

        return -1.0;
    }

    private static Map<String, Map<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String source = equations.get(i).get(0);
            String destination = equations.get(i).get(1);
            double value = values[i];

            graph.putIfAbsent(source, new HashMap<>());
            graph.putIfAbsent(destination, new HashMap<>());

            graph.get(source).put(destination, value);
            graph.get(destination).put(source, 1.0 / value);
        }

        return graph;
    }

    public static void main(String[] args) {
        List<List<String>> equations = List.of(
                List.of("a", "b"),
                List.of("b", "c")
        );
        double[] values = {2.0, 3.0};
        List<List<String>> queries = List.of(
                List.of("a", "c"),
                List.of("b", "a"),
                List.of("a", "e"),
                List.of("a", "a"),
                List.of("x", "x")
        );

        double[] results = calcEquation(equations, values, queries);

        System.out.println("Results:");
        for (double result : results) {
            System.out.print(result + " ");
        }
    }
}
