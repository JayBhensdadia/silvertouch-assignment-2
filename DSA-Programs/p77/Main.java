import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Point {
    int x, y; 

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Node {
    Point point;
    Node left, right;

    public Node(Point point) {
        this.point = point;
        this.left = this.right = null;
    }
}

public class Main {
    private Node root;

    public Main(List<Point> points) {
        this.root = buildTree(points, 0, points.size() - 1, 0);
    }

    private Node buildTree(List<Point> points, int left, int right, int depth) {
        if (left > right) return null;

        points.sort(depth % 2 == 0 ? Comparator.comparingInt(p -> p.x) : Comparator.comparingInt(p -> p.y));
        int median = left + (right - left) / 2;

        Node node = new Node(points.get(median));
        node.left = buildTree(points, left, median - 1, depth + 1);
        node.right = buildTree(points, median + 1, right, depth + 1);

        return node;
    }

    public List<Point> rangeQuery(int x1, int y1, int x2, int y2) {
        List<Point> result = new ArrayList<>();
        rangeQuery(root, x1, y1, x2, y2, 0, result);
        return result;
    }

    private void rangeQuery(Node node, int x1, int y1, int x2, int y2, int depth, List<Point> result) {
        if (node == null) return;

        Point point = node.point;
        int currentDimValue = (depth % 2 == 0) ? point.x : point.y;

        if (x1 <= point.x && point.x <= x2 && y1 <= point.y && point.y <= y2) {
            result.add(point);
        }

        if (depth % 2 == 0) {
            if (x1 <= currentDimValue) rangeQuery(node.left, x1, y1, x2, y2, depth + 1, result);
            if (x2 > currentDimValue) rangeQuery(node.right, x1, y1, x2, y2, depth + 1, result);
        } else {
            if (y1 <= currentDimValue) rangeQuery(node.left, x1, y1, x2, y2, depth + 1, result);
            if (y2 > currentDimValue) rangeQuery(node.right, x1, y1, x2, y2, depth + 1, result);
        }
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        points.add(new Point(5, 6));
        points.add(new Point(7, 8));

        Main tree = new Main(points);

        int x1 = 2, y1 = 3, x2 = 6, y2 = 7;
        List<Point> result = tree.rangeQuery(x1, y1, x2, y2);

        for (Point point : result) {
            System.out.println("(" + point.x + ", " + point.y + ")");
        }
    }
}

