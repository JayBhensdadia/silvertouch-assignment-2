import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class KDNode {
    int[] point;
    KDNode left, right;

    public KDNode(int[] point) {
        this.point = point.clone();
        this.left = this.right = null;
    }
}

public class Main {
    private KDNode root;

    public Main(List<int[]> points) {
        this.root = buildTree(points, 0, points.size() - 1, 0);
    }

    private KDNode buildTree(List<int[]> points, int left, int right, int depth) {
        if (left > right) return null;

        int k = points.get(0).length;
        points.sort(Comparator.comparingInt(p -> p[depth % k]));

        int median = left + (right - left) / 2;
        KDNode node = new KDNode(points.get(median));

        node.left = buildTree(points, left, median - 1, depth + 1);
        node.right = buildTree(points, median + 1, right, depth + 1);

        return node;
    }

    public List<int[]> rangeQuery(int[] minPoint, int[] maxPoint) {
        List<int[]> result = new ArrayList<>();
        rangeQuery(root, minPoint, maxPoint, 0, result);
        return result;
    }

    private void rangeQuery(KDNode node, int[] minPoint, int[] maxPoint, int depth, List<int[]> result) {
        if (node == null) return;

        int k = minPoint.length;
        int[] currentPoint = node.point;

        boolean isIncluded = true;
        for (int i = 0; i < k; i++) {
            if (currentPoint[i] < minPoint[i] || currentPoint[i] > maxPoint[i]) {
                isIncluded = false;
                break;
            }
        }

        if (isIncluded) {
            result.add(currentPoint);
        }

        if (minPoint[depth % k] <= currentPoint[depth % k]) {
            rangeQuery(node.left, minPoint, maxPoint, depth + 1, result);
        }
        if (maxPoint[depth % k] >= currentPoint[depth % k]) {
            rangeQuery(node.right, minPoint, maxPoint, depth + 1, result);
        }
    }

    public static void main(String[] args) {
        List<int[]> points = new ArrayList<>();
        points.add(new int[]{2, 3});
        points.add(new int[]{5, 4});
        points.add(new int[]{9, 6});
        points.add(new int[]{4, 7});

        Main kdTree = new Main(points);

        int[] minPoint = {3, 4};
        int[] maxPoint = {8, 7};
        List<int[]> result = kdTree.rangeQuery(minPoint, maxPoint);

        for (int[] point : result) {
            System.out.print("(");
            for (int i = 0; i < point.length; i++) {
                System.out.print(point[i]);
                if (i < point.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }
    }
}
