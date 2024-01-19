package p86;

class TwoLevelTreeNode {
    char key;
    TwoLevelTreeNode left, right;

    public TwoLevelTreeNode(char key) {
        this.key = key;
        this.left = this.right = null;
    }
}

class TwoLevelTree {
    private TwoLevelTreeNode root;

    public TwoLevelTree() {
        this.root = null;
    }

    public void insert(String key) {
        root = insert(root, key.toCharArray(), 0);
    }

    private TwoLevelTreeNode insert(TwoLevelTreeNode node, char[] key, int depth) {
        if (node == null) {
            node = new TwoLevelTreeNode(key[depth]);
        }

        if (key[depth] < node.key) {
            node.left = insert(node.left, key, depth + 1);
        } else if (key[depth] > node.key) {
            node.right = insert(node.right, key, depth + 1);
        }

        return node;
    }

    public boolean search(String key) {
        return search(root, key.toCharArray(), 0);
    }

    private boolean search(TwoLevelTreeNode node, char[] key, int depth) {
        if (node == null) {
            return false;
        }

        if (key[depth] == node.key) {
            if (depth == key.length - 1) {
                return true; // Key found
            } else {
                return search(node.left, key, depth + 1) || search(node.right, key, depth + 1);
            }
        } else if (key[depth] < node.key) {
            return search(node.left, key, depth + 1);
        } else {
            return search(node.right, key, depth + 1);
        }
    }

    public static void main(String[] args) {
        TwoLevelTree tree = new TwoLevelTree();

        tree.insert("apple");
        tree.insert("banana");
        tree.insert("orange");

        System.out.println(tree.search("apple")); // true
        System.out.println(tree.search("grape"));  // false
    }
}

