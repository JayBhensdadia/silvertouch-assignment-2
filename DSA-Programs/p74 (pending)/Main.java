import java.io.*;
import java.util.ArrayList;
import java.util.List;

class BTreeNode implements Serializable {
    boolean leaf;
    List<Integer> keys;
    List<BTreeNode> children;

    BTreeNode(boolean leaf) {
        this.leaf = leaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }
}

class BTree {
    BTreeNode root;
    int order;
    int nextNodeId;

    BTree(int order) {
        this.root = new BTreeNode(true);
        this.order = order;
        this.nextNodeId = 1;
        save();
    }

    void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bt_" + nextNodeId + ".ser"))) {
            oos.writeObject(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void load(int nodeId) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bt_" + nodeId + ".ser"))) {
            root = (BTreeNode) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void insert(int key) {
        _insert(root, key);
    }

    void _insert(BTreeNode node, int key) {
        if (node.keys.size() == 2 * order - 1) {
            BTreeNode newNode = new BTreeNode(false);
            Object[] splitResult = _split(node, newNode);

            node = (BTreeNode) splitResult[0];
            int median = (int) splitResult[1];

            if (node == root) {
                root = new BTreeNode(false);
                root.keys.add(median);
                root.children.add(node);
                root.children.add(newNode);
                save();
                return;
            }

            _insertParent(node, median, newNode);
        }

        if (node.leaf) {
            _insertIntoLeaf(node, key);
        } else {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) {
                i++;
            }
            _insert(node.children.get(i), key);
        }
    }

    void _insertIntoLeaf(BTreeNode node, int key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) {
            i++;
        }
        node.keys.add(i, key);
        save();
    }

    Object[] _split(BTreeNode node, BTreeNode newNode) {
        int medianIndex = node.keys.size() / 2;
        int median = node.keys.get(medianIndex);

        List<Integer> newNodeKeys = new ArrayList<>(node.keys.subList(medianIndex + 1, node.keys.size()));
        node.keys.subList(medianIndex, node.keys.size()).clear();
        newNode.keys.addAll(newNodeKeys);

        if (!node.leaf) {
            List<BTreeNode> newNodeChildren = new ArrayList<>(node.children.subList(medianIndex + 1, node.children.size()));
            node.children.subList(medianIndex + 1, node.children.size()).clear();
            newNode.children.addAll(newNodeChildren);
        }

        save();
        return new Object[]{node, median};
    }

    void _insertParent(BTreeNode oldNode, int median, BTreeNode newNode) {
        BTreeNode parent = _findParent(root, oldNode);

        if (parent.keys.size() == 2 * order - 1) {
            BTreeNode newParent = new BTreeNode(false);
            Object[] splitResult = _split(parent, newParent);

            parent = (BTreeNode) splitResult[0];
            int parentMedian = (int) splitResult[1];

            _insertParent(parent, parentMedian, newParent);
        }

        int i = 0;
        while (i < parent.keys.size() && median > parent.keys.get(i)) {
            i++;
        }

        parent.keys.add(i, median);
        parent.children.add(i + 1, newNode);
        save();
    }

    BTreeNode _findParent(BTreeNode node, BTreeNode child) {
        if (node.leaf || (!node.leaf && node.children.contains(child))) {
            return node;
        }

        int i = 0;
        while (i < node.keys.size() && child.keys.get(0) > node.keys.get(i)) {
            i++;
        }

        return _findParent(node.children.get(i), child);
    }

    boolean search(int key) {
        return search(key, root);
    }

    boolean search(int key, BTreeNode node) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) {
            i++;
        }

        if (i < node.keys.size() && key == node.keys.get(i)) {
            return true;
        } else if (node.leaf) {
            return false;
        } else {
            return search(key, node.children.get(i));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BTree btree = new BTree(2);
        btree.insert(10);
        btree.insert(20);
        btree.insert(5);
        btree.insert(6);
        btree.insert(12);
        btree.insert(30);

        // Save the state of the B-tree
        btree.save();

        // Perform more operations (insertions, deletions, etc.) if needed

        // Load the B-tree at a previous point in time
        btree.load(1);

        // Perform queries on the B-tree as it was at the loaded state
        boolean result = btree.search(10);
        System.out.println(result);  // Output: true
    }
}
