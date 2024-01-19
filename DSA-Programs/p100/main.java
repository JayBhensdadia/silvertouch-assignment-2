package p100;

import java.util.*;

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Triangle {
    Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}

class TriangulationAlgorithm {
    private List<Point> polygon;

    public TriangulationAlgorithm(List<Point> polygon) {
        this.polygon = polygon;
    }

    public List<Triangle> triangulate() {
        List<Triangle> triangles = new ArrayList<>();
        List<Point> vertices = new ArrayList<>(polygon);

        while (vertices.size() > 3) {
            int n = vertices.size();
            boolean[] earVertices = new boolean[n];

            for (int i = 0; i < n; i++) {
                int prev = (i - 1 + n) % n;
                int next = (i + 1) % n;

                Point p1 = vertices.get(prev);
                Point p2 = vertices.get(i);
                Point p3 = vertices.get(next);

                if (isEarVertex(p1, p2, p3, vertices)) {
                    earVertices[i] = true;
                }
            }

            int earIndex = findEarVertex(earVertices);
            int prevIndex = (earIndex - 1 + n) % n;
            int nextIndex = (earIndex + 1) % n;

            triangles.add(new Triangle(vertices.get(prevIndex), vertices.get(earIndex), vertices.get(nextIndex)));

            vertices.remove(earIndex);
        }

        triangles.add(new Triangle(vertices.get(0), vertices.get(1), vertices.get(2)));
        return triangles;
    }

    private boolean isEarVertex(Point p1, Point p2, Point p3, List<Point> vertices) {
        for (Point vertex : vertices) {
            if (vertex.equals(p1) || vertex.equals(p2) || vertex.equals(p3)) {
                continue;
            }

            if (isPointInsideTriangle(p1, p2, p3, vertex)) {
                return false;
            }
        }

        return true;
    }

    private boolean isPointInsideTriangle(Point p1, Point p2, Point p3, Point testPoint) {
        double areaOriginal = Math.abs((p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x));
        double area1 = Math.abs((p1.x - testPoint.x) * (p2.y - testPoint.y) - (p1.y - testPoint.y) * (p2.x - testPoint.x));
        double area2 = Math.abs((p2.x - testPoint.x) * (p3.y - testPoint.y) - (p2.y - testPoint.y) * (p3.x - testPoint.x));
        double area3 = Math.abs((p3.x - testPoint.x) * (p1.y - testPoint.y) - (p3.y - testPoint.y) * (p1.x - testPoint.x));

        double sumOfAreas = area1 + area2 + area3;

        return Math.abs(areaOriginal - sumOfAreas) < 1e-6; // Check if the areas are almost equal
    }

    private int findEarVertex(boolean[] earVertices) {
        for (int i = 0; i < earVertices.length; i++) {
            if (earVertices[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(0, 0));
        polygon.add(new Point(0, 4));
        polygon.add(new Point(2, 2));
        polygon.add(new Point(4, 4));
        polygon.add(new Point(4, 0));

        TriangulationAlgorithm triangulator = new TriangulationAlgorithm(polygon);
        List<Triangle> triangles = triangulator.triangulate();

        for (Triangle triangle : triangles) {
            System.out.println("Triangle: (" + triangle.p1.x + ", " + triangle.p1.y + "), (" +
                    triangle.p2.x + ", " + triangle.p2.y + "), (" +
                    triangle.p3.x + ", " + triangle.p3.y + ")");
        }
    }
}

