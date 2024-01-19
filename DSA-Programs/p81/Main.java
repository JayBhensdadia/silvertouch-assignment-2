class WAVLNode {
    int key, rank, size;
    WAVLNode left, right;

    public WAVLNode(int key) {
        this.key = key;
        this.rank = 0;
        this.size = 1;
        this.left = this.right = null;
    }
}

public class Main {
    private WAVLNode root;

    public Main() {
        this.root = null;
    }

    private int rank(WAVLNode node) {
        return (node == null) ? -1 : node.rank;
    }

    private int size(WAVLNode node) {
        return (node == null) ? 0 : node.size;
    }

    private void updateNodeAttributes(WAVLNode node) {
        if (node != null) {
            node.rank = Math.min(rank(node.left), rank(node.right)) + 1;
            node.size = size(node.left) + size(node.right) + 1;
        }
    }

    private WAVLNode rightRotate(WAVLNode y) {
        WAVLNode x = y.left;
        y.left = x.right;
        x.right = y;

        updateNodeAttributes(y);
        updateNodeAttributes(x);

        return x;
    }

    private WAVLNode leftRotate(WAVLNode x) {
        WAVLNode y = x.right;
        x.right = y.left;
        y.left = x;

        updateNodeAttributes(x);
        updateNodeAttributes(y);

        return y;
    }

    private WAVLNode balance(WAVLNode node) {
        if (rank(node.right) - rank(node.left) == 2) {
            if (rank(node.right.right) < rank(node.right.left)) {
                node.right = rightRotate(node.right);
            }
            node = leftRotate(node);
        } else if (rank(node.left) - rank(node.right) == 2) {
            if (rank(node.left.left) < rank(node.left.right)) {
                node.left = leftRotate(node.left);
            }
            node = rightRotate(node);
        }

        return node;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private WAVLNode insert(WAVLNode node, int key) {
        if (node == null) {
            return new WAVLNode(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        }

        updateNodeAttributes(node);
        return balance(node);
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(WAVLNode node, int key) {
        if (node == null) {
            return false;
        }

        if (key == node.key) {
            return true;
        } else if (key < node.key) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }

    public static void main(String[] args) {
        Main tree = new Main();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);

        System.out.println(tree.search(5)); // true
        System.out.println(tree.search(8)); // false
    }
}
