class RBNode {
    int key;
    int color; 
    RBNode left, right, parent;

    public RBNode(int key, int color) {
        this.key = key;
        this.color = color;
        this.left = this.right = this.parent = null;
    }
}

public class Main {
    private RBNode root;
    private static final int BLACK = 0;
    private static final int RED = 1;

    public Main() {
        this.root = null;
    }

    private RBNode createNode(int key, int color) {
        return new RBNode(key, color);
    }

    private RBNode insert(RBNode root, int key) {
        return insert(root, key, null);
    }

    private RBNode insert(RBNode root, int key, RBNode parent) {
        if (root == null) {
            RBNode newNode = createNode(key, RED);
            newNode.parent = parent;
            return newNode;
        }

        if (key < root.key) {
            root.left = insert(root.left, key, root);
        } else if (key > root.key) {
            root.right = insert(root.right, key, root);
        } else {
            // Duplicate keys are not allowed
            return root;
        }

        if (isRed(root.right) && !isRed(root.left)) {
            root = rotateLeft(root);
        }
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rotateRight(root);
        }
        if (isRed(root.left) && isRed(root.right)) {
            flipColors(root);
        }

        return root;
    }

    private boolean isRed(RBNode node) {
        return node != null && node.color == RED;
    }

    private RBNode rotateLeft(RBNode h) {
        RBNode x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private RBNode rotateRight(RBNode h) {
        RBNode x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(RBNode h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public Main insert(int key) {
        Main newTree = new Main();
        newTree.root = insert(this.root, key);
        return newTree;
    }

    public static void main(String[] args) {
        Main tree1 = new Main();
        tree1 = tree1.insert(10);
        tree1 = tree1.insert(20);

        Main tree2 = tree1.insert(5);
        tree2 = tree2.insert(15);

        // Use tree1 or tree2 for querying at different points in time
    }
}

