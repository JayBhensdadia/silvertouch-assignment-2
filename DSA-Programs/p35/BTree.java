

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BTreeNode {
    private List<Integer> keys;
    private List<BTreeNode> children;

    public BTreeNode() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public List<Integer> getKeys() {
        return keys;
    }

    public List<BTreeNode> getChildren() {
        return children;
    }
}

public class BTree {
    private BTreeNode root;
    private int order;

    public BTree(int order) {
        this.root = new BTreeNode();
        this.order = order;
    }

    public void insert(int key) {
        insert(root, key);
    }

    private void insert(BTreeNode node, int key) {
        if (node.getKeys().size() == 2 * order - 1) {
            BTreeNode newRoot = new BTreeNode();
            newRoot.getChildren().add(root);
            splitChild(newRoot, 0);
            root = newRoot;
            insertNonFull(root, key);
        } else {
            insertNonFull(node, key);
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.getKeys().size() - 1;

        if (node.getChildren().isEmpty()) {
            while (i >= 0 && key < node.getKeys().get(i)) {
                i--;
            }
            node.getKeys().add(i + 1, key);
        } else {
            while (i >= 0 && key < node.getKeys().get(i)) {
                i--;
            }
            i++;

            if (node.getChildren().get(i).getKeys().size() == 2 * order - 1) {
                splitChild(node, i);
                if (key > node.getKeys().get(i)) {
                    i++;
                }
            }

            insertNonFull(node.getChildren().get(i), key);
        }
    }

    private void splitChild(BTreeNode parentNode, int childIndex) {
        BTreeNode child = parentNode.getChildren().get(childIndex);
        BTreeNode newChild = new BTreeNode();

        parentNode.getKeys().add(childIndex, child.getKeys().get(order - 1));
        parentNode.getChildren().add(childIndex + 1, newChild);

        newChild.getKeys().addAll(child.getKeys().subList(order, 2 * order - 1));
        child.getKeys().subList(order - 1, 2 * order - 1).clear();

        if (!child.getChildren().isEmpty()) {
            newChild.getChildren().addAll(child.getChildren().subList(order, 2 * order));
            child.getChildren().subList(order, 2 * order).clear();
        }
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(BTreeNode node, int key) {
        int i = 0;
        while (i < node.getKeys().size() && key > node.getKeys().get(i)) {
            i++;
        }
        if (i < node.getKeys().size() && key == node.getKeys().get(i)) {
            return true;
        } else if (node.getChildren().isEmpty()) {
            return false;
        } else {
            return search(node.getChildren().get(i), key);
        }
    }

    public void delete(int key) {
        delete(root, key);
    }

    private void delete(BTreeNode node, int key) {
        
        int i = 0;
        while (i < node.getKeys().size() && key > node.getKeys().get(i)) {
            i++;
        }

        
        if (i < node.getKeys().size() && key == node.getKeys().get(i)) {
            
            node.getKeys().remove(i);
        } else {
           
            if (!node.getChildren().isEmpty()) {
                delete(node.getChildren().get(i), key);
            }
        }
    }

    public static void main(String[] args) {
        BTree bTree = new BTree(3);

        bTree.insert(1);
        bTree.insert(3);
        bTree.insert(7);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(13);
        bTree.insert(14);
        bTree.insert(15);
        bTree.insert(18);
        bTree.insert(16);
        bTree.insert(19);
        bTree.insert(24);
        bTree.insert(25);
        bTree.insert(26);
        bTree.insert(21);
        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(20);
        bTree.insert(22);
        bTree.insert(2);
        bTree.insert(17);
        bTree.insert(12);
        bTree.insert(6);
        
        //bTree.delete(6);

        // Search for keys
        System.out.println("Search for key 6: " + bTree.search(6));
        System.out.println("Search for key 23: " + bTree.search(23));
    }
}
