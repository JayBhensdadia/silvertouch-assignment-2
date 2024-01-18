import java.util.LinkedList;
import java.util.Queue;

class BTreeNode {
    int t; 
    int n; 
    int[] keys; 
    BTreeNode[] children; 
    boolean leaf; 

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.n = 0;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.leaf = leaf;
    }

    public int findKey(int k) {
        int index = 0;
        while (index < n && keys[index] < k) {
            index++;
        }
        return index;
    }

    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1;

        for (int j = n; j > i; j--) {
            children[j + 1] = children[j];
        }

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }

        keys[i] = y.keys[t - 1];
        n++;
    }

    public void insertNonFull(int k) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && k < keys[i]) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = k;
            n++;
        } else {
            while (i >= 0 && k < keys[i]) {
                i--;
            }

            if (children[i + 1].n == 2 * t - 1) {
                splitChild(i + 1, children[i + 1]);

                if (k > keys[i + 1]) {
                    i++;
                }
            }
            children[i + 1].insertNonFull(k);
        }
    }

    public void rangeSearch(int k1, int k2) {
        int i = 0;
        while (i < n && keys[i] < k1) {
            i++;
        }

        while (i < n && keys[i] <= k2) {
            if (leaf) {
                System.out.print(keys[i] + " ");
            } else {
                children[i].rangeSearch(k1, k2);
            }
            i++;
        }

        if (!leaf) {
            children[i].rangeSearch(k1, k2);
        }
    }

    public void printLevelOrder() {
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.offer(this);

        while (!queue.isEmpty()) {
            int levelNodes = queue.size();

            while (levelNodes > 0) {
                BTreeNode node = queue.poll();
                for (int i = 0; i < node.n; i++) {
                    System.out.print(node.keys[i] + " ");
                }

                if (!node.leaf) {
                    for (int i = 0; i <= node.n; i++) {
                        if (node.children[i] != null) {
                            queue.offer(node.children[i]);
                        }
                    }
                }

                System.out.print("| ");
                levelNodes--;
            }
            System.out.println();
        }
    }
}

class BTree {
    private BTreeNode root;
    private int t;

    public BTree(int t) {
        this.root = null;
        this.t = t;
    }

    public void insert(int k) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);
                s.children[0] = root;
                s.splitChild(0, root);

                int i = 0;
                if (s.keys[0] < k) {
                    i++;
                }
                s.children[i].insertNonFull(k);

                root = s;
            } else {
                root.insertNonFull(k);
            }
        }
    }

    public void rangeSearch(int k1, int k2) {
        if (root != null) {
            root.rangeSearch(k1, k2);
        }
    }

    public void printLevelOrder() {
        if (root != null) {
            root.printLevelOrder();
        }
    }
}


public class Main {

    public static void main(String[] args) {
        BTree bTree = new BTree(3);

        int[] keys = { 3, 8, 12, 17, 21, 29, 31, 33, 38, 42, 47, 50, 52, 56, 60, 63, 70, 75 };
        for (int key : keys) {
            bTree.insert(key);
        }

        System.out.println("B-Tree Level Order:");
        bTree.printLevelOrder();

        int k1 = 30;
        int k2 = 60;
        System.out.println("\nRange Search for keys between " + k1 + " and " + k2 + ":");
        bTree.rangeSearch(k1, k2);
    }
    
}