import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ScapegoatTree<T extends Comparable<T>> {
    private static final double ALPHA = 0.75;

    private static class Node<T> {
        T key;
        Node<T> left, right, parent;

        public Node(T key) {
            this.key = key;
            this.left = this.right = this.parent = null;
        }
    }

    private Node<T> root;
    private int size;

    public ScapegoatTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T key) {
        root = insert(root, key);
        size++;
    }

    private Node<T> insert(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
            node.left.parent = node;
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
            node.right.parent = node;
        }

        if (size(node.left) > ALPHA * size || size(node.right) > ALPHA * size) {
            return rebuild(node);
        }

        return node;
    }

    private Node<T> rebuild(Node<T> node) {
        List<Node<T>> nodes = new ArrayList<>();
        collectNodes(node, nodes);
        return buildBalanced(nodes, 0, nodes.size() - 1);
    }

    private void collectNodes(Node<T> node, List<Node<T>> nodes) {
        if (node != null) {
            collectNodes(node.left, nodes);
            nodes.add(node);
            collectNodes(node.right, nodes);
        }
    }

    private Node<T> buildBalanced(List<Node<T>> nodes, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        Node<T> midNode = nodes.get(mid);
        midNode.left = buildBalanced(nodes, start, mid - 1);
        if (midNode.left != null) {
            midNode.left.parent = midNode;
        }
        midNode.right = buildBalanced(nodes, mid + 1, end);
        if (midNode.right != null) {
            midNode.right.parent = midNode;
        }

        return midNode;
    }

    private int size(Node<T> node) {
        return (node == null) ? 0 : 1 + size(node.left) + size(node.right);
    }

    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node<T> node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.key + " ");
            printInOrder(node.right);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ScapegoatTree<Integer> scapegoatTree = new ScapegoatTree<>();

        int[] keys = {5, 2, 8, 1, 4, 7, 9};
        for (int key : keys) {
            scapegoatTree.insert(key);
        }

        System.out.println("In-order traversal of Scapegoat Tree:");
        scapegoatTree.printInOrder();
    }
}