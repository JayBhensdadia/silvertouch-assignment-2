import java.util.Stack;

class Node {
    int value;
    int index;
    Node left, right;

    public Node(int value, int index) {
        this.value = value;
        this.index = index;
        this.left = this.right = null;
    }
}

class CartesianTree {
    Node root;

    public CartesianTree(int[] arr) {
        buildTree(arr);
    }

    private Node buildTree(int[] arr) {
        Stack<Node> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            Node node = new Node(arr[i], i);

            while (!stack.isEmpty() && stack.peek().value > arr[i]) {
                node.left = stack.pop();
            }

            if (!stack.isEmpty()) {
                stack.peek().right = node;
            } else {
                root = node;
            }

            stack.push(node);
        }

        return root;
    }

    public int findMinimumInRange(int start, int end) {
        return findMinimumInRange(root, start, end);
    }

    private int findMinimumInRange(Node root, int start, int end) {
        if (root == null || root.index < start || root.index > end) {
            return Integer.MAX_VALUE;
        }

        if (root.index >= start && root.index <= end) {
            return Math.min(root.value, Math.min(
                    findMinimumInRange(root.left, start, end),
                    findMinimumInRange(root.right, start, end)
            ));
        }

        return Integer.MAX_VALUE;
    }
}

public class Main {
    public static void main(String[] args) {
        int[] array = {5, 3, 2, 7, 9, 8, 10};
        CartesianTree cartesianTree = new CartesianTree(array);

        int rangeStart = 1;
        int rangeEnd = 5;

        int minInRange = cartesianTree.findMinimumInRange(rangeStart, rangeEnd);
        System.out.println("Minimum element in range [" + rangeStart + ", " + rangeEnd + "]: " + minInRange);
    }
}