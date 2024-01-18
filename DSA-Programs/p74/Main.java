

import java.util.ArrayList;
import java.util.List;

class PersistentBTree<T extends Comparable<T>> {
    private static final int MIN_KEYS = 1;
    private static final int MAX_KEYS = 2 * MIN_KEYS;

    private static class Node<T> {
        List<T> keys;
        List<Node<T>> children;

        Node(List<T> keys, List<Node<T>> children) {
            this.keys = keys;
            this.children = children;
        }
    }

    private List<Node<T>> versions;

    public PersistentBTree() {
        versions = new ArrayList<>();
        versions.add(new Node<>(new ArrayList<>(), new ArrayList<>()));
    }

    public PersistentBTree<T> insert(T key) {
        int versionIndex = versions.size() - 1;
        Node<T> root = versions.get(versionIndex);
        Node<T> newRoot = insert(root, key);

        if (newRoot != null) {
            versions.add(newRoot);
        }

        return this;
    }

    private Node<T> insert(Node<T> node, T key) {
        List<T> newKeys = new ArrayList<>(node.keys);
        List<Node<T>> newChildren = new ArrayList<>(node.children);

        if (node.children.isEmpty()) {
            insertIntoLeaf(newKeys, key);
        } else {
            int childIndex = findChildIndex(node, key);
            Node<T> child = newChildren.get(childIndex);
            Node<T> newChild = insert(child, key);

            if (newChild != null) {
                insertIntoNode(newKeys, newChildren, childIndex, newChild.keys.get(0), newChild);
                if (newKeys.size() > MAX_KEYS) {
                    return splitNode(newKeys, newChildren);
                }
            }
        }

        return new Node<>(newKeys, newChildren);
    }

    private void insertIntoLeaf(List<T> keys, T key) {
        int index = findKeyIndex(keys, key);
        keys.add(index, key);
    }

    private void insertIntoNode(List<T> keys, List<Node<T>> children, int index, T key, Node<T> child) {
        keys.add(index, key);
        children.add(index + 1, child);
    }

    private Node<T> splitNode(List<T> keys, List<Node<T>> children) {
        int splitIndex = keys.size() / 2;
        List<T> newKeys = new ArrayList<>(keys.subList(splitIndex + 1, keys.size()));
        List<Node<T>> newChildren = new ArrayList<>(children.subList(splitIndex + 1, children.size()));
        keys.subList(splitIndex, keys.size()).clear();
        children.subList(splitIndex + 1, children.size()).clear();

        return new Node<>(newKeys, newChildren);
    }

    private int findKeyIndex(List<T> keys, T key) {
        int index = 0;
        while (index < keys.size() && key.compareTo(keys.get(index)) > 0) {
            index++;
        }
        return index;
    }

    private int findChildIndex(Node<T> node, T key) {
        int index = 0;
        while (index < node.keys.size() && key.compareTo(node.keys.get(index)) > 0) {
            index++;
        }
        return index;
    }

    public boolean contains(T key, int version) {
        if (version < 0 || version >= versions.size()) {
            throw new IllegalArgumentException("Invalid version");
        }

        Node<T> root = versions.get(version);
        return contains(root, key);
    }

    private boolean contains(Node<T> node, T key) {
        if (node == null) {
            return false;
        }

        int index = findKeyIndex(node.keys, key);
        if (index < node.keys.size() && node.keys.get(index).equals(key)) {
            return true;
        }

        return index < node.children.size() && contains(node.children.get(index), key);
    }

   
}


public class Main {

    public static void main(String[] args) {
        PersistentBTree<Integer> persistentBTree = new PersistentBTree<>();

        // Version 0
        persistentBTree.insert(5);

        // Version 1
        persistentBTree = persistentBTree.insert(10);

        // Check if 5 is present in version 0
        System.out.println("Contains 5 in version 0: " + persistentBTree.contains(5, 0)); // true

        // Check if 10 is present in version 0
        System.out.println("Contains 10 in version 0: " + persistentBTree.contains(10, 0)); // false

        // Check if 10 is present in version 1
        System.out.println("Contains 10 in version 1: " + persistentBTree.contains(10, 1)); // true
    }
}