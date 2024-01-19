package p96;

class Node {
    int key;
    int size;
    Node left, right;

    public Node(int key) {
        this.key = key;
        this.size = 1;
        this.left = this.right = null;
    }
}

class OrderStatisticTree {
    private Node root;

    private int getSize(Node node) {
        return (node != null) ? node.size : 0;
    }

    private void updateSize(Node node) {
        if (node != null) {
            node.size = getSize(node.left) + getSize(node.right) + 1;
        }
    }

    private Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }

        if (key < root.key) {
            root.left = insert(root.left, key);
        } else {
            root.right = insert(root.right, key);
        }

        updateSize(root);
        return root;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node kthSmallest(Node root, int k) {
        if (root == null) {
            return null;
        }

        int leftSize = getSize(root.left);

        if (k == leftSize + 1) {
            return root;
        } else if (k <= leftSize) {
            return kthSmallest(root.left, k);
        } else {
            return kthSmallest(root.right, k - leftSize - 1);
        }
    }

    public int kthSmallest(int k) {
        Node resultNode = kthSmallest(root, k);
        return (resultNode != null) ? resultNode.key : Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        OrderStatisticTree ost = new OrderStatisticTree();

        // Inserting elements
        ost.insert(3);
        ost.insert(5);
        ost.insert(2);
        ost.insert(8);
        ost.insert(7);

        // Querying k-th smallest elements
        System.out.println("1st smallest element: " + ost.kthSmallest(1));
        System.out.println("3rd smallest element: " + ost.kthSmallest(3));
        System.out.println("5th smallest element: " + ost.kthSmallest(5));
    }
}
