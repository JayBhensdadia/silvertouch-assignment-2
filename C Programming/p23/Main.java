import java.util.LinkedList;
import java.util.Queue;

class BTreeNode {
    int degree;
    int[] keys;
    BTreeNode[] children;
    int numKeys;
    boolean isLeaf;

    public BTreeNode(int degree, boolean isLeaf) {
        this.degree = degree;
        this.isLeaf = isLeaf;
        this.keys = new int[2 * degree - 1];
        this.children = new BTreeNode[2 * degree];
        this.numKeys = 0;
    }
}

public class Main {
    private BTreeNode root;
    private int degree;

    public Main(int degree) {
        this.root = null;
        this.degree = degree;
    }

    public void insert(int key) {
        if (root == null) {
            root = new BTreeNode(degree, true);
            root.keys[0] = key;
            root.numKeys = 1;
        } else {
            if (root.numKeys == 2 * degree - 1) {
                BTreeNode newRoot = new BTreeNode(degree, false);
                newRoot.children[0] = root;
                splitChild(newRoot, 0);
                root = newRoot;
            }
            insertNonFull(root, key);
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.numKeys - 1;
        if (node.isLeaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.children[i].numKeys == 2 * degree - 1) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(BTreeNode parent, int index) {
        BTreeNode child = parent.children[index];
        BTreeNode newChild = new BTreeNode(degree, child.isLeaf);
        parent.numKeys++;

        for (int i = parent.numKeys - 1; i > index; i--) {
            parent.keys[i] = parent.keys[i - 1];
        }
        parent.keys[index] = child.keys[degree - 1];

        for (int i = 2 * degree - 2; i >= degree; i--) {
            newChild.keys[i - degree] = child.keys[i];
            child.keys[i] = 0;
        }

        if (!child.isLeaf) {
            for (int i = 2 * degree - 1; i >= degree; i--) {
                newChild.children[i - degree] = child.children[i];
                child.children[i] = null;
            }
        }

        child.numKeys = degree - 1;
        newChild.numKeys = degree - 1;

        parent.children[parent.numKeys] = parent.children[parent.numKeys - 1];
        parent.children[index + 1] = newChild;
    }

    public void printBTree() {
        if (root != null) {
            printBTree(root, 0);
        }
    }

    private void printBTree(BTreeNode node, int level) {
        System.out.println("Level " + level + ": " + node.numKeys + " keys - " + (node.isLeaf ? "Leaf" : "Internal"));

        for (int i = 0; i < node.numKeys; i++) {
            System.out.print(node.keys[i] + " ");
        }
        System.out.println();

        if (!node.isLeaf) {
            for (int i = 0; i <= node.numKeys; i++) {
                printBTree(node.children[i], level + 1);
            }
        }
    }

    public static void main(String[] args) {
        Main bTree = new Main(3);

        int[] keys = {3, 7, 1, 8, 4, 6, 2, 5};
        for (int key : keys) {
            bTree.insert(key);
        }

        System.out.println("B-Tree structure:");
        bTree.printBTree();
    }
}
