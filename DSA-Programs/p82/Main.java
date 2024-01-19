
class COBSTNode {
    int key;
    COBSTNode left, right;

    public COBSTNode(int key) {
        this.key = key;
        this.left = this.right = null;
    }
}

public class Main {
    private COBSTNode root;

    public Main() {
        this.root = null;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private COBSTNode insert(COBSTNode node, int key) {
        if (node == null) {
            return new COBSTNode(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        }

        return node;
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(COBSTNode node, int key) {
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

