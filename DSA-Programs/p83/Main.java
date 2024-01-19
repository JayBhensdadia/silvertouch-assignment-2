

import java.util.ArrayList;
import java.util.List;

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Edge {
    int u, v;

    public Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }
}

public class Main {
    private List<Point> points;
    private List<Edge> edges;

    public Main(List<Point> points) {
        this.points = points;
        this.edges = new ArrayList<>();
    }

    public void constructYaoGraph(double radius) {
        int n = points.size();

        for (int i = 0; i < n; i++) {
            List<Integer> neighbors = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if (i != j && distance(points.get(i), points.get(j)) <= radius) {
                    neighbors.add(j);
                }
            }

            buildYaoSubgraph(i, neighbors);
        }
    }

    private void buildYaoSubgraph(int center, List<Integer> neighbors) {
        int n = neighbors.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.add(new Edge(neighbors.get(i), neighbors.get(j)));
            }
        }
    }

    private double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 3));

        Main yaoGraph = new Main(points);
        yaoGraph.constructYaoGraph(2.0);

        List<Edge> edges = yaoGraph.getEdges();

        for (Edge edge : edges) {
            System.out.println("(" + edge.u + ", " + edge.v + ")");
        }
    }
}
