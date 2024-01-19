import java.util.*;

class Main {

    static class Edge {
        int destination;
        int weight;

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Pair implements Comparable<Pair> {
        int city;
        int distance;

        public Pair(int city, int distance) {
            this.city = city;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static int findTheCity(int n, int[][] edges, int distanceThreshold) {
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int src = edge[0];
            int dest = edge[1];
            int weight = edge[2];

            graph.get(src).add(new Edge(dest, weight));
            graph.get(dest).add(new Edge(src, weight));
        }

        int resultCity = -1;
        int smallestNeighbors = n;

        for (int i = 0; i < n; i++) {
            int[] distances = dijkstra(graph, i, n);

            int count = 0;
            for (int dist : distances) {
                if (dist > 0 && dist <= distanceThreshold) {
                    count++;
                }
            }

            if (count <= smallestNeighbors) {
                smallestNeighbors = count;
                resultCity = i;
            }
        }

        return resultCity;
    }

    private static int[] dijkstra(List<List<Edge>> graph, int start, int n) {
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Pair> minHeap = new PriorityQueue<>();
        minHeap.add(new Pair(start, 0));

        while (!minHeap.isEmpty()) {
            Pair current = minHeap.poll();

            if (current.distance > distances[current.city]) {
                continue;
            }

            for (Edge neighbor : graph.get(current.city)) {
                int newDistance = current.distance + neighbor.weight;

                if (newDistance < distances[neighbor.destination]) {
                    distances[neighbor.destination] = newDistance;
                    minHeap.add(new Pair(neighbor.destination, newDistance));
                }
            }
        }

        return distances;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] edges = {
                {0, 2, 4},
                {2, 3, 3},
                {0, 3, 7},
                {0, 4, 5}
        };
        int distanceThreshold = 5;

        int resultCity = findTheCity(n, edges, distanceThreshold);

        System.out.println("City with the smallest number of neighbors at a threshold distance: " + resultCity);
    }
}
