package p97;

class Node {
    int key;
    int priority;
    Node left, right;

    public Node(int key, int priority) {
        this.key = key;
        this.priority = priority;
        this.left = this.right = null;
    }
}

class PersistentCartesianTree {
    private Node[] versions;

    public PersistentCartesianTree(int[] keys, int[] priorities) {
        int n = keys.length;
        versions = new Node[n];

        for (int i = 0; i < n; i++) {
            versions[i] = insert(versions[i - 1], keys[i], priorities[i]);
        }
    }

    private Node insert(Node root, int key, int priority) {
        Node newNode = new Node(key, priority);

        if (root == null || root.priority < priority) {
            split(root, key, newNode.left, newNode.right);
            return newNode;
        }

        if (key < root.key) {
            root.left = insert(root.left, key, priority);
        } else {
            root.right = insert(root.right, key, priority);
        }

        return root;
    }

    private void split(Node root, int key, Node left, Node right) {
        if (root == null) {
            left = right = null;
        } else if (key < root.key) {
            split(root.left, key, left, root.left);
            right = root;
        } else {
            split(root.right, key, root.right, right);
            left = root;
        }
    }

    public int query(int version, int key) {
        return query(versions[version], key);
    }

    private int query(Node root, int key) {
        if (root == null) {
            return -1; // Key not found
        }

        if (key == root.key) {
            return root.priority;
        } else if (key < root.key) {
            return query(root.left, key);
        } else {
            return query(root.right, key);
        }
    }

    public static void main(String[] args) {
        int[] keys = {3, 1, 4, 2};
        int[] priorities = {10, 5, 8, 3};

        PersistentCartesianTree tree = new PersistentCartesianTree(keys, priorities);

        System.out.println("Query version 0, key 2: " + tree.query(0, 2));
        System.out.println("Query version 1, key 4: " + tree.query(1, 4));
    }
}

